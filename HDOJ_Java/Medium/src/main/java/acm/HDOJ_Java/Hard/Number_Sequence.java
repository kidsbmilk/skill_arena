package acm.HDOJ_Java.Hard;

/*
Problem Description
A number sequence is defined as follows:

f(1) = 1, f(2) = 1, f(n) = (A * f(n - 1) + B * f(n - 2)) mod 7.

Given A, B, and n, you are to calculate the value of f(n).


Input
The input consists of multiple test cases. Each test case contains 3 integers A, B and n on a single line (1 <= A, B <= 1000, 1 <= n <= 100,000,000). Three zeros signal the end of input and this test case is not to be processed.


Output
For each test case, print the value of f(n) on a single line.


Sample Input
1 1 3
1 2 10
0 0 0


Sample Output
2
5

思路参考了以下两篇文章：
http://blog.csdn.net/Dragon_fxl/article/details/54929092
http://blog.csdn.net/DTL66/article/details/53084721
当然，假定49之内必出现周期。
 */

import java.util.Scanner;

public class Number_Sequence {
//    public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextInt()) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int n = sc.nextInt();
            if(a == 0 && b == 0 && n == 0) {
                break;
            }
            if(n < 3) {
                System.out.println(1);
                continue;
            }
            if(a % 7 == 0 && b %7 == 0) {
                System.out.println(0);
                continue;
            }
            int[] f = new int[53];
            f[2] = f[1] = 1;
            f[3] = (a + b) % 7;
            f[4] = (a * f[3] + b) % 7;
            for(int i = 5; i < n + 1; i++) {
                f[i] = (a * f[i - 1] + b * f[i - 2]) % 7;
                if(f[i] == f[4] && f[i - 1] == f[3]) {
                    n = (n - 3) % (i - 4) + 3; //注意 i - 4 才是周期，加3是因为f(3)才是周期的第一个数。
                    break;
                }
            }
            System.out.println(f[n]);
        }
    }
}
