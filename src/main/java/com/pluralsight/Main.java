package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ArrayList<Product> inventory = new ArrayList<Product>();


        inventory = getInventory();

        displayInventory(inventory);


    }

    public static ArrayList<Product> getInventory() {
        ArrayList<Product> inventory = new ArrayList<Product>();
        // this method loads product objects into inventory
        // and its details are not shown
        try{
            FileReader fileReader = new FileReader("inventory.csv");
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

        } catch (IOException e) {

        }

        return inventory;
    }

    public static void displayInventory(ArrayList<Product> inventory){

        System.out.println("=== INVENTORY ===");
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
}