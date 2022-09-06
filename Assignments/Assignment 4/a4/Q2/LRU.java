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

public class LRU extends Algo {

    public static int calculateFaults(int[] pageReference, int pageFramesSize) {
        int pageFault = 0;
        int[] memory = new int[pageFramesSize];
        int[] countLeastUsed = new int[pageFramesSize];

        Arrays.fill(memory, -1);
        Arrays.fill(countLeastUsed, 0);

        for (int number : pageReference) {
            boolean checkResult = check(number, memory);

            for (int i = 0; i < pageFramesSize; i++) {
                countLeastUsed[i]++;
            }

            if (!checkResult) {
                int leastUsedPos = findMaxPos(countLeastUsed);
                memory[leastUsedPos] = number;
                countLeastUsed[leastUsedPos] = 0;
                pageFault++;
            }

            else {
                for (int i = 0; i < pageFramesSize; i++) {
                    if (memory[i] == number) {
                        countLeastUsed[i] = 0;
                    }
                }
            }
        }

        return pageFault;
    }

    /* Find the position of the maximum value in the array */
    public static int findMaxPos(int[] array) {
        int maxVal = array[0];
        int maxPos = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxVal) {
                maxVal = array[i];
                maxPos = i;
            }
        }

        return maxPos;
    }

}
