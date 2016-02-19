package com.fmi.jones.indiana;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class IndianaJones {
    public static void execute(int numberOfItems, int capacity, List<Item> items) {
        long startTime = System.currentTimeMillis();
        DynamicProgramingSolution dps = new DynamicProgramingSolution(numberOfItems, capacity);
        dps.solve(numberOfItems, capacity, items);
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time of DynamicProgramming Solution: " + (endTime - startTime));
        System.out.println();
        startTime = System.currentTimeMillis();
        BranchAndBoundSolution bab = new BranchAndBoundSolution(items, capacity);
        bab.solve();
        endTime = System.currentTimeMillis();
        System.out.println("Total execution time of BranchAndBound Solution: " + (endTime - startTime));
    }

    public static void main(String[] args) {
        List<Item> items = new ArrayList<Item>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose 0 for input from console and 1 for input from file");
        int option = sc.nextInt();
        switch (option) {
        case 0: {

            System.out.println("Enter Capacity");
            int capacity = sc.nextInt();
            System.out.println("Enter Number of items");
            int numberOfItems = sc.nextInt();
            sc.nextLine();
            for (int i = 0; i < numberOfItems; i++) {
                String input = sc.nextLine();
                String[] splittedInput = input.trim().split("\\s+");
                items.add(new Item(splittedInput[0], Integer.parseInt(splittedInput[1]), Integer
                        .parseInt(splittedInput[2])));
            }
            execute(numberOfItems, capacity, items);

        }
            break;
        case 1: {
           // String fileName = "C:/Users/Bozhidar/Desktop/sda.txt";    Choose file destination

            // This will reference one line at a time
            String line = null;
            int numberOfItems = 0;
            int capacity = 0;
            try {
                // FileReader reads text files in the default encoding.
                FileReader fileReader = new FileReader(fileName);

                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                int flag = 1;
                boolean isMarked = true;
                while ((line = bufferedReader.readLine()) != null) {
                    if (flag == 1) {
                        capacity = Integer.parseInt(line);
                        flag = 2;
                    } else if (flag == 2 && !isMarked) {
                        numberOfItems = Integer.parseInt(line);
                        flag = 0;
                    } else {
                        String[] splittedInput = line.trim().split("\\s+");
                        items.add(new Item(splittedInput[0], Integer.parseInt(splittedInput[1]), Integer
                                .parseInt(splittedInput[2])));
                    }
                    isMarked = false;
                }
                bufferedReader.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Unable to open file '" + fileName + "'");
            } catch (IOException ex) {
                System.out.println("Error reading file '" + fileName + "'");
            }
            execute(numberOfItems, capacity, items);
        }
            break;
        default:
            System.out.println("Wrong input try again!");
            break;
        }

    }
}
