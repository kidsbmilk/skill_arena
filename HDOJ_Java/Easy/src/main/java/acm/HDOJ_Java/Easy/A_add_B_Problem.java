package acm.HDOJ_Java.Easy;

/**
 *
 Problem Description
 Calculate A + B.


 Input
 Each line will contain two integers A and B. Process to end of file.


 Output
 For each case, output A + B in one line.


 Sample Input
 1 1


 Sample Output
 2

 */

import java.util.Scanner;

public class A_add_B_Problem {
//    public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextInt()) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            System.out.println(a + b);
        }
    }
}
