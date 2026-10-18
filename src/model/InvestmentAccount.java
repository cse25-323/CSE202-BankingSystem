package model;

// An investment account pays more interest than savings and lets you take money out.
// It needs at least BWP500 to open.
public class InvestmentAccount extends Account implements InterestBearing {

    private static final double RATE = 0.05;                          // 5% a month
    public static final double MIN_OPENING_DEPOSIT = 500.00;         // the Bank checks this

    public InvestmentAccount(String branch, double initialDeposit) {
        super(branch, initialDeposit);
    }

    // Overriding: allowed, but only if the account has enough money.
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be more than 0.");
        }
        if (amount > getBalance()) {
            return false;   // not enough money, and no overdrafts
        }
        setBalance(getBalance() - amount);
        recordTransaction("Withdrawal", amount);
        return true;
    }

    @Override
    public String getAccountType() {
        return "Investment Account";
    }

    // Interest = balance x rate, rounded to cents.
    @Override
    public double calculateInterest() {
        return Math.round(getBalance() * RATE * 100) / 100.0;
    }

    @Override
    public void payInterest() {
        double interest = calculateInterest();
        if (interest > 0) {
            deposit(interest, "Interest");
        }
    }
}
