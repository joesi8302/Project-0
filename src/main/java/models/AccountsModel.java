package models;

import java.util.Objects;

public class AccountsModel {
    private Integer account_id;
    private Integer client_id;
    private Double balance;
    private String type;
    private Boolean account_is_open;



    public AccountsModel(){

    }

    public AccountsModel(Integer account_id, Integer client_id, Double balance, String type, Boolean account_is_open) {
        this.account_id = account_id;
        this.client_id = client_id;
        this.balance = balance;
        this.type = type;
        this.account_is_open = account_is_open;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAccount_is_open() {
        return account_is_open;
    }

    public void setAccount_is_open(Boolean account_is_open) {
        this.account_is_open = account_is_open;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountsModel that = (AccountsModel) o;
        return Objects.equals(account_id, that.account_id) && Objects.equals(client_id, that.client_id) && Objects.equals(balance, that.balance) && Objects.equals(type, that.type) && Objects.equals(account_is_open, that.account_is_open);
    }

    @Override
    public String toString() {
        return "AccountsModel{" +
                "account_id=" + account_id +
                ", client_id=" + client_id +
                ", balance=" + balance +
                ", type='" + type + '\'' +
                ", account_is_open=" + account_is_open +
                '}';
    }
}
