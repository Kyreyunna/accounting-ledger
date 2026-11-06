package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountingLedgerApp {

    static Scanner scanner = new Scanner(System.in);
    static List<Transaction> transactionList = new ArrayList<>();

    public static final String PINK = "\u001B[38;5;213m";
    public static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        loadTransactions();
        runHomeScreen();
    }

    public static void loadTransactions() {
        try {
            
            FileReader fileReader = new FileReader("transactions.csv");
            
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            
            String headerLine = bufferedReader.readLine();

            
            String line;

            
            while ((line = bufferedReader.readLine()) != null) {
                
                if (!line.trim().isBlank()) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 5) {
                        
                        LocalDate date = LocalDate.parse(parts[0]);
                        LocalTime time = LocalTime.parse(parts[1]);
                        String description = parts[2];
                        String vendor = parts[3];
                        double amount = Double.parseDouble(parts[4]);

                        
                        Transaction transaction = new Transaction(date, time, description, vendor, amount);
                        transactionList.add(transaction);
                    }
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addToTransactionList(Transaction transaction) {
        transactionList.add(transaction);
    }

    public static void runHomeScreen() {
        String command;
        do {
            System.out.println();
            System.out.println(PINK + "========================================" + RESET);
            System.out.println(PINK + "       ♥  ACCOUNTING LEDGER  ♥          " + RESET);
            System.out.println(PINK + "========================================" + RESET);
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
            System.out.println("A) All");
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
            System.out.println("=== Reports ===");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back to Ledger");
            System.out.print("Enter a command: ");
            command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "1" -> searchByMonth();
                case "2" -> searchByPreviousMonth();
                case "3" -> searchByYear();
                case "4" -> searchByPreviousYear();
                case "5" -> searchByVendor();
                case "0" -> System.out.println("Back to Ledger");
                default -> System.out.println("That isn't a command. Try again.");
            }
        } while (!command.equals("0"));
    }

    public static void addDeposit() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        
        String dateStr = date.toString(); 
        String timeStr = time.format(DateTimeFormatter.ofPattern("HH:mm:ss")); 

        String description = "";
        do {
            System.out.print("Enter description: ");
            description = scanner.nextLine().trim();
            try {
                if (description.isEmpty()) {
                    System.out.println("This can't be blank.");
                } else if (description.contains("|")) {
                    System.out.println("Please don't use the | character.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid word, like Doc Martens");
                throw new RuntimeException(e);

            }

        } while (true);

        String vendor = "";
        do {
            System.out.print("Enter vendor(letters only): ");
            vendor = scanner.nextLine().trim();
            try {
                if (vendor.isEmpty()) {
                    System.out.println("This can't be blank.");
                } else if (vendor.contains("|")) {
                    System.out.println("Please don't use the | character.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid word, like Hot Topic");
                throw new RuntimeException(e);

            }

        } while (true);

        double amount;
        do {
            System.out.print("Enter deposit amount (must be > 0): ");
            try {
                amount = Double.parseDouble(scanner.nextLine());
                if (amount <= 0) {
                    System.out.println("Amount must be greater than zero. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number, like 89.50.");
                amount = -1; 
            }
        } while (amount <= 0);

        
        Transaction newDeposit = new Transaction(date, time, description, vendor, amount);
        addToTransactionList(newDeposit);

        
        String csvRow = dateStr + "|" + timeStr + "|" + description + "|" + vendor + "|" + String.format("%.2f", amount);
        appendToCSV(csvRow);

        System.out.println("Deposit added!");
        pressEnterToContinue();
    }

    public static void makePayment() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        
        String dateStr = date.toString(); 
        String timeStr = time.format(DateTimeFormatter.ofPattern("HH:mm:ss")); 

        String description = "";
        do {
            System.out.print("Enter description(letters only):");
            description = scanner.nextLine().trim();
            try {
                if (description.isEmpty()) {
                    System.out.println("This can't be blank.");
                } else if (description.contains("|")) {
                    System.out.println("Please don't use the | character.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid word, like Doc Martens");
                throw new RuntimeException(e);

            }

        } while (true);

        String vendor = "";
        do {
            System.out.print("Enter vendor(letters only):");
            vendor = scanner.nextLine().trim();
            try {
                if (vendor.isEmpty()) {
                    System.out.println("This can't be blank.");
                } else if (vendor.contains("|")) {
                    System.out.println("Please don't use the | character.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid word, like Hot Topic");
                throw new RuntimeException(e);

            }

        } while (true);

        double inputAmount = -1;
        do {
            System.out.print("Enter payment amount (must be > 0): ");
            try {
                inputAmount = Double.parseDouble(scanner.nextLine());
                if (inputAmount <= 0) {
                    System.out.println("Payment amount must be greater than zero. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number, like 89.50.");
                inputAmount = -1;
            }
        } while (inputAmount <= 0);

        double amount = -Math.abs(inputAmount);

        
        Transaction newPayment = new Transaction(date, time, description, vendor, amount);
        addToTransactionList(newPayment);

        
        String csvRow = dateStr + "|" + timeStr + "|" + description + "|" + vendor + "|" + String.format("%.2f", amount);
        appendToCSV(csvRow);

        System.out.println("Payment made successfully!");
        pressEnterToContinue();
    }

    public static void displayAllTransactions() {
        System.out.println();
        System.out.println("=== All Transactions ===");

        
        System.out.printf("%-12s %-10s %-25s %-20s %12s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------------------------");

        
        for (Transaction t : transactionList) {
            System.out.printf("%-12s %-10s %-25s %-20s %12.2f%n",
                    t.getDate(),
                    t.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                    t.getDescription(),
                    t.getVendor(),
                    t.getAmount());
        }

        pressEnterToContinue();
    }

    public static void displayDeposits() {
        System.out.println();
        System.out.println("=== All Deposits ===");

        
        System.out.printf("%-12s %-10s %-25s %-20s %12s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------------------------");

        
        for (Transaction depositTransactions : transactionList) {
            if (depositTransactions.getAmount() > 0) {
                System.out.printf("%-12s %-10s %-25s %-20s %12.2f%n",
                        depositTransactions.getDate(),
                        depositTransactions.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        depositTransactions.getDescription(),
                        depositTransactions.getVendor(),
                        depositTransactions.getAmount());
            }
        }

        pressEnterToContinue();
    }

    public static void displayPayments() {
        System.out.println();
        System.out.println("=== All Payments ===");

        
        System.out.printf("%-12s %-10s %-25s %-20s %12s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------------------------");

        
        for (Transaction depositTransactions : transactionList) {
            if (depositTransactions.getAmount() < 0) {
                System.out.printf("%-12s %-10s %-25s %-20s %12.2f%n",
                        depositTransactions.getDate(),
                        depositTransactions.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        depositTransactions.getDescription(),
                        depositTransactions.getVendor(),
                        depositTransactions.getAmount());
            }
        }
        pressEnterToContinue();
    }

    public static void searchByMonth() {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        System.out.println("=== This Month ===");

        
        System.out.printf("%-12s %-10s %-25s %-20s %12s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------------------------");

        

        for (Transaction t : transactionList) {
            int transactionYear = t.getDate().getYear();
            int transactionMonth = t.getDate().getMonthValue();
            if (transactionYear == currentYear) {
                if (transactionMonth == currentMonth) {
                    System.out.printf("%-12s %-10s %-25s %-20s %12.2f%n",
                            t.getDate(),
                            t.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                            t.getDescription(),
                            t.getVendor(),
                            t.getAmount());
                }
            }
        }
        pressEnterToContinue();
    }

    public static void searchByPreviousMonth() {
        System.out.println("=== Last Month ===");
        
        System.out.printf("%-12s %-10s %-25s %-20s %12s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------------------------");

        

        try {
            LocalDate today = LocalDate.now();
            LocalDate previousMonth = LocalDate.now().minusMonths(1);
            int targetYear = previousMonth.getYear();
            int targetMonth = previousMonth.getMonthValue();

            for (Transaction t : transactionList) {

                int transactionYear = t.getDate().getYear();
                int transactionMonth = t.getDate().getMonthValue();

                if (transactionYear == targetYear) {
                    if (transactionMonth == targetMonth) {
                        System.out.printf("%-12s %-10s %-25s %-20s %12.2f%n",
                                t.getDate(),
                                t.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                                t.getDescription(),
                                t.getVendor(),
                                t.getAmount());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error occured");
            throw new RuntimeException(e);
        }
        pressEnterToContinue();
    }

    public static void searchByYear() {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        System.out.println();
        System.out.println("=== This Year ===");

        
        System.out.printf("%-12s %-10s %-25s %-20s %12s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------------------------");

        

        for (Transaction t : transactionList) {
            int transactionYear = t.getDate().getYear();
            if (transactionYear == currentYear) {
                System.out.printf("%-12s %-10s %-25s %-20s %12.2f%n",
                        t.getDate(),
                        t.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount());
            }
        }
        pressEnterToContinue();
    }


    public static void searchByPreviousYear() {
        int previousYear = LocalDate.now().minusYears(1).getYear();
        System.out.println();
        System.out.println("=== Previous Year ===");

        
        System.out.printf("%-12s %-10s %-25s %-20s %12s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------------------------");

        

        for (Transaction t : transactionList) {
            int transactionYear = t.getDate().getYear();
            if (transactionYear == previousYear) {
                System.out.printf("%-12s %-10s %-25s %-20s %12.2f%n",
                        t.getDate(),
                        t.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount());
            }
        }
        pressEnterToContinue();
    }

    public static void searchByVendor() {
        System.out.println();
        System.out.println("=== Search By Vendor ===");
        System.out.print("Enter vendor(letters only):");

        String vendorInput = scanner.nextLine().trim();

        if (vendorInput.isEmpty()) {
            System.out.println("This can't be blank.");
            pressEnterToContinue();
            return;
        } else if (vendorInput.contains("|")) {
            System.out.println("Please don't use the | character.");
            pressEnterToContinue();
            return;
        }
        System.out.printf("%-12s %-10s %-25s %-20s %12s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-----------------------------------------------------------------------------------------");

        int vendorCount = 0;

        
        for (Transaction t : transactionList) {
            if (t.getVendor().equalsIgnoreCase(vendorInput)) {
                System.out.printf("%-12s %-10s %-25s %-20s %12.2f%n",
                        t.getDate(),
                        t.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount());
                vendorCount = vendorCount + 1;
            }
        }
        if (vendorCount == 0) {
            System.out.println("Could not find vendor " + vendorInput);
        } else {
            System.out.println("displayed the transactions from " + vendorInput);
        }

        pressEnterToContinue();
    }

    
    public static void pressEnterToContinue() {
        System.out.println();
        System.out.print("Press Enter to go back to the menu...");
        scanner.nextLine();
    }

    
    public static void appendToCSV(String record) {
        try {
            
            
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(record);
            bufferedWriter.newLine(); 

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
