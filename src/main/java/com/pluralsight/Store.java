package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Starter code for the Online Store workshop.
 * Students will complete the TODO sections to make the program work.
 */
public class Store {

    public static void main(String[] args) {

        // Create lists for inventory and the shopping cart
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();

        // Load inventory from the data file (pipe-delimited: id|name|price)
        loadInventory("products.csv", inventory);

        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 3) {
            System.out.println("\nWelcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scanner.nextLine();                 // discard bad input
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();                     // clear newline

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }

    /**
     * Reads product data from a file and populates the inventory list.
     * File format (pipe-delimited):
     * id|name|price
     * <p>
     * Example line:
     * A17|Wireless Mouse|19.99
     */
    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        // TODO: read each line, split on "|",
        //       create a Product object, and add it to the inventory list
        try {
            BufferedReader bf = new BufferedReader(new FileReader(fileName));
            String line;

            while((line = bf.readLine()) != null) {
                String[] product  = line.split("\\|");
                String productID = product[0];
                String productName = product[1];
                double productPrice = Double.parseDouble(product[2]);
                inventory.add(new Product(productID, productName, productPrice));
            }
            bf.close();
        } catch (Exception e) {
            System.out.println("Invalid File.");
        }
    }

    /**
     * Displays all products and lets the user add one to the cart.
     * Typing X returns to the main menu.
     */
    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {
        // TODO: show each product (id, name, price),
        //       prompt for an id, find that product, add to cart
        boolean continueShopping = true;

        while(continueShopping) {
            System.out.println(inventory);
            System.out.print("Enter product ID to Add to your cart: ");
            String userInput = scanner.nextLine();
            Product product = findProductById(userInput, inventory);

            if ( product!= null ) {
                cart.add(product);
                System.out.println(product.getName() + " has been added to your cart.");
            } else {
                System.out.println(userInput + " Not found.");
            }

            System.out.println("Would you like to continue shopping? Enter \"Y\" to continue: ");
            userInput = scanner.nextLine();
            if(!userInput.equalsIgnoreCase("y")){
                continueShopping = false;
            }
        }



    }

    /**
     * Shows the contents of the cart, calculates the total,
     * and offers the option to check out.
     */
    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        // TODO:
        //   • list each product in the cart
        //   • compute the total cost
        //   • ask the user whether to check out (C) or return (X)
        //   • if C, call checkOut(cart, totalAmount, scanner)

        System.out.println(cart);
        double cartTotal = 0;
        for (Product product : cart) {
            cartTotal += product.getPrice();
        }
        System.out.printf("Total price = $%.2f\n", cartTotal);
        System.out.println("Would you like to checkout or return to main menu? \"C\" - Checkout, \"R\" - Return: ");
        String userInput = scanner.nextLine();

        if(userInput.equalsIgnoreCase("c")){
            checkOut(cart, cartTotal, scanner);
        } else {
            System.out.println("Returning to Main Menu");
        }
    }

    /**
     * Handles the checkout process:
     * 1. Confirm that the user wants to buy.
     * 2. Accept payment and calculate change.
     * 3. Display a simple receipt.
     * 4. Clear the cart.
     */
    public static void checkOut(ArrayList<Product> cart,
                                double totalAmount,
                                Scanner scanner) {
        // TODO: implement steps listed above
        System.out.println("Are you finished with your order? Enter \"C\" to confim Checkout: ");
        String userInput = scanner.nextLine();

        if(userInput.equalsIgnoreCase("c")) {
            System.out.printf("Your total is: $%.2f\n",  totalAmount);
            System.out.print("How much cash do you have? ");
            double userCash = scanner.nextDouble();
            if(userCash > totalAmount) {
                double userChange = userCash - totalAmount;
                System.out.printf("Your change is: $%.2f\n", userChange);
                System.out.println("Sales Receipt: ");
                System.out.println("Items Purchased: \n" + cart);
                System.out.printf("Total cart price: $%.2f\n", totalAmount);
                System.out.printf("Total cash provided from customer: $%.2f\n", userCash);
                System.out.printf("Change returned to customer: $%.2f\n ", userChange);
                cart.clear();
            } else {
                System.out.println("You dont have enough cash to cover your order. ");
            }
        }
    }

    /**
     * Searches a list for a product by its id.
     *
     * @return the matching Product, or null if not found
     */
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // TODO: loop over the list and compare ids
        for (Product product : inventory) {
            if (id.equalsIgnoreCase(product.getId())) {
                return product;
            }
        }
        return null;
    }
}

