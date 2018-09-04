package acm.HDOJ_Java.Easy;
/*
Problem Description
I have a very simple problem for you. Given two integers A and B, your job is to calculate the Sum of A + B.


Input
The first line of the input contains an integer T(1<=T<=20) which means the number of test cases. Then T lines follow, each line consists of two positive integers, A and B. Notice that the integers are very large, that means you should not process them by using 32-bit integer. You may assume the length of each integer will not exceed 1000.


Output
For each test case, you should output two lines. The first line is "Case #:", # means the number of the test case. The second line is the an equation "A + B = Sum", Sum means the result of A + B. Note there are some spaces int the equation. Output a blank line between two test cases.


Sample Input
2
1 2
112233445566778899 998877665544332211


Sample Output
Case 1:
1 + 2 = 3

Case 2:
112233445566778899 + 998877665544332211 = 1111111111111111110
 */
import java.math.BigInteger;
import java.util.Scanner;

public class A_add_B_Problem2 {
//    public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        int i = 0;
        while(i < count) {
            BigInteger a = new BigInteger(sc.next());
            BigInteger b = new BigInteger(sc.next());
            System.out.println("Case " + (i+1) + ":");
            System.out.println(a + " + " + b + " = " + a.add(b));
            if(i < (count - 1)) {
                System.out.println();
            }
            i++;
        }
    }
}
