package com.javatechie.spring.consul.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Consolidated practice examples for interview preparation.
 * Contains various coding challenges and solutions.
 */
public class PracticeExamples {

    // ========================================================================
    // EXAMPLE 1: Palindromic Substrings
    // ========================================================================
    /**
     * Write a program that prints all palindromic substrings from a given string.
     * The program should only consider substrings with a minimum length of 3 characters.
     * Input: "racecarannakayak"
     */
    public static void findPalindromicSubstrings() {
        String input = "racecarannakayak";
        List<String> res = new ArrayList<>();
        
        for (int i = 0; i < input.length(); i++) {
            for (int j = i + 1; j < input.length(); j++) {
                String subtemp = input.substring(i, j);
                System.out.println("substring: " + subtemp);
                if (isValidPalindrome(subtemp)) {
                    res.add(subtemp);
                }
            }
        }

        List<String> threeCharsString = res.stream()
                .filter(s -> s.length() >= 3)
                .collect(Collectors.toList());
        System.out.println("threeCharsString:" + threeCharsString);
    }

    private static boolean isValidPalindrome(String sub) {
        int i = 0;
        int j = sub.length() - 1;

        while (i < j) {
            if (sub.charAt(i) != sub.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }

    // ========================================================================
    // EXAMPLE 2: Ping-Pong Threading
    // ========================================================================
    /**
     * Multi-threaded ping-pong example using wait/notify.
     * Thread 1 prints "Ping", Thread 2 prints "Pong Pong" alternately.
     */
    static final Object lock = new Object();
    static volatile boolean isPingTurn = true; // Start with ping

    public static void runPingPong() {
        Thread thread1 = new Thread(new PingThread());
        Thread thread2 = new Thread(new PongThread());

        thread1.start();
        thread2.start();
    }

    public static void waitForTurn() {
        try {
            lock.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static class PingThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) { // Repeat 3 times
                synchronized (lock) {
                    while (!isPingTurn) {
                        waitForTurn();
                    }
                    System.out.print("Ping ");
                    isPingTurn = false;
                    lock.notify();
                }
            }
        }
    }

    static class PongThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) { // Repeat 3 times
                synchronized (lock) {
                    while (isPingTurn) {
                        waitForTurn();
                    }
                    System.out.print("Pong ");
                    System.out.print("Pong ");
                    isPingTurn = true;
                    lock.notifyAll();
                }
            }
        }
    }

    // ========================================================================
    // EXAMPLE 3: Add Two Numbers (Linked List)
    // ========================================================================
    /**
     * Add Two Numbers - You are given two non-empty linked lists, l1 and l2,
     * where each represents a non-negative integer.
     * 
     * The digits are stored in reverse order, e.g. the number 123 is represented
     * as 3 -> 2 -> 1 in the linked list.
     * 
     * Example 1:
     * Input: l1 = [1,2,3], l2 = [4,5,6]
     * Output: [5,7,9]
     * Explanation: 321 + 654 = 975.
     */
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    public static void addTwoNumbers() {
        // Create first linked list: 1 -> 2 -> 3 (represents 321)
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        node1.next = node2;
        node2.next = node3;

        // Create second linked list: 4 -> 5 -> 6 (represents 654)
        Node onode1 = new Node(4);
        Node onode2 = new Node(5);
        Node onode3 = new Node(6);
        onode1.next = onode2;
        onode2.next = onode3;

        // Convert first list to number
        Node head1 = node1;
        StringBuilder sb = new StringBuilder();
        while (head1 != null) {
            sb.append(head1.data);
            head1 = head1.next;
        }
        System.out.println("concatenated String1:" + sb);
        sb.reverse();
        System.out.println("reversed String1:" + sb);

        // Convert second list to number
        Node head2 = onode1;
        StringBuilder sb2 = new StringBuilder();
        while (head2 != null) {
            sb2.append(head2.data);
            head2 = head2.next;
        }
        System.out.println("concatenated String2:" + sb2);
        sb2.reverse();
        System.out.println("reversed String2:" + sb2);

        // Add the numbers
        int sum = Integer.parseInt(sb.toString()) + Integer.parseInt(sb2.toString());
        System.out.println("sum of reversed String:" + sum);

        // Convert sum back to linked list
        int num = sum;
        Node res = null;
        Node prev = null;
        Node headres = null;
        while (num != 0) {
            res = new Node(num % 10);
            if (prev == null) {
                headres = res;
            }
            if (prev != null) {
                prev.next = res;
            }
            prev = res;
            num = num / 10;
        }

        // Print result
        while (headres != null) {
            System.out.println(headres.data);
            headres = headres.next;
        }
    }

    // ========================================================================
    // EXAMPLE 4: Stock Profit Calculation
    // ========================================================================
    /**
     * Calculate maximum profit from stock prices.
     * Multiple buy/sell transactions allowed.
     */
    public static void calculateStockProfit() {
        List<Integer> prices = Arrays.asList(1, 5, 3, 5, 2); // Expected: 4 + 2 = 6
        int[] p = {1, 5, 3, 5, 2};
        
        int minPriceSoFar = prices.get(0);
        int maxProfit = Integer.MIN_VALUE;
        
        for (int i = 1; i < prices.size(); i++) {
            int currentProfit = (prices.get(i) - minPriceSoFar);
            maxProfit = Math.max(currentProfit, maxProfit);
            minPriceSoFar = Math.min(prices.get(i), minPriceSoFar);
        }

        System.out.println("maxProfit: " + maxProfit(p));
    }

    public static int maxProfit(int[] prices) {
        int profit = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }

        return profit;
    }

    // ========================================================================
    // MAIN METHOD - Run all examples
    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== Example 1: Palindromic Substrings ===");
        findPalindromicSubstrings();
        
        System.out.println("\n=== Example 2: Ping-Pong Threading ===");
        runPingPong();
        
        System.out.println("\n\n=== Example 3: Add Two Numbers (Linked List) ===");
        addTwoNumbers();
        
        System.out.println("\n=== Example 4: Stock Profit Calculation ===");
        calculateStockProfit();
    }
}
