# Accounting Ledger

A command-line accounting ledger app that tracks financial transactions from a CSV file and displays formatted reports with **custom menu navigation** for everyday personal finance management.

## Features

* Add new financial deposits with custom descriptions, vendor names, and input validation

* Record itemized payments with automatic negative amount conversion and decimal formatting

* View a complete ledger of all recorded transactions in a clean, aligned table view

* Filter and review specific ledger categories including deposits and payments

* Run financial reports for month-to-date, previous month, year-to-date, and previous year

* Search transaction records by vendor name with interactive match tracking

* Enjoy an aesthetic, customized terminal interface featuring a styled pink header

## How to run it

1. Clone the repository and open it in IntelliJ
2. Run `AccountingLedgerApp.java`
3. When the console starts up, you can type L right away to open the ledger screen and view all your pre-existing transactions, or jump straight to R to run reports and search through that history!

## Screenshots

![Input validation](images/input-validation.png)

![Ledger view](images/ledger-view.png)

## Interesting code

Users love to break apps by typing letters when they are supposed to enter numbers, or leaving fields blank. My code handles this using a do-while loop combined with try-catch.

```java
public class InputValidator {

    public static double getValidAmount(Scanner scanner) {
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
        return amount;
    }
}
```

## Author

Kyreyunna Haugabook, Year Up Java Academy