package model;

import java.util.ArrayList;

// The Bank is the manager's office. It keeps all the customers, registers them,
// checks their login, opens their accounts and pays the monthly interest.
public class Bank {

    // The scenario does not say how branches are chosen, so every account uses this one.
    private static final String BRANCH = "Main Branch";

    private ArrayList<Customer> customers = new ArrayList<>();   // the bank's list of customers

    // The clerk registers a new customer. Two customers cannot share a username.
    public void registerCustomer(Customer c) {
        for (Customer existing : customers) {
            if (existing.getUsername().equalsIgnoreCase(c.getUsername())) {
                throw new IllegalArgumentException("That username is already taken.");
            }
        }
        customers.add(c);
    }

    // Checks the username and password. Gives back the customer if they match, otherwise null.
    public Customer login(String username, String password) {
        for (Customer c : customers) {
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null;
    }

    // Opens a savings or investment account for a registered customer.
    public Account openAccount(Customer c, String type, double initialDeposit) {
        checkRegistered(c);
        Account a;
        if (type.equalsIgnoreCase("Savings")) {
            a = new SavingsAccount(BRANCH, initialDeposit);
        } else if (type.equalsIgnoreCase("Investment")) {
            // The rule from the scenario: an investment account needs BWP500 to open.
            if (initialDeposit < InvestmentAccount.MIN_OPENING_DEPOSIT) {
                throw new IllegalArgumentException("An investment account needs at least BWP500.00 to open.");
            }
            a = new InvestmentAccount(BRANCH, initialDeposit);
        } else if (type.equalsIgnoreCase("Cheque")) {
            throw new IllegalArgumentException("A cheque account needs the company name and address.");
        } else {
            throw new IllegalArgumentException("Unknown account type: " + type);
        }
        c.addAccount(a);
        return a;
    }

    // Overloading: a second openAccount, used for a cheque account.
    // It also needs the company name and address.
    public Account openAccount(Customer c, String type, double initialDeposit,
                               String companyName, String companyAddress) {
        checkRegistered(c);
        if (!type.equalsIgnoreCase("Cheque")) {
            throw new IllegalArgumentException("Company details are only used for cheque accounts.");
        }
        // The rule from the scenario: a cheque account is only for a working person.
        if (!c.isEmployed()) {
            throw new IllegalArgumentException("A cheque account can only be opened for an employed customer.");
        }
        Account a = new ChequeAccount(BRANCH, initialDeposit, companyName, companyAddress);
        c.addAccount(a);
        return a;
    }

    // Goes through every account of every customer.
    // It asks each one "do you wear the InterestBearing badge?". If yes, it pays interest.
    // Cheque accounts have no badge, so they are skipped.
    public void payMonthlyInterest() {
        for (Customer c : customers) {
            for (Account a : c.getAccounts()) {
                if (a instanceof InterestBearing) {
                    InterestBearing payer = (InterestBearing) a;
                    payer.payInterest();
                }
            }
        }
    }

    // Stops an account being opened for someone the bank has not registered.
    private void checkRegistered(Customer c) {
        if (!customers.contains(c)) {
            throw new IllegalArgumentException("The customer must be registered before opening an account.");
        }
    }
}
