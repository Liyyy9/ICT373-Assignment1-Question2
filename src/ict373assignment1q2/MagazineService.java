/*
 * Title: ICT373 Assignment 1 - Question 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 04 October 2026
 * File Name: MagazineService.java
 * Purpose: Runs the magazine subscription service.
 * Assumptions: The user enters a menu option between 1 and 7.
 * Input: Keyboard input entered by the user to select menu options.
 * Output: Console menu, customer/supplement listings, and weekly/monthly emails.
 */
package ict373assignment1q2;

import java.util.Scanner;

/**
 * Runs the magazine subscription service and controls the main menu.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class MagazineService {

    /**
     * Main method used to run the magazine service.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrintHelper.displayStudentDetails();

        Magazine magazine = new Magazine();
        Scanner scanner = new Scanner(System.in);

        // Set up the starting supplements and customers before the menu loop begins.
        MagazineHelper.constructSupplements(magazine);
        MagazineHelper.constructCustomers(magazine);

        int input;

        // Keep showing the menu until the user chooses to quit (option 7).
        do {
            PrintHelper.printMenu();
            System.out.println("Enter your option: ");

            input = InputHelper.getInt(scanner, 1, 7);

            switch (input) {
                case 1:
                    // Show all supplements available with the magazine.
                    PrintHelper.printSupplements(magazine);
                    break;

                case 2:
                    // Show all current customers and their details.
                    PrintHelper.printCustomers(magazine);
                    break;

                case 3:
                    // Calculate four weeks of subscription costs by printing the weekly email for each customer, four times.
                    for (int week = 1; week <= 4; week++) {
                        PrintHelper.printWeekHeader(week);

                        for (Customer customer
                                : magazine.getCustomers()) {
                            PrintHelper.printWeeklyEmail(customer);
                        }
                    }
                    break;

                case 4:
                    // Check whether the selected customer is a paying customer, since only they get billed.
                    for (Customer customer
                            : magazine.getCustomers()) {

                        if (customer instanceof PayingCustomer) {
                            PrintHelper.printMonthlyEmail(
                                    (PayingCustomer) customer,
                                    magazine);
                        }
                    }
                    break;

                case 5:
                    // Add a new paying or associate customer through the helper.
                    MagazineHelper.addCustomer(
                            magazine, scanner);
                    break;

                case 6:
                    // Remove an existing customer through the helper.
                    MagazineHelper.removeCustomer(
                            magazine, scanner);
                    break;

                case 7:
                    System.out.println(
                            "Exiting Magazine Service. Goodbye!\n");
                    break;
            }

        } while (input != 7);

        scanner.close();
    }
}
