package model;

import java.util.ArrayList;

// This class tests the banking classes from the console. It has no screens and no database yet.
// Each check() line says what we expect, and prints PASS if it happened or FAIL if it did not.
// It is like a teacher ticking off a list of things the bank must do.
public class BankDemo {

    private static int passed = 0;
    private static int failed = 0;

    // Prints PASS or FAIL for one check and keeps count.
    private static void check(String name, boolean ok) {
        if (ok) {
            passed++;
            System.out.println("PASS  " + name);
        } else {
            failed++;
            System.out.println("FAIL  " + name);
        }
    }

    // Compares two money amounts. Doubles can be off by a tiny bit, so we allow a tiny gap.
    private static boolean same(double a, double b) {
        return Math.abs(a - b) < 0.001;
    }

    public static void main(String[] args) {
        Bank bank = new Bank();

        Customer neo = new Customer("C001", "Neo", "Kgosi", "Plot 12, Gaborone", "neo", "pass123", true);
        Customer lesedi = new Customer("C002", "Lesedi", "Mothibi", "Plot 45, Francistown", "lesedi", "secret", false);
        bank.registerCustomer(neo);
        bank.registerCustomer(lesedi);

        System.out.println("--- Registering and logging in ---");
        check("Login with the right password returns the customer", bank.login("neo", "pass123") == neo);
        check("Login with the wrong password returns null", bank.login("neo", "wrong") == null);
        boolean duplicateRejected = false;
        try {
            bank.registerCustomer(new Customer("C003", "Other", "Person", "Somewhere", "NEO", "x", false));
        } catch (IllegalArgumentException e) {
            duplicateRejected = true;
        }
        check("A username that is already taken is rejected", duplicateRejected);

        System.out.println("--- Opening accounts ---");
        Account savings = bank.openAccount(neo, "Savings", 1000);
        check("Savings account opens with BWP1000.00", same(savings.getBalance(), 1000));

        boolean tooLittle = false;
        try {
            bank.openAccount(neo, "Investment", 100);
        } catch (IllegalArgumentException e) {
            tooLittle = true;
        }
        check("Investment account with BWP100.00 is rejected", tooLittle);

        Account investment = bank.openAccount(neo, "Investment", 1000);
        check("Investment account opens with BWP1000.00", same(investment.getBalance(), 1000));

        boolean notEmployed = false;
        try {
            bank.openAccount(lesedi, "Cheque", 200, "Shoprite", "Francistown");
        } catch (IllegalArgumentException e) {
            notEmployed = true;
        }
        check("Cheque account for an unemployed customer is rejected", notEmployed);

        Account cheque = bank.openAccount(neo, "Cheque", 1000, "Choppies", "Gaborone");
        check("Cheque account opens for an employed customer", same(cheque.getBalance(), 1000));
        check("One customer holds three accounts", neo.getAccounts().size() == 3);
        check("An account can be found by its number", neo.getAccount(savings.getAccountNumber()) == savings);
        check("Another customer's account number is not found", lesedi.getAccount(savings.getAccountNumber()) == null);

        System.out.println("--- Deposits and withdrawals ---");
        savings.deposit(500);
        check("Deposit adds to the balance", same(savings.getBalance(), 1500));
        check("Savings account refuses a withdrawal", savings.withdraw(100) == false);
        check("Savings balance is unchanged after the refused withdrawal", same(savings.getBalance(), 1500));
        check("Investment withdrawal of BWP200.00 works", investment.withdraw(200) == true);
        check("Investment balance is BWP800.00 after the withdrawal", same(investment.getBalance(), 800));
        check("Cheque withdrawal bigger than the balance is refused", cheque.withdraw(5000) == false);
        boolean negativeRejected = false;
        try {
            cheque.deposit(-50);
        } catch (IllegalArgumentException e) {
            negativeRejected = true;
        }
        check("A negative deposit is rejected", negativeRejected);

        System.out.println("--- Monthly interest ---");
        Customer thandi = new Customer("C004", "Thandi", "Seretse", "Plot 7, Maun", "thandi", "pw", true);
        bank.registerCustomer(thandi);
        Account s = bank.openAccount(thandi, "Savings", 1000);
        Account i = bank.openAccount(thandi, "Investment", 1000);
        Account c = bank.openAccount(thandi, "Cheque", 1000, "Mascom", "Maun");
        bank.payMonthlyInterest();
        check("Savings earns 0.05%: BWP1000.00 becomes BWP1000.50", same(s.getBalance(), 1000.50));
        check("Investment earns 5%: BWP1000.00 becomes BWP1050.00", same(i.getBalance(), 1050.00));
        check("Cheque earns no interest", same(c.getBalance(), 1000.00));

        System.out.println("--- Transactions ---");
        ArrayList<Transaction> history = investment.getTransactions();
        check("Investment history has the deposit, the withdrawal and the interest", history.size() == 3);
        check("Interest is saved as a transaction", i.getTransactions().get(1).getType().equals("Interest"));

        System.out.println("--- Polymorphism: same call, different account types ---");
        for (Account a : thandi.getAccounts()) {
            System.out.println(a);
        }
        for (Transaction t : investment.getTransactions()) {
            System.out.println("  " + t);
        }

        System.out.println();
        System.out.println("Passed: " + passed + "   Failed: " + failed);
    }
}
