package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountingLedgerApp {

    static Scanner scanner = new Scanner(System.in);
    static List<Transaction> transactionList = new ArrayList<>();

    public static void main(String[] args) {
        loadTransactions();
        runHomeScreen();
    }

    public static void loadTransactions() {
        try {
            // create a FileReader connected to the file
            FileReader fileReader = new FileReader("transactions.csv");
            // create a BufferedReader to manage the input stream
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // read the first line
            String line = bufferedReader.readLine();

            // readLine() returns null when there are no more lines
            while (line != null) {
                System.out.println(line);
                // read the next line
                line = bufferedReader.readLine();
            }

            // close the reader to release the file
            bufferedReader.close();
        } catch (IOException e) {
            // print the details so the error is never hidden from you
            e.printStackTrace();
        }
    }

    public static void addToTransactionList(Transaction transaction) {
        transactionList.add(transaction);
    }
    // ---------- Screens: a menu that repeats until the user picks the way out ----------

    public static void runHomeScreen() {
        String command;
        do {
            System.out.println();
            System.out.println("=== Home ===");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("What would you like to do?: ");
            command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "D" -> addDeposit();
                case "P" -> makePayment();
                case "L" -> runLedgerScreen();
                case "X" -> System.out.println("Goodbye!");
                default -> System.out.println("That isn't a command. Try again.");
            }
        } while (!command.equals("X"));
    }

    public static void runLedgerScreen() {
        String command;
        do {
            System.out.println();
            System.out.println("=== Ledger ===");
            System.out.println("A) All Transactions");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Enter a command: ");
            command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "A" -> displayAllTransactions();
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "R" -> runReportsScreen();
                case "H" -> System.out.println("Back to Home");
                default -> System.out.println("That isn't a command. Try again.");
            }
        } while (!command.equals("H"));
    }

    public static void runReportsScreen() {
        String command;
        do {
            System.out.println();
            System.out.println("=== Search ===");
            System.out.println("1) Month to Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Enter a command: ");
            command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "1" -> searchByMonth();
                case "2" -> searchByPreviousMonth();
                case "3" -> searchByYear();
                case "4" -> searchByPreviousYear();
                case "5" -> searchByVendor();
                case "0" -> System.out.println("Back to Browse Movies");
                default -> System.out.println("That isn't a command. Try again.");
            }
        } while (!command.equals("0"));
    }

    // ---------- Actions: do one thing, wait for Enter, then return to the screen ----------

    public static void addDeposit() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();

        System.out.print("Enter time (HH:mm:ss): ");
        String timeStr = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter deposit amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        int date = Integer.parseInt(dateStr.replace("-", ""));
        int time = Integer.parseInt(timeStr.replace(":", ""));

        // Create the new transaction object
        Transaction newDeposit = new Transaction(date, time, description, vendor, amount);

        addToTransactionList(newDeposit);

        // Save it to CSV file (create a CSV string format)
        String csvRow = dateStr + "|" + timeStr + "|" + description + "|" + vendor + "|" + amount;
        appendToCSV(csvRow);

        System.out.println("Deposit added and saved successfully!");
        pressEnterToContinue();
    }

    public static void makePayment() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Year: ");
        // read the whole line, then turn it into a number
        int year = Integer.parseInt(scanner.nextLine());

        System.out.println("Added " + title + " (" + year + ")");
        pressEnterToContinue();
    }

    public static void displayAllTransactions() {
        System.out.println("TODO: display all movies");
        pressEnterToContinue();
    }

    public static void displayDeposits() {
        System.out.println("TODO: display the movies you've watched");
        pressEnterToContinue();
    }

    public static void displayPayments() {
        System.out.println("TODO: display the movies you haven't watched");
        pressEnterToContinue();
    }

    public static void searchByMonth() {
        System.out.print("Title to search for: ");
        String title = scanner.nextLine();

        System.out.println("TODO: display the movies whose title matches " + title);
        pressEnterToContinue();
    }

    public static void searchByPreviousMonth() {
        System.out.print("Year to search for: ");
        int year = Integer.parseInt(scanner.nextLine());

        System.out.println("TODO: display the movies from " + year);
        pressEnterToContinue();
    }

    public static void searchByYear() {
        System.out.print("Year to search for: ");
        int year = Integer.parseInt(scanner.nextLine());

        System.out.println("TODO: display the movies from " + year);
        pressEnterToContinue();
    }

    public static void searchByPreviousYear() {
        System.out.print("Year to search for: ");
        int year = Integer.parseInt(scanner.nextLine());

        System.out.println("TODO: display the movies from " + year);
        pressEnterToContinue();
    }

    public static void searchByVendor() {
        System.out.print("Year to search for: ");
        int year = Integer.parseInt(scanner.nextLine());

        System.out.println("TODO: display the movies from " + year);
        pressEnterToContinue();
    }

    // the results stay on screen until the user is ready to see the menu again
    public static void pressEnterToContinue() {
        System.out.println();
        System.out.print("Press Enter to go back to the menu...");
        scanner.nextLine();
    }

    // Helper method to append a transaction line to transactions.csv
    public static void appendToCSV(String record) {
        try {
            // Passing true to the FileWriter constructor enables "append mode"
            // so it adds to the file instead of overwriting it.
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(record);
            bufferedWriter.newLine(); // Moves to the next line for the next entry

            bufferedWriter.close();
            System.out.println("Successfully saved to file!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
