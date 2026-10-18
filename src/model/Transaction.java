package model;

import java.util.Date;

// One line on a bank statement: a deposit, a withdrawal or an interest payment.
public class Transaction {

    private String transactionId;   // e.g. ACC1001-T1
    private String type;            // "Deposit", "Withdrawal" or "Interest"
    private double amount;          // how much money moved
    private Date date;              // when it happened
    private double balanceAfter;    // what the account held after it

    // Makes a new statement line. The date is set to right now.
    public Transaction(String transactionId, String type, double amount, double balanceAfter) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.date = new Date();
    }

    // Getters let other classes READ the values without being able to change them.
    public String getTransactionId() {
        return transactionId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    // Turns the line into text so it can be printed.
    @Override
    public String toString() {
        return transactionId + " | " + type + " | BWP" + String.format("%.2f", amount)
                + " | balance BWP" + String.format("%.2f", balanceAfter);
    }
}
