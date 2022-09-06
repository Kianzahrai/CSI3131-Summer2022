package Q2;

import java.util.Arrays;

/**
 * Course: CSI3131 - Operating Systems Professor: Dr. Fadi Malek - Malek@uOttawa.ca Semester: Summer
 * 2022
 * 
 * @author Kian Zahrai - 300098986
 * @author Billy Bolton - 6411144
 * @since 2022-07-17
 */

public class FIFO extends Algo {

    public static int calculateFaults(int[] pageReference, int pageFramesSize) {
        int pageFault = 0;
        int firstInPos = 0;
        int[] memory = new int[pageFramesSize];

        Arrays.fill(memory, -1);

        for (int number : pageReference) {
            boolean checkResult = check(number, memory);

            if (!checkResult) {
                memory[firstInPos] = number;
                firstInPos = (firstInPos + 1) % pageFramesSize;
                pageFault++;
            }
        }

        return pageFault;
    }

    /* Check if the number in page reference is exist in memoryArray */
    public static boolean check(int num, int[] memoryArray) {
        for (int i : memoryArray) {
            if (num == i) {
                return true;
            }
        }

        return false;
    }

}
