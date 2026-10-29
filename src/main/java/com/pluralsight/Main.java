package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Product> inventory = new ArrayList<Product>();

        inventory = getInventory();

        int choice;
        boolean inLoop = true;
        String userInput;
        while (inLoop) {

            System.out.println("""
                    \nWhat do you want to do?
                    1- List all products
                    2- Lookup a product by its id
                    3- Find all products within a price range
                    4- Add a new product
                    5- Quit the application
                    """);

            System.out.print("Enter command: ");
            userInput = input.nextLine();
            try{
                choice = Integer.parseInt(userInput);
                switch (choice) {
                    case 1 -> displayInventory(inventory);
                    case 2 -> searchProductId(inventory);
                    case 3 -> searchPriceRange(inventory);
                    case 4 -> addProduct(inventory);
                    case 5 -> inLoop = false;
                    default -> System.out.println("Enter a number between 1 to 5.");
                }
            } catch (Exception e){
                System.out.println("\n!!! Invalid Input !!!");
                System.out.println("Try Again!");
            }


        }
        System.out.println("Ending Program...");

    }

    public static ArrayList<Product> getInventory() {
        ArrayList<Product> inventory = new ArrayList<Product>();
        // this method loads product objects into inventory
        // and its details are not shown
        String fileName = "inventory.csv";
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            int id;
            String name;
            double price;
            String[] parts;
            while ((line = bufferedReader.readLine()) != null){
                parts = line.split("\\|");

                // handle inconsistent lines
                if (parts.length != 3){
                    System.out.println("Invalid item data");
                    continue;
                }

                // handle invalid data types
                try{
                    id = Integer.parseInt(parts[0].trim());
                    name = parts[1].trim();
                    price = Double.parseDouble(parts[2].trim());
                } catch (Exception e) {
                    System.out.println("Invalid Input");
                    continue;
                }

                Product product = new Product(id, name, price);

                inventory.add(product);

            }
            bufferedReader.close();

        } catch (IOException e) {
            System.out.printf("Unable to open %s.", fileName);

        }

        return inventory;
    }

    public static void displayInventory(ArrayList<Product> inventory){

        System.out.println("\n=== INVENTORY ===");
        System.out.println("We carry the following inventory: ");
        if (inventory.isEmpty()){
            System.out.println("\n!!! EMPTY INVENTORY !!!");
        }

        inventory.sort(Comparator.comparing(Product::getName));

        for (Product p : inventory) {
            System.out.printf("id: %d %s - Price: $%.2f%n",
                    p.getId(), p.getName(), p.getPrice());
        }
    }

    public static void searchProductId(ArrayList<Product> inventory){
        boolean found;
        int productID;

        while(true){
            found = false;
            System.out.println("\n=== Search By Product ID ===");
            System.out.println("Enter \"b\" or \"B\" to exit to Main Menu.");
            System.out.print("Product ID : ");
            String userInput = input.nextLine();

            if (userInput.equalsIgnoreCase("b")){
                System.out.println("Exiting to Main Menu...");
                break;
            }

            try{
                productID = Integer.parseInt(userInput.trim());
            } catch (Exception e){
                System.out.println("\n!!! Invalid Product ID !!!");
                continue;
            }

            for(Product p : inventory){
                if (p.getId() == productID){
                    found = true;
                    displayProduct(p);
                }
            }
            if(!found){
                System.out.println("\n Product with ID \"" + productID +
                        "\" does not exist in the inventory");
            }

        }


    }

    public static void searchPriceRange(ArrayList<Product> inventory){
        System.out.println("\n=== Search Price Range ===");

        String userInput;
        double maxPrice;
        double minPrice;
        boolean found = false;

        // continously prompt for correct price
        while (true) {
            System.out.print("\nEnter Min Price: ");
            userInput = input.nextLine();
            try {
                minPrice = Double.parseDouble(userInput);
                break;
            } catch (Exception e){
                System.out.println("\nInvalid price.");
            }
        }

        while (true) {
            System.out.print("\nEnter Max Price: ");
            userInput = input.nextLine();
            try {
                maxPrice = Double.parseDouble(userInput);
                break;
            } catch (Exception e){
                System.out.println("\nInvalid price.");
            }
        }

        for (Product p : inventory){
            if ((p.getPrice() >= minPrice) && (p.getPrice() <= maxPrice)){
                found = true;
                displayProduct(p);
            }
        }

        if(!found){
            System.out.printf("\nNo Product between \"$%.2f\" and \"$%.2f\".\n",
                    minPrice, maxPrice);
        }
    }

    public static void addProduct(ArrayList<Product> inventory){
        String userInput;
        int productID;
        String productName;
        double productPrice;

        while(true){
            System.out.println("\n=== Add A New Product ===");
            System.out.print("Enter a new Product (y/n): ");
            userInput = input.nextLine();
            if (!userInput.equalsIgnoreCase("y")){
                System.out.println("\nExiting to Main Menu...");
                break;
            }

            // accept product ID input
            while(true){
                System.out.print("Enter Product ID: ");
                userInput = input.nextLine();
                try{
                    productID = Integer.parseInt(userInput);
                    break;
                } catch (Exception e){
                    System.out.println("\nInvalid Input");
                    System.out.println("Re-Enter Input.");
                }
            }

            // accept product name input
            System.out.print("Enter Product Name: ");
            productName = input.nextLine();

            // accept product price input
            while(true){
                System.out.print("Enter Product Price: ");
                userInput = input.nextLine();

                try{
                    productPrice = Double.parseDouble(userInput);
                    break;
                } catch (Exception e){
                    System.out.println("\nInvalid Input");
                    System.out.println("Re-Enter Input.");
                }
            }

            Product product = new Product(productID, productName, productPrice);

            inventory.add(product);
        }

    }

    public static void displayProduct(Product product){
        System.out.printf("\nID: %d\nName: %s\nPrice: %.2f\n",
                product.getId(), product.getName(), product.getPrice());
    }
}