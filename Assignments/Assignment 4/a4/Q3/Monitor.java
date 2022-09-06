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

import java.util.concurrent.Semaphore;

public class Monitor {

    private final Semaphore isBusy;
    protected MonitorRole ROLE;

    public Monitor() {
        isBusy = new Semaphore(1);
    }

    public Monitor(MonitorRole role) {
        this();
        this.ROLE = role;
    }

    protected void pause() {
        try {
            Thread.sleep(Utils.randomSleepTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void pause(int n) {
        try {
            Thread.sleep(Utils.randomSleepTime(n));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected synchronized void setIsBusy(boolean busy) {
        if (busy) {
            try {
                isBusy.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            isBusy.release();
        }
        notifyAll();
    }

    public synchronized boolean isBusy() {
        return isBusy.availablePermits() == 0;
    }

}
