package sample;

public class Customer {

    private String name;
    private String surname;
    private String email;
    private CustomerType customerType;
    private Account account;
    private double companyOverdraftDiscount = 1;

    public Customer(String name, String surname, String email, CustomerType customerType, Account account) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.customerType = customerType;
        this.account = account;
    }

    // use only to create companies
    public Customer(String name, String email, Account account, double companyOverdraftDiscount) {
        this.name = name;
        this.email = email;
        this.customerType = CustomerType.COMPANY;
        this.account = account;
        this.companyOverdraftDiscount = companyOverdraftDiscount;
    }

    public void withdraw(double sum, String currency) {
        if (!account.getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }
        if (account.getType().isPremium()) {
            pay(sum, true);
        } else {
            pay(sum, false);
        }
    }

    private void pay(double sum, boolean discount) {
        // 50 percent discount for overdraft for premium account
        double discountDivider = (discount) ? 2 : 1;
        switch (customerType) {
            case COMPANY:
                // we are in overdraft
                if (account.getMoney() < 0) {
                    account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee() * companyOverdraftDiscount / discountDivider);
                } else {
                    account.setMoney(account.getMoney() - sum);
                }
                break;
            case PERSON:
                // we are in overdraft
                if (account.getMoney() < 0) {
                    account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee());
                } else {
                    account.setMoney(account.getMoney() - sum);
                }
                break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String printCustomerDaysOverdrawn() {
        String fullName = getFullName();

        String accountDescription = "Account: IBAN: " + account.getIban() + ", Days Overdrawn: " + account.getDaysOverdrawn();
        return fullName + accountDescription;
    }

    public String printCustomerMoney() {
        String fullName = getFullName();
        String accountDescription = "";
        accountDescription += "Account: IBAN: " + account.getIban() + ", Money: " + account.getMoney();
        return fullName + accountDescription;
    }

    private String getFullName() {
        return name + " " + surname + " ";
    }

    public String printCustomerAccount() {
        return "Account: IBAN: " + account.getIban() + ", Money: "
                + account.getMoney() + ", Account type: " + account.getType();
    }
}
