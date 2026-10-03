/*
 * Title: ICT373 Assignment 1 - Question 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 04 October 2026
 * File Name: MagazineHelper.java
 * Purpose: Provides methods for constructing and modifying magazine data.
 * Assumptions: A paying customer must exist before an associate customer can be linked to them. A paying customer with associates cannot be removed.
 * Input: Keyboard input entered by the user through the Scanner, used when adding or removing customers.
 * Output: Console messages confirming that a customer has been added or removed.
 */

package ict373assignment1q2;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Provides methods for constructing and modifying magazine data.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class MagazineHelper {

    /**
     * Constructs the initial supplements.
     *
     * @param magazine magazine containing the supplements
     */
    public static void constructSupplements(Magazine magazine) {
        Supplement singing = new Supplement("Singing", 10.00);
        Supplement cooking = new Supplement("Cooking", 5.00);
        Supplement gardening = new Supplement("Gardening", 15.00);
        Supplement beauty = new Supplement("Korean Beauty", 20.00);

        magazine.addSupplement(singing);
        magazine.addSupplement(cooking);
        magazine.addSupplement(gardening);
        magazine.addSupplement(beauty);
    }

    /**
     * Constructs the initial customers.
     *
     * @param magazine magazine containing the customers
     */
    public static void constructCustomers(Magazine magazine) {
        // Create the paying customers first.
        PaymentMethod payment1 = new PaymentMethod(1, "478619485794");
        PayingCustomer nayeon = new PayingCustomer(
                "Im Nayeon",
                "im.nayeon@gmail.com",
                payment1);
        nayeon.addSupplement(magazine.getSupplements().get(0));
        nayeon.addSupplement(magazine.getSupplements().get(2));

        PaymentMethod payment2 = new PaymentMethod(2, "9876543210");
        PayingCustomer jihyo = new PayingCustomer(
                "Park Jihyo",
                "park.jihyo@hotmail.com",
                payment2);
        jihyo.addSupplement(magazine.getSupplements().get(1));
        jihyo.addSupplement(magazine.getSupplements().get(3));

        PaymentMethod payment3 = new PaymentMethod(1, "5423123412345678");
        PayingCustomer jeongyeon = new PayingCustomer(
                "Yoo Jeongyeon",
                "yoo.jeongyeon@yahoo.com",
                payment3);
        jeongyeon.addSupplement(magazine.getSupplements().get(0));
        jeongyeon.addSupplement(magazine.getSupplements().get(1));
        jeongyeon.addSupplement(magazine.getSupplements().get(3));

        // Create the associate customers and link each one to a paying customer.
        AssociateCustomer mina = new AssociateCustomer(
                "Myoui Mina",
                "myoui.mina@gmail.com",
                nayeon);
        mina.addSupplement(magazine.getSupplements().get(1));
        mina.addSupplement(magazine.getSupplements().get(3));

        AssociateCustomer sana = new AssociateCustomer(
                "Minatozaki Sana",
                "minatozaki.sana@gmail.com",
                nayeon);
        sana.addSupplement(magazine.getSupplements().get(0));
        sana.addSupplement(magazine.getSupplements().get(1));

        AssociateCustomer momo = new AssociateCustomer(
                "Hirai Momo",
                "hirai.momo@yahoo.com",
                jihyo);
        momo.addSupplement(magazine.getSupplements().get(2));

        // Add each associate to their paying customer's associate list.
        nayeon.addAssociateCustomer(mina);
        nayeon.addAssociateCustomer(sana);
        jihyo.addAssociateCustomer(momo);

        // Add each customer to the magazine
        magazine.addCustomer(nayeon);
        magazine.addCustomer(jihyo);
        magazine.addCustomer(jeongyeon);
        magazine.addCustomer(mina);
        magazine.addCustomer(sana);
        magazine.addCustomer(momo);
    }

    /**
     * Adds a new customer.
     *
     * @param magazine magazine containing the customers
     * @param scanner Scanner used to read user input
     */
    public static void addCustomer(Magazine magazine, Scanner scanner) {
        String name;
        String email;
        int custType;
        int type;
        String number;
        int payingCustomerChoice;
        int supplementChoice;

        System.out.println("======== Add New Customer ========");

        System.out.println("\nEnter customer name: ");
        name = scanner.nextLine();

        System.out.println("\nEnter email:");
        email = scanner.nextLine();

        System.out.println("\nSelect Customer Type:"
                + "\n1. Paying Customer"
                + "\n2. Associate Customer"
                + "\nEnter 1 or 2:");

        custType = InputHelper.getInt(scanner, 1, 2);

        Customer newCustomer = null;

        // Create a paying customer with their own payment method.
        if (custType == 1) {
            System.out.println("\nSelect payment method:"
                    + "\n1. Credit Card"
                    + "\n2. Direct Debit"
                    + "\nEnter 1 or 2:");

            type = InputHelper.getInt(scanner, 1, 2);

            if (type == 1) {
                System.out.println("\nEnter credit card number:");
            } else if (type == 2) {
                System.out.println("\nEnter bank account number:");
            } else {
                System.out.println("Invalid option entered");
            }

            number = scanner.nextLine();

            PaymentMethod newPayment = new PaymentMethod(type, number);
            newCustomer = new PayingCustomer(name, email, newPayment);

        // Create an associate customer with a paying customer
        } else if (custType == 2) {
            System.out.println("\nSelect the Paying Customer:");

            ArrayList<PayingCustomer> payingCustomers = new ArrayList<>();

            // Find all existing paying customers so the user can choose who to link the associate to.
            for (Customer customer : magazine.getCustomers()) {
                if (customer instanceof PayingCustomer) {
                    PayingCustomer paying = (PayingCustomer) customer;
                    payingCustomers.add(paying);

                    System.out.printf("%d. %s\n",
                            payingCustomers.size(),
                            paying.getName());
                }
            }

            System.out.println("Enter choice:");

            payingCustomerChoice = InputHelper.getInt(
                    scanner, 1, payingCustomers.size());

            PayingCustomer selectedPayingCustomer
                    = payingCustomers.get(payingCustomerChoice - 1);

            newCustomer = new AssociateCustomer(
                    name, email, selectedPayingCustomer);

            // Link the new associate customer back to the paying customer.
            selectedPayingCustomer.addAssociateCustomer(
                    (AssociateCustomer) newCustomer);

        } else {
            System.out.println("Invalid option entered.");
            return;
        }

        // Keep letting the user add supplements until they choose "0. Done".
        do {
            System.out.println("\nSelect Supplements");
            int count = 1;

            // Display supplement list to user
            for (Supplement supplement : magazine.getSupplements()) {
                System.out.printf("%d. %s\n",
                        count, supplement.getName());
                count++;
            }

            System.out.println("0. Done\n");
            System.out.println("Enter choice:");

            supplementChoice = InputHelper.getInt(
                    scanner, 0, magazine.getSupplements().size());

            if (supplementChoice != 0) {

                newCustomer.addSupplement(
                        magazine.getSupplements()
                                .get(supplementChoice - 1));

                System.out.printf("\n%s added.",
                        magazine.getSupplements()
                                .get(supplementChoice - 1)
                                .getName());
            }

        } while (supplementChoice != 0);

        magazine.addCustomer(newCustomer);

        System.out.printf("\n%s has been added successfully.\n",
                newCustomer.getName());
        System.out.println("");
    }

    /**
     * Removes an existing customer.
     *
     * @param magazine magazine containing the customers
     * @param scanner Scanner used to read user input
     */
    public static void removeCustomer(Magazine magazine, Scanner scanner) {
        System.out.println("\nList of customers:");

        int count = 1;
        int choice;
        Customer selectedCustomer;

        for (Customer customer : magazine.getCustomers()) {
            System.out.printf("%d. %s\n",
                    count, customer.getName());
            count++;
        }

        System.out.println("Enter customer to remove:");

        choice = InputHelper.getInt(
                scanner, 1, magazine.getCustomers().size());

        selectedCustomer
                = magazine.getCustomers().get(choice - 1);

        // If removing an associate customer, unlink them from their paying customer first.
        if (selectedCustomer instanceof AssociateCustomer) {
            AssociateCustomer associate
                    = (AssociateCustomer) selectedCustomer;

            PayingCustomer paying
                    = associate.getPayingCustomer();

            paying.removeAssociateCustomer(associate);
        }

        // A paying customer with associates cannot be removed, since the associates depend on them.
        if (selectedCustomer instanceof PayingCustomer) {
            PayingCustomer paying
                    = (PayingCustomer) selectedCustomer;

            if (!paying.getAssociates().isEmpty()) {
                System.out.println("Cannot remove customer. "
                        + "This paying customer still has associate customers.\n");
                return;
            }
        }

        magazine.removeCustomer(selectedCustomer);

        System.out.printf("%s has been removed successfully\n",
                selectedCustomer.getName());
        System.out.println("");
    }
}