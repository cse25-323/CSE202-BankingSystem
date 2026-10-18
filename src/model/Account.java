package model;

import java.util.ArrayList;

// An Account is like a blank bank form. Every real account is one of three kinds
// (Savings, Investment or Cheque), so this class is "abstract": you cannot make a plain
// Account, only one of the three kinds. It holds what all three have in common.
public abstract class Account {

    // One counter shared by ALL accounts, like the ticket machine in a bank queue.
    // Each new account takes the next number, so no two accounts get the same one.
    private static int nextNumber = 1001;

    // "private" means only this class can touch these directly (encapsulation).
    private String accountNumber;
    private double balance;
    private String branch;
    private ArrayList<Transaction> transactions = new ArrayList<>();   // the account's statement

    // Runs when a new account is made. It gives the account a number and its first deposit.
    public Account(String branch, double initialDeposit) {
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("The first deposit cannot be negative.");
        }
        this.accountNumber = "ACC" + nextNumber;
        nextNumber = nextNumber + 1;
        this.branch = branch;
        this.balance = 0;
        if (initialDeposit > 0) {
            deposit(initialDeposit);
        }
    }

    // Overloading: two methods with the same name but different inputs.
    // This one is for a normal deposit.
    public void deposit(double amount) {
        deposit(amount, "Deposit");
    }

    // This one lets us say what the money is, for example "Interest".
    public void deposit(double amount, String description) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be more than 0.");
        }
        balance = balance + amount;
        recordTransaction(description, amount);
    }

    // "abstract" means: no code here. Every kind of account must write its own version.
    // That is overriding. Savings refuses, Investment and Cheque allow it.
    public abstract boolean withdraw(double amount);

    public abstract String getAccountType();

    // "protected" means only this class and its children can use it.
    // The three account types use it to lower the balance after a withdrawal.
    protected void setBalance(double newBalance) {
        if (newBalance < 0) {
            throw new IllegalArgumentException("The balance cannot go below zero.");
        }
        balance = newBalance;
    }

    // Adds a new line to the statement. Call it after the balance has changed.
    protected void recordTransaction(String type, double amount) {
        String id = accountNumber + "-T" + (transactions.size() + 1);
        transactions.add(new Transaction(id, type, amount, balance));
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getBranch() {
        return branch;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return getAccountType() + " " + accountNumber + " (" + branch + ") balance BWP"
                + String.format("%.2f", balance);
    }
}
