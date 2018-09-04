package acm.HDOJ_Java.Easy;

/*
Problem Description
Contest time again! How excited it is to see balloons floating around. But to tell you a secret, the judges' favorite time is guessing the most popular problem. When the contest is over, they will count the balloons of each color and find the result.

This year, they decide to leave this lovely job to you.


Input
Input contains multiple test cases. Each test case starts with a number N (0 < N <= 1000) -- the total number of balloons distributed. The next N lines contain one color each. The color of a balloon is a string of up to 15 lower-case letters.

A test case with N = 0 terminates the input and this test case is not to be processed.


Output
For each case, print the color of balloon for the most popular problem on a single line. It is guaranteed that there is a unique solution for each test case.


Sample Input
5
green
red
blue
red
red
3
pink
orange
pink
0


Sample Output
red
pink
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Let_the_Balloon_Rise {
//    public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextInt()) {
            int n = sc.nextInt();
            if(n > 0 && n < 1001) {
                Map<String, Integer> map = new HashMap<String, Integer>();
                for(int i = 0; i < n; i++) {
                    String str = sc.next();
                    if(map.containsKey(str)) {
                        map.put(str, map.get(str) + 1);
                    } else {
                        map.put(str, 1);
                    }
                }

                int max = 0;
                String color = null;
                Iterator iter = map.entrySet().iterator();
                while(iter.hasNext()) {
                    Map.Entry entry = (Map.Entry)iter.next();
                    int temp = (Integer)entry.getValue();
                    if(max < temp) {
                        max = temp;
                        color = entry.getKey().toString();
                    }
                }
                System.out.println(color);
                map.clear();
            }
        }
    }
}
