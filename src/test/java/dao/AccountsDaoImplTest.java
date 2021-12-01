package dao;

import models.AccountsModel;
import models.BalanceModel;
import models.ClientsModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ClientUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountsDaoImplTest {

    AccountsDao accountsDao;
    ClientsDao clientsDao;

    public AccountsDaoImplTest() {
        this.accountsDao = new AccountsDaoImpl(ClientUtil.url, ClientUtil.username, ClientUtil.password);
        this.clientsDao = new ClientsDaoImpl(ClientUtil.url, ClientUtil.username, ClientUtil.password);
    }


    @BeforeEach
    void setUp() {

        ClientUtil.createTable();
        ClientUtil.createTableAcc();
    }

    @AfterEach
    void tearDown() {
        ClientUtil.dropTable();
        ClientUtil.dropTableAcc();
    }

    @Test
    void getAllAccountsIT() {
        //arrange
        List<AccountsModel> expectedResult = new ArrayList<>();
        ClientsModel client = new ClientsModel(1,"Joe", "Si", true);
        clientsDao.createClient(client);
        expectedResult.add(new AccountsModel(1, 1, 500.0, "checking", true));
        expectedResult.add(new AccountsModel(2, 1, 500.0, "checking", true));
        expectedResult.add(new AccountsModel(3, 1, 500.0, "checking", true));
        accountsDao.createAccount(expectedResult.get(0),1);
        accountsDao.createAccount(expectedResult.get(1),1);
        accountsDao.createAccount(expectedResult.get(2),1);

        //act
        List<AccountsModel> actualResult = accountsDao.getAllAccounts(1);

        //assert
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void getOneAccount() {

        List<AccountsModel> expectedResult = new ArrayList<>();
        ClientsModel client = new ClientsModel(1,"Joe", "Si", true);
        clientsDao.createClient(client);
        expectedResult.add(new AccountsModel(1, 1, 500.0, "checking", true));
        expectedResult.add(new AccountsModel(2, 1, 500.0, "checking", true));
        expectedResult.add(new AccountsModel(3, 1, 500.0, "checking", true));
        accountsDao.createAccount(expectedResult.get(0),1);
        accountsDao.createAccount(expectedResult.get(1),1);
        accountsDao.createAccount(expectedResult.get(2),1);

        AccountsModel actualResult = accountsDao.getOneAccount(1,2);

        assertEquals(expectedResult.get(1),actualResult);

    }

    @Test
    void createAccount() {

        List<AccountsModel> expectedResult = new ArrayList<>();
        ClientsModel client = new ClientsModel(1,"Joe", "Si", true);
        clientsDao.createClient(client);
        expectedResult.add(new AccountsModel(1, 1, 500.0, "checking", true));
        expectedResult.add(new AccountsModel(2, 1, 500.0, "checking", true));
        expectedResult.add(new AccountsModel(3, 1, 500.0, "checking", true));
        accountsDao.createAccount(expectedResult.get(0),1);
        accountsDao.createAccount(expectedResult.get(1),1);
        accountsDao.createAccount(expectedResult.get(2),1);

        Integer actualResult = accountsDao.getAllAccounts(1).size();

        assertEquals(expectedResult.size(),actualResult);

    }

    @Test
    void updateAccount() {

        List<AccountsModel> accountToPass = new ArrayList<>();
        ClientsModel client = new ClientsModel(1,"Joe", "Si", true);
        clientsDao.createClient(client);
        accountToPass.add(new AccountsModel(1, 1, 500.0, "checking", true));
        accountsDao.createAccount(accountToPass.get(0),1);
        AccountsModel expectedResult = new AccountsModel(1, 1, 500.0, "checking", false);

        accountsDao.updateAccount(accountToPass.get(0).getClient_id(),1);
        AccountsModel actualResult = accountsDao.getOneAccount(accountToPass.get(0).getClient_id(),1);

        assertEquals(expectedResult, actualResult );


    }

    @Test
    void deleteAccount() {

        List<AccountsModel> expectedResult = new ArrayList<>();
        ClientsModel client = new ClientsModel(1,"Joe", "Si", true);
        clientsDao.createClient(client);
        expectedResult.add(new AccountsModel(1, 1, 500.0, "checking", true));
        expectedResult.add(new AccountsModel(2, 1, 500.0, "checking", true));
        expectedResult.add(new AccountsModel(3, 1, 500.0, "checking", true));
        accountsDao.createAccount(expectedResult.get(0),1);
        accountsDao.createAccount(expectedResult.get(1),1);
        accountsDao.createAccount(expectedResult.get(2),1);

        accountsDao.deleteAccount(1,1);
        expectedResult.remove(0);

        List<AccountsModel> actualResult = accountsDao.getAllAccounts(1);

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void getAllAccountsBetween(){
        List<AccountsModel> expectedResult = new ArrayList<>();
        ClientsModel client = new ClientsModel(1,"Joe", "Si", true);
        clientsDao.createClient(client);
        expectedResult.add(new AccountsModel(1, 1, 399.0, "checking", true));
        expectedResult.add(new AccountsModel(2, 1, 500.0, "checking", true));
        expectedResult.add(new AccountsModel(3, 1, 600.0, "checking", true));
        expectedResult.add(new AccountsModel(4, 1, 2000.0, "checking", true));
        expectedResult.add(new AccountsModel(5, 1, 2001.0, "checking", true));
        accountsDao.createAccount(expectedResult.get(0),1);
        accountsDao.createAccount(expectedResult.get(1),1);
        accountsDao.createAccount(expectedResult.get(2),1);
        accountsDao.createAccount(expectedResult.get(3),1);
        accountsDao.createAccount(expectedResult.get(4),1);

        List<AccountsModel> expectedResult1 = new ArrayList<>();
        expectedResult1.add(new AccountsModel(2, 1, 500.0, "checking", true));
        expectedResult1.add(new AccountsModel(3, 1, 600.0, "checking", true));
        expectedResult1.add(new AccountsModel(4, 1, 2000.0, "checking", true));

        List<AccountsModel> actualResult = accountsDao.getAllAccounts(1, 2000, 400);

        assertEquals(expectedResult1,actualResult);

    }

    @Test
    void changeBalance() {
        List<AccountsModel> accountToPass = new ArrayList<>();
        BalanceModel balance1 = new BalanceModel(50.0,null,null);
        BalanceModel balance2 = new BalanceModel(null,50.0,null);
        ClientsModel client = new ClientsModel(1,"Joe", "Si", true);
        clientsDao.createClient(client);
        accountToPass.add(new AccountsModel(1, 1, 500.0, "checking", true));
        accountToPass.add(new AccountsModel(2, 1, 500.0, "checking", true));
        accountsDao.createAccount(accountToPass.get(0),1);
        accountsDao.createAccount(accountToPass.get(1),1);
        AccountsModel expectedResult1 = new AccountsModel(1, 1, 450.0, "checking", true);
        AccountsModel expectedResult2 = new AccountsModel(2, 1, 550.0, "checking", true);


        accountsDao.changeBalance(accountToPass.get(0).getClient_id(),1,balance1);
        accountsDao.changeBalance(accountToPass.get(1).getClient_id(),2,balance2);
        AccountsModel actualResult1 = accountsDao.getOneAccount(accountToPass.get(0).getClient_id(),1);
        AccountsModel actualResult2 = accountsDao.getOneAccount(accountToPass.get(1).getClient_id(),2);

        assertEquals(expectedResult1, actualResult1 );
        assertEquals(expectedResult2, actualResult2 );

    }

    @Test
    void transfer() {

        List<AccountsModel> accountToPass = new ArrayList<>();
        BalanceModel balance1 = new BalanceModel(null,null,50.0);
        BalanceModel balance2 = new BalanceModel(50.0,null,null);
        BalanceModel balance3 = new BalanceModel(null,50.0,null);


        ClientsModel client = new ClientsModel(1,"Joe", "Si", true);
        clientsDao.createClient(client);
        accountToPass.add(new AccountsModel(1, 1, 500.0, "checking", true));
        accountToPass.add(new AccountsModel(2, 1, 500.0, "checking", true));
        accountsDao.createAccount(accountToPass.get(0),1);
        accountsDao.createAccount(accountToPass.get(1),1);

        AccountsModel expectedResult1 = new AccountsModel(1, 1, 450.0, "checking", true);
        AccountsModel expectedResult2 = new AccountsModel(2, 1, 550.0, "checking", true);


        accountsDao.transfer(accountToPass.get(0).getClient_id(),accountToPass.get(0).getAccount_id(),accountToPass.get(1).getAccount_id(), balance1);
        accountsDao.changeBalance(accountToPass.get(0).getClient_id(),accountToPass.get(0).getAccount_id(),balance2);
        accountsDao.changeBalance(accountToPass.get(0).getClient_id(),accountToPass.get(1).getAccount_id(),balance3);

        AccountsModel actualResult1 = accountsDao.getOneAccount(accountToPass.get(0).getClient_id(),1);
        AccountsModel actualResult2 = accountsDao.getOneAccount(accountToPass.get(1).getClient_id(),2);

        //assertEquals(expectedResult1, actualResult1 );
        assertEquals(expectedResult2, actualResult2 );


    }
}