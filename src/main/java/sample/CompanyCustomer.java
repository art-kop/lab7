package sample;

public class CompanyCustomer extends Customer {
    public CompanyCustomer(String name, String surname, String email, CustomerType customerType, Account account) {
        super(name, surname, email, customerType, account);
    }

    // use only to create companies
    public CompanyCustomer(String name, String email, Account account, double companyOverdraftDiscount) {
        super(name, email, account, companyOverdraftDiscount);
    }
}