package services;

import dao.AccountsDao;
import dao.AccountsDaoImpl;
import models.AccountsModel;
import models.BalanceModel;

import java.util.List;

public class AccountsService {

    AccountsDao accountsDao;

    public AccountsService(AccountsDao accountsDao) {this.accountsDao = accountsDao;}

    public AccountsService(){ this.accountsDao = new AccountsDaoImpl();}

    public List<AccountsModel> getAllAccounts(Integer client_id) {return accountsDao.getAllAccounts(client_id);}

    public AccountsModel getOneAccount(Integer client_id, Integer account_id) {return accountsDao.getOneAccount(client_id, account_id);}

    public void createAccount(AccountsModel account, Integer client_id) { accountsDao.createAccount(account, client_id);}

    public Integer updateAccount(Integer client_id, Integer account_id){return accountsDao.updateAccount(client_id, account_id);}

    public Integer deleteAccount(Integer client_id, Integer account_id){return accountsDao.deleteAccount(client_id, account_id);}

    public List<AccountsModel> getAllAccounts(Integer client_id, Integer amountLessThan, Integer amountGreaterThan)
    {return accountsDao.getAllAccounts(client_id, amountLessThan, amountGreaterThan);}

    public Integer changeBalance(Integer client_id, Integer account_id, BalanceModel balance){

        AccountsModel account = getOneAccount(client_id, account_id);


        if(account == null)
        {
            return 0;
        }
        else if(balance.getWithdraw() != null && balance.getWithdraw() > account.getBalance())
        {
            return -1;
        }
        else{
            return accountsDao.changeBalance(client_id, account_id, balance);
        }

    }

    public Integer transfer(Integer client_id, Integer account_id, Integer account_id2, BalanceModel balance)
    {
        AccountsModel account1 = getOneAccount(client_id, account_id);
        AccountsModel account2 = getOneAccount(client_id, account_id2);
        if(account1 == null || account2 == null){
            return 0;
        }
        else {
            return accountsDao.transfer(client_id, account_id, account_id2, balance);
        }
    }

}
