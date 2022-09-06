package Q3;

import java.math.BigInteger;
import java.util.Scanner;

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

public class Producer extends Monitor implements Runnable {

    private static Scanner input = new Scanner(System.in);
    private static boolean isSimulationModeSet = false;
    private static SimulationMode mode;

    public Producer() {
        super(MonitorRole.PRODUCER);
    }

    private static synchronized int getInput() {

        if (!isSimulationModeSet) {
            setSimulationMode();
        }

        int num;
        while (true) {
            if (mode == SimulationMode.MANUAL) {
                System.out.println("SYSTEM: Enter an integer greater than 0. ");
            }
            num = (mode == SimulationMode.AUTOMATED) ? Utils.randomInput() : input.nextInt();
            if (num > 0) {
                System.out.println("SYSTEM: User entered " + num + ".");
                return num;
            }
        }
    }

    private static synchronized void setSimulationMode() {
        while (!isSimulationModeSet) {
            System.out.println("SYSTEM: Select a simulation mode:\n ");
            System.out.println("Automated: Enter 1.");
            System.out.println("Manual: Enter 2.\n");
            String inputValue = input.next().toUpperCase();

            switch (inputValue) {
                case ("1"):
                case ("AUTOMATED"):
                case ("2"):
                case ("MANUAL"):
                    isSimulationModeSet = true;
                    mode = (inputValue.equals("1") || inputValue.equals("AUTOMATED"))
                            ? SimulationMode.AUTOMATED
                            : SimulationMode.MANUAL;
                    break;
                default:
                    System.out.println("SYSTEM: Invalid input. Please try again.");
                    continue;
            }
        }
    }

    private synchronized BigInteger factorial(BigInteger i) {
        if (i.compareTo(Utils.ONE) <= 0) {
            return BigInteger.valueOf(1);
        }

        BigInteger decremented = i.subtract(Utils.ONE);
        return i.multiply(factorial(decremented));
    }

    private synchronized void generateCatalanSequence() {
        try {
            while (!Memory.isQueueEmpty()) {
                System.out.println(ROLE + " is waiting for the queue to be empty.");
                wait();
            }
            System.out.println(ROLE + " is DONE waiting for the queue to be empty.");

            while (!Memory.isQueueAvailable() || Memory.isConsumerBusy()) {
                System.out.println(ROLE + " is waiting for the queue to be available.");
                wait();
            }
            System.out.println(ROLE + " is DONE waiting for the queue to be available.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setIsBusy(true);
        Memory.acquireQueue(ROLE);
        int n = getInput();
        for (long i = 1; i <= n; i++) {
            BigInteger Cn =
                    factorial(BigInteger.valueOf(2 * i)).divide(factorial(BigInteger.valueOf(i + 1))
                            .multiply(factorial(BigInteger.valueOf(i))));
            Memory.addToQueue(Cn);
        }
        Memory.releaseQueue(ROLE);
        setIsBusy(false);
    }

    @Override
    public void run() {
        pause(500);
        while (true) {
            Utils.printHeaderString(ROLE);
            generateCatalanSequence();
            pause();
        }
    }

}
