package Q3;

// @formatter:off
/**
 * Course: CSI3131 - Operating Systems
 * Professor: Dr. Fadi Malek - Malek@uOttawa.ca
 * Semester: Summer 2022
 * 
 * @author Kian Zahrai - 300098986
 * @author Billy Bolton - 6411144
 * @since 2022-07-17
 */
// @formatter:on

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Memory {

    private static Producer producer;
    private static Consumer consumer;
    private static final MonitorRole ROLE = MonitorRole.MEMORY;

    private static Semaphore lock;
    private static Queue<BigInteger> queue;

    private Memory() {}

    public static void start() {
        lock = new Semaphore(1);
        queue = new LinkedList<>();

        consumer = new Consumer();
        producer = new Producer();

        Thread consumerThread = new Thread(consumer);
        Thread producerThread = new Thread(producer);

        consumerThread.start();
        producerThread.start();

    }

    public static synchronized void acquireQueue(MonitorRole role) {
        try {
            synchronized (lock) {
                // Utils.printHeaderString(role);
                System.out.println(role + " is acquiring the queue.");
                lock.acquire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void releaseQueue(MonitorRole role) {

        synchronized (lock) {
            System.out.println(role + " is releasing the queue.");
            lock.release();
            lock.notifyAll();
            System.out.println(role + " has released the queue.");
        }
        synchronized (producer) {
            System.out.println("SYSTEM: notifying the producer.");
            producer.notifyAll();
        }

        synchronized (consumer) {
            System.out.println("SYSTEM: notifying the consumer.");
            consumer.notifyAll();
        }
    }

    public static synchronized boolean isQueueAvailable() {
        return lock.availablePermits() == 1;
    }

    public static synchronized boolean isQueueEmpty() {
        return queue.isEmpty();
    }

    public static synchronized int getQueueSize() {
        return queue.size();
    }

    public static synchronized void addToQueue(BigInteger Cn) {
        synchronized (queue) {
            System.out.println(
                    ROLE + " enqueing: n=" + (getQueueSize() + 1) + ", Cn=" + Cn.toString());
            queue.add(Cn);
            queue.notifyAll();
        }
    }

    public static synchronized BigInteger dequeueQueue() {
        synchronized (queue) {
            BigInteger Cn = queue.remove();
            queue.notifyAll();
            return Cn;
        }
    }

    public static synchronized void clearQueue() {
        synchronized (queue) {
            int count = 0;
            while (!Memory.isQueueEmpty()) {
                BigInteger Cn = Memory.dequeueQueue();
                System.out.println(ROLE + " dequeing: n=" + (++count) + ", Cn=" + Cn.toString());
            }
            queue.notifyAll();
        }
    }

    public static synchronized boolean isProducerBusy() {
        return producer.isBusy();
    }

    public static synchronized boolean isConsumerBusy() {
        return consumer.isBusy();
    }

}
