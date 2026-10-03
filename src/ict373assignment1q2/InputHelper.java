/*
 * Title: ICT373 Assignment 1 - Question 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 04 October 2026
 * File Name: InputHelper.java
 * Purpose: Provides methods for validating user input.
 * Assumptions: The Scanner passed in is already open and connected to System.in.
 * Input: Keyboard input entered by the user through the Scanner.
 * Output: None
 */
package ict373assignment1q2;

import java.util.Scanner;

/**
 * Provides helper methods for validating user input.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class InputHelper {

    /**
     * Gets an integer input within the specified range.
     *
     * @param scanner Scanner used to read user input
     * @param min minimum accepted value
     * @param max maximum accepted value
     * @return validated integer input
     */
    public static int getInt(Scanner scanner, int min, int max) {

        // Keep asking until the user enters a valid number in range.
        while (true) {

            if (!scanner.hasNextInt()) {
                System.out.printf(
                        "Invalid input. Please enter a number between %d and %d:\n",
                        min, max);
                scanner.nextLine();
                continue;
            }

            int input = scanner.nextInt();
            scanner.nextLine();

            if (input < min || input > max) {
                System.out.printf(
                        "Invalid choice. Please enter a number between %d and %d:\n",
                        min, max);
                continue;
            }

            return input;
        }
    }
}
