package sample;

public class Account {

    private String iban;

    private AccountType type;

    private int daysOverdrawn;

    private double money;

    private String currency;

    public Customer customer;

    public Account(AccountType type, int daysOverdrawn) {
        super();
        this.type = type;
        this.daysOverdrawn = daysOverdrawn;
    }

    public double bankcharge() {
        double result = 4.5;

        result += overdraftCharge();

        return result;
    }

    private double overdraftCharge() {
        if (type.isPremium()) {
            double result = 10;
            if (getDaysOverdrawn() > 7)
                result += (getDaysOverdrawn() - 7) * 1.0;
            return result;
        } else
            return getDaysOverdrawn() * 1.75;
    }

    public double overdraftFee() {
        if (type.isPremium()) {
            return 0.10;
        } else {
            return 0.20;
        }
    }

    public void withdraw(double sum, String currency, Customer customer) {
        if (!getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }
        overdraft(sum, getType().isPremium(), customer.getCustomerType(), customer);
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

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AccountType getType() {
        return type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
