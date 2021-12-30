package sample;

public class AccountMoney {
    private double money;
    private String currency;

    public AccountMoney() {
        super();
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
