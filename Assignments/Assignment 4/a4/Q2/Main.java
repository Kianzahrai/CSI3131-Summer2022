package Q2;


import java.util.Scanner;

/**
 * Course: CSI3131 - Operating Systems Professor: Dr. Fadi Malek - Malek@uOttawa.ca Semester: Summer
 * 2022
 * 
 * @author Kian Zahrai - 300098986
 * @author Billy Bolton - 6411144
 * @since 2022-07-17
 */
public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean pageFramesSizeCheck = false;

        int pageFramesSize;
        int pageReferenceSize;

        while (!pageFramesSizeCheck) {
            System.out.print("Please enter the number of page frames greater than 1: ");

            String numPageFrames = scan.nextLine();
            pageFramesSize = Integer.parseInt(numPageFrames);

            if (pageFramesSize < 1) {
                System.out.println("The integer is invalid.");
            }

            else {
                pageFramesSizeCheck = true;
            }
        }

        System.out.println();
        System.out.print("Please enter an integer for the size of page-reference string: ");

        String numPageReference = scan.nextLine();
        pageReferenceSize = Integer.parseInt(numPageReference);
        int[] pageReference = new int[pageReferenceSize];

        for (int i = 0; i < pageReferenceSize; i++) {
            int randomPage = (int) (Math.random() * 10);
            pageReference[i] = randomPage;
        }

        System.out.print("The page-reference string is: ");

        for (int i = 0; i < pageReferenceSize; i++) {
            System.out.print(pageReference[i] + " ");
        }

        int fifoFault = FIFO.calculateFaults(pageReference, pageReferenceSize);
        int lruFault = LRU.calculateFaults(pageReference, pageReferenceSize);

        System.out.println();
        System.out.println("There is/are " + String.valueOf(fifoFault)
                + " page fault(s) in FIFO page-replacement algorithm.");
        System.out.println("There is/are " + String.valueOf(lruFault)
                + " page fault(s) in LRU page-replacement algorithm.");

    }

}
