package dao;

import models.AccountsModel;
import models.BalanceModel;

import java.util.List;

public interface AccountsDao {
    List<AccountsModel> getAllAccounts(Integer client_id);
    AccountsModel getOneAccount(Integer client_id, Integer account_id);
    void createAccount(AccountsModel account, Integer client_id);
    Integer updateAccount(Integer client_id, Integer account_id);
    Integer deleteAccount(Integer client_id, Integer account_id);
    List<AccountsModel> getAllAccounts(Integer client_id, Integer amountLessThan, Integer amountGreaterThan);
    Integer changeBalance(Integer client_id, Integer account_id, BalanceModel balance);
    Integer transfer(Integer client_id, Integer account_id, Integer account_id2, BalanceModel balance);


}
