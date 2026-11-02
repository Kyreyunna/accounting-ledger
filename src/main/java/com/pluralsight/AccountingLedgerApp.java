package com.pluralsight;

import java.util.Scanner;

public class AccountingLedgerApp {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        runHomeScreen();
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
            System.out.println("=== Browse Movies ===");
            System.out.println("A) All Movies");
            System.out.println("W) Watched");
            System.out.println("U) Unwatched");
            System.out.println("S) Search");
            System.out.println("H) Home");
            System.out.print("Enter a command: ");
            command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "A" -> displayAllMovies();
                case "W" -> displayWatchedMovies();
                case "U" -> displayUnwatchedMovies();
                case "S" -> runSearchScreen();
                case "H" -> System.out.println("Back to Home");
                default -> System.out.println("That isn't a command. Try again.");
            }
        } while (!command.equals("H"));
    }

    public static void runSearchScreen() {
        String command;
        do {
            System.out.println();
            System.out.println("=== Search ===");
            System.out.println("1) By Title");
            System.out.println("2) By Year");
            System.out.println("0) Back");
            System.out.print("Enter a command: ");
            command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "1" -> searchByTitle();
                case "2" -> searchByYear();
                case "0" -> System.out.println("Back to Browse Movies");
                default -> System.out.println("That isn't a command. Try again.");
            }
        } while (!command.equals("0"));
    }

    // ---------- Actions: do one thing, wait for Enter, then return to the screen ----------

    public static void addDeposit() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Year: ");
        // read the whole line, then turn it into a number
        int year = Integer.parseInt(scanner.nextLine());

        System.out.println("Added " + title + " (" + year + ")");
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

    public static void displayAllMovies() {
        System.out.println("TODO: display all movies");
        pressEnterToContinue();
    }

    public static void displayWatchedMovies() {
        System.out.println("TODO: display the movies you've watched");
        pressEnterToContinue();
    }

    public static void displayUnwatchedMovies() {
        System.out.println("TODO: display the movies you haven't watched");
        pressEnterToContinue();
    }

    public static void searchByTitle() {
        System.out.print("Title to search for: ");
        String title = scanner.nextLine();

        System.out.println("TODO: display the movies whose title matches " + title);
        pressEnterToContinue();
    }

    public static void searchByYear() {
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
}
