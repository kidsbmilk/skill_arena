package acm.HDOJ_Java.DP;

/*
Problem Description
Given a sequence a[1],a[2],a[3]......a[n], your job is to calculate the max sum of a sub-sequence. For example, given (6,-1,5,4,-7), the max sum in this sequence is 6 + (-1) + 5 + 4 = 14.


Input
The first line of the input contains an integer T(1<=T<=20) which means the number of test cases. Then T lines follow, each line starts with a number N(1<=N<=100000), then N integers followed(all the integers are between -1000 and 1000).


Output
For each test case, you should output two lines. The first line is "Case #:", # means the number of the test case. The second line contains three integers, the Max Sum in the sequence, the start position of the sub-sequence, the end position of the sub-sequence. If there are more than one result, output the first one. Output a blank line between two cases.


Sample Input
2
5 6 -1 5 4 -7
7 0 6 -1 1 -6 7 -5


Sample Output
Case 1:
14 1 4

Case 2:
7 1 6

 */

import java.util.Scanner;

public class Max_Sum {
//    public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextInt()) {
            int n = sc.nextInt();
            for(int i = 0; i < n; i++) {
                int count = sc.nextInt();
                long[] a = new long[count + 1];
                for(int j = 1; j <= count; j++) {
                    a[j] = sc.nextInt();
                }

                long[] b = new long[count + 1]; // 记录到当前j时的最大和
                long max = b[1] = a[1];
                int start = 1, end = 1;
                for(int j = 2; j <= count; j++) {
                    if(b[j-1] >= 0) {
                        b[j] = b[j-1] + a[j];
                    } else {
                        b[j] = a[j];
                    }
                    if(max < b[j]) {
                        end = j;
                        max = b[j];
                    }
                }
                int sum = 0;
                for(int j = end; j >= 1; j--) { // 从后相加，找到子序列的起始位置
                    sum += a[j];
                    if(sum == max) {
                        start = j;
//                        break; // 这个不能加break，因为要找到第一个
                        /*
                        如果加break，对于例子：
                            0 6 -1 1 -6 7 -5
                        输出的是：7 6 6
                        正确的输出应该是：7 1 6
                         */
                    }
                }
                System.out.println("Case " + (i + 1) + ":");
                System.out.println(max + " " + start + " " + end);
                if(i != n - 1) {
                    System.out.println();
                }
            }
            break;
        }
    }
}
