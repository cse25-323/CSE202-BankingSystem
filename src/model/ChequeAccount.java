package model;

// A cheque account is for salaries. You can put money in and take it out.
// It pays no interest, so it does NOT implement InterestBearing (no badge).
// It also remembers where the customer works.
public class ChequeAccount extends Account {

    private String companyName;
    private String companyAddress;

    public ChequeAccount(String branch, double initialDeposit, String companyName, String companyAddress) {
        super(branch, initialDeposit);
        if (companyName == null || companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("The company name is required for a cheque account.");
        }
        if (companyAddress == null || companyAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("The company address is required for a cheque account.");
        }
        this.companyName = companyName;
        this.companyAddress = companyAddress;
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
        return "Cheque Account";
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }
}
