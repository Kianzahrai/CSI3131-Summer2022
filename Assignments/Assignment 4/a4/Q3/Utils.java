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
import java.util.Random;

/**
 * This class contains methods that returns a random number between 0 and 10,000.
 */
public class Utils {


    public static final BigInteger ZERO = BigInteger.valueOf(0);
    public static final BigInteger ONE = BigInteger.valueOf(1);
    public static final BigInteger TWO = BigInteger.valueOf(2);

    public static final Random random = new Random();

    private Utils() {}

    /**
     * Generates a random number between 0 and 10000.
     * 
     * @return A random number between 0 and 10000.
     */
    public static long randomSleepTime() {
        return randomSleepTime(10000);
    }

    /**
     * Generates a random number between 0 and a specified time.
     * 
     * @param time
     * @return Return a random number between 0 and a specified time.
     */
    public static long randomSleepTime(int time) {
        return (long) (Math.random() * time);
    }

    /**
     * Generates a random number between 0 and 100.
     * 
     * @return
     */
    public static int randomInput() {
        return random.nextInt(100);
    }

    public static void printHeaderString(MonitorRole role) {
        StringBuilder sb = new StringBuilder();
        System.out.println("\n\n*******************************************\n");
        System.out.println(role);
        System.out.println("\n*******************************************\n");
        System.out.println(sb.toString());
    }

}
