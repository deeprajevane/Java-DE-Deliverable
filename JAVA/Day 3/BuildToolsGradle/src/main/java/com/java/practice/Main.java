package com.java.practice;
//import org.slf4j.LoggerFactory;
import java.util.logging.Logger;
public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());

    // Recursive method to find the nth Fibonacci number
    public static long fibonacci(long n) {
        if (n <= 1) {
            return n; // base cases: 0 and 1
        }
        return fibonacci(n - 1) + fibonacci(n - 2); // recursive call
    }
    public static void main(String[] args) {
        long count = 50;
        // Start measuring time
        long startTime = System.nanoTime();
        System.out.println("Fibonacci Series up to " + count + " terms:");
        for (long i = 0; i < count; i++) {
            System.out.print(fibonacci(i) + " ");
        }
        // End measuring time
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;
        System.out.println("\nExecution Time: " + executionTime + " nanoseconds");
        System.out.println("Execution Time: " + (executionTime / 1_000_000.0) + " milliseconds");
    }
}