package model;

// Think of this as a badge. An account that wears the badge can work out its interest
// and pay it. Savings and Investment accounts wear it. A Cheque account does not.
// An interface only lists what must be done. Each account writes how it does it.
public interface InterestBearing {

    // Works out how much interest the account earns this month.
    double calculateInterest();

    // Adds that interest to the account.
    void payInterest();
}
