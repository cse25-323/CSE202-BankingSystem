package model;

import java.util.ArrayList;

// A customer is like a customer card with a folder of their accounts inside.
// One customer can have many accounts.
public class Customer {

    private String customerId;
    private String firstName;
    private String surname;
    private String address;
    private String username;
    private String password;
    private boolean employed;    // needed for a cheque account
    private ArrayList<Account> accounts = new ArrayList<>();   // the folder of accounts

    public Customer(String customerId, String firstName, String surname, String address,
                    String username, String password, boolean employed) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.username = username;
        this.password = password;
        this.employed = employed;
    }

    // Puts a new account into the customer's folder.
    public void addAccount(Account a) {
        accounts.add(a);
    }

    // Looks through the folder for the account with that number.
    // Gives back null if this customer does not have it.
    public Account getAccount(String accountNumber) {
        for (Account a : accounts) {
            if (a.getAccountNumber().equals(accountNumber)) {
                return a;
            }
        }
        return null;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEmployed() {
        return employed;
    }

    @Override
    public String toString() {
        return firstName + " " + surname + " (" + customerId + ")";
    }
}
