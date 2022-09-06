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

public class Consumer extends Monitor implements Runnable {

    public Consumer() {
        super(MonitorRole.CONSUMER);
    }

    private synchronized void consumeQueue() {
        Utils.printHeaderString(ROLE);
        try {
            while (Memory.isQueueEmpty()) {
                System.out.println(ROLE + " is waiting for the queue to not be empty.");
                wait();
            }
            System.out.println(ROLE + " is DONE waiting for the queue to not be empty.");
            while (!Memory.isQueueAvailable() || Memory.isProducerBusy()) {
                System.out.println(ROLE + " is waiting for the queue to be available.");
                wait();
            }
            System.out.println(ROLE + " is DONE waiting for the queue to available.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setIsBusy(true);
        Memory.acquireQueue(ROLE);
        System.out.println(ROLE + " is printing the Catalan sequence:");
        Memory.clearQueue();
        System.out.println(ROLE + " is done printing Catalan sequence.");
        Memory.releaseQueue(ROLE);
        setIsBusy(false);
    }

    @Override
    public void run() {
        while (true) {
            pause();
            consumeQueue();
        }
    }

}
