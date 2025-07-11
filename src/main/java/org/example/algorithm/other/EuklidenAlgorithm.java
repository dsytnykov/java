package org.example.algorithm.other;

public class EuklidenAlgorithm {

    public static int gcd(int a, int b) {
        if(b == 0) return a;
        //gcd(b, a % b); - recursive solution
        while(b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
