package models;

public class BalanceModel {
    private Double withdraw;
    private Double deposit;
    private Double amount;



    public BalanceModel(){

    }
    public BalanceModel(Double withdraw, Double deposit, Double amount) {
        this.withdraw = withdraw;
        this.deposit = deposit;
        this.amount = amount;
    }

    public Double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Double withdraw) {
        this.withdraw = withdraw;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
