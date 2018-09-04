package acm.HDOJ_Java.Easy;

/**
 Problem Description
 Hey, welcome to HDOJ(Hangzhou Dianzi University Online Judge).

 In this problem, your task is to calculate SUM(n) = 1 + 2 + 3 + ... + n.


 Input
 The input will consist of a series of integers n, one integer per line.


 Output
 For each case, output SUM(n) in one line, followed by a blank line. You may assume the result will be in the range of 32-bit signed integer.


 Sample Input
 1
 100


 Sample Output
 1

 5050

 */

/*
import java.util.Scanner;

public class Sum_Problem {
//    public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNextInt()) {
            int a = in.nextInt();
            System.out.println((int)((1+a)*a/2)); // 因为求和公式我们最后会除以2，题目中指明了result是在32位范围内，但并不代表在除以2之前，也就是运行到((1 + n)*n)这里，值还是在范围里面。
            System.out.println();
        }
    }
}
*/

import java.util.Scanner;

public class Sum_Problem {
//    public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextInt()) {
            int a = sc.nextInt();
            int sum = 0;
            for(int i = 1; i <= a; i++) {
                sum += i;
            }
            System.out.println(sum);
            System.out.println();
        }
    }
}
