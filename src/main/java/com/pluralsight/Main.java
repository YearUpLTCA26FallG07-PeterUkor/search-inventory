package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ArrayList<Product> inventory = new ArrayList<Product>();
        System.out.println("=== INVENTORY ===");

        inventory = getInventory();

        System.out.println("\nWe carry the following inventory: ");
        if (inventory.isEmpty()){
            System.out.println("\nEMPTY INVENTORY");
        }

        for (Product p : inventory) {
            System.out.printf("id: %d %s - Price: $%.2f%n",
                    p.getId(), p.getName(), p.getPrice());
        }
    }

    public static ArrayList<Product> getInventory() {
        Scanner input = new Scanner(System.in);
        ArrayList<Product> inventory = new ArrayList<Product>();
        // this method loads product objects into inventory
        // and its details are not shown
        String choice;
        int id;
        String name;
        double price;
        while (true){
            System.out.print("Would you like to add an item to the inventory (y/n): ");
            choice = input.nextLine();

            if (!choice.equalsIgnoreCase("y")){
                System.out.println("\nThank you.");
                break;
            }
            System.out.print("Enter Item ID: ");
            id = input.nextInt();
            input.nextLine();

            System.out.print("Enter Item Name: ");
            name = input.nextLine();

            System.out.print("Enter Item price: ");
            price = input.nextDouble();
            input.nextLine();

            Product product = new Product(id, name, price);

            inventory.add(product);

        }

        return inventory;
    }
}