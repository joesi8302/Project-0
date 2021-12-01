package services;

import dao.AccountsDao;
import models.AccountsModel;
import models.BalanceModel;
import models.ClientsModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountsServiceTest {

    AccountsDao accountsDao = Mockito.mock(AccountsDao.class);

    AccountsService accountsService;

    public AccountsServiceTest() {this.accountsService = new AccountsService(accountsDao);}


    @Test
    void getAllAccounts() {

        List<AccountsModel> accounts = new ArrayList<>();
        accounts.add(new AccountsModel(1, 1, 500.0, "checking", true));
        accounts.add(new AccountsModel(2, 1, 500.0, "checking", true));
        accounts.add(new AccountsModel(3, 1, 500.0, "checking", true));
        List<AccountsModel> expectedValue = accounts;
        Mockito.when(accountsDao.getAllAccounts(1)).thenReturn(accounts);


        List<AccountsModel> actualResult = accountsService.getAllAccounts(1);


        assertEquals(expectedValue, actualResult);

    }

    @Test
    void getOneAccount() {

        AccountsModel expectedResult = new AccountsModel(1, 1, 500.0, "checking", true);
        Mockito.when(accountsDao.getOneAccount(expectedResult.getClient_id(), expectedResult.getAccount_id())).thenReturn(expectedResult);

        AccountsModel actualResult = accountsService.getOneAccount(expectedResult.getClient_id(), expectedResult.getAccount_id());

        assertEquals(expectedResult,actualResult);


    }

    @Test
    void createAccount() {

        AccountsModel accountToTest = new AccountsModel(1, 1, 500.0, "checking", true);

        accountsService.createAccount(accountToTest, accountToTest.getClient_id());

        Mockito.verify(accountsDao, Mockito.times(1)).createAccount(accountToTest,accountToTest.getClient_id());

    }

    @Test
    void updateAccount() {

        AccountsModel account= new AccountsModel(1, 1, 500.0, "checking", true);

        Mockito.when(accountsDao.updateAccount(account.getClient_id(), account.getAccount_id())).thenReturn(1);
        Integer expectedResult = 1;

        Integer actualResult = accountsService.updateAccount(account.getClient_id(), account.getAccount_id());

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void deleteAccount() {

        AccountsModel account= new AccountsModel(1, 1, 500.0, "checking", true);

        Mockito.when(accountsDao.deleteAccount(account.getClient_id(), account.getAccount_id())).thenReturn(1);
        Integer expectedResult = 1;

        Integer actualResult = accountsService.deleteAccount(account.getClient_id(), account.getAccount_id());

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void testGetAllAccounts() {

        List<AccountsModel> accounts = new ArrayList<>();
        accounts.add(new AccountsModel(1, 1, 399.0, "checking", true));
        accounts.add(new AccountsModel(2, 1, 400.0, "checking", true));
        accounts.add(new AccountsModel(3, 1, 600.0, "checking", true));
        accounts.add(new AccountsModel(4, 1, 2000.0, "checking", true));
        accounts.add(new AccountsModel(3, 1, 2001.0, "checking", true));
        List<AccountsModel> expectedValue = new ArrayList<>();
        expectedValue.add(new AccountsModel(2, 1, 400.0, "checking", true));
        expectedValue.add(new AccountsModel(3, 1, 600.0, "checking", true));
        expectedValue.add(new AccountsModel(4, 1, 2000.0, "checking", true));
        Mockito.when(accountsDao.getAllAccounts(1,2000, 400)).thenReturn(expectedValue);


        List<AccountsModel> actualResult = accountsService.getAllAccounts(1,2000, 400);


        assertEquals(expectedValue, actualResult);

    }

    @Test
    void changeBalance() {

        AccountsModel account= new AccountsModel(1, 1, 500.0, "checking", true);
        AccountsModel account2= new AccountsModel(null,null,null,null,null);
        BalanceModel balance = new BalanceModel(50.0, null, null);
        Integer expectedValue = 1;

        Mockito.when(accountsService.getOneAccount(account.getClient_id(), account.getAccount_id())).thenReturn(account);
        Mockito.when(accountsDao.changeBalance(account.getClient_id(), account.getAccount_id(), balance)).thenReturn(expectedValue);

        Integer actualResult = accountsService.changeBalance(account.getClient_id(), account.getAccount_id(), balance);

        assertEquals(expectedValue,actualResult);

    }

    @Test
    void transfer() {

        AccountsModel account= new AccountsModel(1, 1, 500.0, "checking", true);
        AccountsModel account2= new AccountsModel(2,1,500.0,"checking", true);
        BalanceModel balance = new BalanceModel(null, null, 50.0);

        Integer expectedValue = 1;

        Mockito.when(accountsService.getOneAccount(account.getClient_id(), account.getAccount_id())).thenReturn(account);
        Mockito.when(accountsService.getOneAccount(account2.getClient_id(), account2.getAccount_id())).thenReturn(account2);
        Mockito.when(accountsDao.transfer(account.getClient_id(), account.getAccount_id(), account2.getAccount_id(), balance)).thenReturn(expectedValue);

        Integer actualResult = accountsService.transfer(account.getClient_id(), account.getAccount_id(), account2.getAccount_id(), balance);

        assertEquals(expectedValue,actualResult);

    }
}