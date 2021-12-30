package sample;

public class Account extends AccountMoney {

    private String iban;

    private boolean premium;

    private int daysOverdrawn;

    public Customer customer;

    public Account(boolean type, int daysOverdrawn) {
        super();
        this.premium = type;
        this.daysOverdrawn = daysOverdrawn;
    }

    public double bankcharge() {
        double result = 4.5;

        result += overdraftCharge();

        return result;
    }

    private double overdraftCharge() {
        if (premium) {
            double result = 10;
            if (getDaysOverdrawn() > 7)
                result += (getDaysOverdrawn() - 7) * 1.0;
            return result;
        } else
            return getDaysOverdrawn() * 1.75;
    }

    public double overdraftFee() {
        if (premium) {
            return 0.10;
        } else {
            return 0.20;
        }
    }

    public void withdraw(double sum, String currency, Customer customer) {
        if (!getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }
        overdraft(sum, getPremium(), customer.getCustomerType(), customer);
    }

    void overdraft(double sum, boolean discount, CustomerType ct, Customer customer) {
        double discountDivider = (discount) ? 2 : 1;
        double companyFactor = (ct == CustomerType.COMPANY) ? customer.getCompanyOverdraftDiscount() / discountDivider : 1;
        if (getMoney() < 0) {
            setMoney((getMoney() - sum) - sum * overdraftFee() * companyFactor);
        } else {
            setMoney(getMoney() - sum);
        }
    }

    public int getDaysOverdrawn() {
        return daysOverdrawn;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean getPremium() {
        return premium;
    }

    public String getType() {
        return premium ? "premium" : "normal";
    }

    @Override
    public String toString() {
        return "Account: IBAN: " + iban + ", Money: " + getMoney() + ", Account type: " + getType();
    }

    public String printCustomerDaysOverdrawn(Customer customer) {
        String fullName = customer.getFullName();

        String accountDescription = "Account: IBAN: " + getIban() + ", Days Overdrawn: " + getDaysOverdrawn();
        return fullName + accountDescription;
    }
}
