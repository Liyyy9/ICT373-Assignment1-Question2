/*
 * Title: ICT373 Assignment 1 - Question 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 04 October 2026
 * File Name: PrintHelper.java
 * Purpose: Provides methods for printing the menu, supplements, customers, and weekly/monthly emails to the console.
 * Assumptions: None
 * Input: None
 * Output: Console text such as the menu, customer/supplement listings, and emails.
 */
package ict373assignment1q2;

/**
 * Provides helper methods for printing output to the console.
 * Handles the menu, supplement and customer listings, and weekly/monthly emails.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class PrintHelper {

    /**
     * Prints the header for a given week.
     *
     * @param week the week number to display
     */
    public static void printWeekHeader(int week) {
        System.out.printf("==================== WEEK %d ====================\n", week);
    }

    /**
     * Prints the weekly email for a customer, listing their subscribed supplements.
     *
     * @param customer the customer to print the email for
     */
    public static void printWeeklyEmail(Customer customer) {
        System.out.printf("To: %s\n", customer.getEmail());
        System.out.printf("Hello %s,\n"
                + "Your magazine is ready!\n"
                + "Your subscribed supplements:\n", customer.getName());
        for (Supplement supplement : customer.getSupplements()) {
            System.out.printf("- %s\n", supplement.getName());
        }
        System.out.println("Enjoy your magazine!");
        System.out.println("");
        System.out.println("-------------------------------");
        System.out.println("");
    }

    /**
     * Prints the column header used in the billing tables.
     */
    public static void printItemHeader() {
        System.out.printf("%-20s %-15s %-15s%n", "ITEM", "WEEKLY COST", "MONTHLY COST");
    }

    /**
     * Prints the monthly billing email for a paying customer.
     * Shows their own charges and the charges for each of their associate customers.
     *
     * @param customer the paying customer to bill
     * @param magazine the magazine containing the weekly cost
     */
    public static void printMonthlyEmail(PayingCustomer customer, Magazine magazine) {
        System.out.printf("\nTo: %s\n", customer.getEmail());
        System.out.println("");
        System.out.printf("Hello %s,\n", customer.getName());

        // Show the payment method name based on the stored type.
        if (customer.getPaymentMethod().getType() == 1) {
            System.out.printf("Payment Method: Credit Card - %s\n",
                    customer.getPaymentMethod().getNumber());
        } else {
            System.out.printf("Payment Method: Direct Debit - %s\n", customer.getPaymentMethod().getNumber());
        }
        System.out.println("");
        System.out.println("Here are your charges for this month:\n"
                + "\n"
                + "Your subscription:");

        // Print the table header, then the customer's own magazine and supplement charges.
        printItemHeader();
        double total = 0;

        System.out.printf("%-20s $%-14.2f $%-14.2f%n",
                "Main Magazine",
                magazine.getWeeklyCost(),
                magazine.getWeeklyCost() * 4);

        // Calculate four weeks of subscription costs for the main magazine.
        total += magazine.getWeeklyCost() * 4;

        for (Supplement supplement : customer.getSupplements()) {
            System.out.printf("%-20s $%-14.2f $%-14.2f%n",
                    supplement.getName(),
                    supplement.getWeeklyCost(),
                    supplement.getWeeklyCost() * 4);

            // Calculate four weeks of cost for this supplement.
            total += supplement.getWeeklyCost() * 4;
        }
        System.out.println("");
        System.out.println("Your Associates' Subscriptions:");
        System.out.println("");

        if (customer.getAssociates().isEmpty()) {
            // No associates linked, so skip straight to the overall total.
            System.out.println("No associates found\n\n");
        } else {
            // Add each associate's magazine and supplement costs to the paying customer's total.
            for (AssociateCustomer associate : customer.getAssociates()) {
                System.out.printf("%s\n", associate.getName());
                printItemHeader();

                System.out.printf("%-20s $%-14.2f $%-14.2f%n",
                        "Main Magazine",
                        magazine.getWeeklyCost(),
                        magazine.getWeeklyCost() * 4);

                total += magazine.getWeeklyCost() * 4;

                for (Supplement supplement : associate.getSupplements()) {
                    System.out.printf("%-20s $%-14.2f $%-14.2f%n",
                            supplement.getName(),
                            supplement.getWeeklyCost(),
                            supplement.getWeeklyCost() * 4);

                    total += supplement.getWeeklyCost() * 4;
                }
                System.out.println("");
            }
        }
        // Print the combined total of the paying customer's and all associates' charges.
        System.out.printf("Your overall total = $%.2f\n", total);
        System.out.println("");
        System.out.println("--------------------------------------------------------------");
        System.out.println("");

    }

    /**
     * Prints the main menu options.
     */
    public static void printMenu() {
        System.out.println(
                "========== Magazine Service ==========\n"
                + "1. Display supplements\n"
                + "2. Display customers\n"
                + "3. Print weekly emails (4 weeks)\n"
                + "4. Print monthly billing email\n"
                + "5. Add new customer\n"
                + "6. Remove a customer\n"
                + "7. Quit\n"
                + "======================================\n");
    }

    /**
     * Prints all supplements available with the magazine.
     *
     * @param magazine the magazine containing the supplements
     */
    public static void printSupplements(Magazine magazine) {
        System.out.println("\n========== Available Supplements ==========");
        for (Supplement supplement : magazine.getSupplements()) {
            System.out.printf("%-30s $%.2f\n",
                    supplement.getName(),
                    supplement.getWeeklyCost());
        }
        System.out.println("===========================================\n");
    }

    /**
     * Prints details of all customers, including their type, supplements,
     * and related paying customer or associate customers.
     *
     * @param magazine the magazine containing the customers
     */
    public static void printCustomers(Magazine magazine) {
        for (Customer customer : magazine.getCustomers()) {
            System.out.printf("\nName: %s\n", customer.getName());
            System.out.printf("Email: %s\n", customer.getEmail());

            // Check whether the selected customer is a paying customer.
            if (customer instanceof PayingCustomer) {
                PayingCustomer paying = (PayingCustomer) customer;
                System.out.println("Type: Paying Customer");
                System.out.println("Supplements:");

                for (Supplement supplement : customer.getSupplements()) {
                    System.out.printf("- %s\n", supplement.getName());
                }

                if (paying.getPaymentMethod().getType() == 1) {
                    System.out.println("Payment Method: Credit Card");
                } else {
                    System.out.println("Payment Method: Direct Debit");
                }

                System.out.println("Associates:");

                if (paying.getAssociates().isEmpty()) {
                    // This paying customer has no linked associates.
                    System.out.println("- None");
                } else {
                    for (AssociateCustomer associate : paying.getAssociates()) {
                        System.out.printf("- %s\n", associate.getName());
                    }
                }

                System.out.println("-------------------------------");

            } else if (customer instanceof AssociateCustomer) {
                // Otherwise, check whether the selected customer is an associate customer.
                AssociateCustomer associate = (AssociateCustomer) customer;

                System.out.println("Type: Associate Customer");

                System.out.println("Supplements:");
                for (Supplement supplement : customer.getSupplements()) {
                    System.out.printf("- %s\n", supplement.getName());
                }

                // Show who pays this associate's subscription.
                System.out.printf("Paid by: %s\n", associate.getPayingCustomer().getName());
                System.out.println("-------------------------------");

            }
        }
        System.out.println("");
    }
}
