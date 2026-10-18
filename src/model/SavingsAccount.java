package model;

// A savings account is like a money box: money goes in but never comes out.
// It "extends" Account, so it gets the number, balance, branch and deposit for free.
// It "implements" InterestBearing, so it must write calculateInterest and payInterest.
public class SavingsAccount extends Account implements InterestBearing {

    private static final double RATE = 0.0005;   // 0.05% a month

    public SavingsAccount(String branch, double initialDeposit) {
        super(branch, initialDeposit);   // let Account do the common setup
    }

    // Overriding: this account's own rule for withdrawing is "never".
    @Override
    public boolean withdraw(double amount) {
        return false;
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }

    // Interest = balance x rate. The rounding keeps it to cents.
    @Override
    public double calculateInterest() {
        return Math.round(getBalance() * RATE * 100) / 100.0;
    }

    // Works out the interest and adds it to the balance as an "Interest" transaction.
    @Override
    public void payInterest() {
        double interest = calculateInterest();
        if (interest > 0) {
            deposit(interest, "Interest");
        }
    }
}
