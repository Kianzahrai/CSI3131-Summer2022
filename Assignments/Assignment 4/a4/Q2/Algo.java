package Q2;


/**
 * Course: CSI3131 - Operating Systems Professor: Dr. Fadi Malek - Malek@uOttawa.ca Semester: Summer
 * 2022
 * 
 * @author Kian Zahrai - 300098986
 * @author Billy Bolton - 6411144
 * @since 2022-07-17
 */
public abstract class Algo {

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
