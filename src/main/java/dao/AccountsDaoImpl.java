package dao;

import models.AccountsModel;
import models.BalanceModel;
import models.ClientsModel;
import org.apache.log4j.Logger;
import services.AccountsService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountsDaoImpl implements AccountsDao {

    String url;
    String username;
    String password;

    static Logger logger = Logger.getLogger(ClientsDaoImpl.class);
    static AccountsService accountsService = new AccountsService();

    public AccountsDaoImpl(){
        this.url = "jdbc:postgresql://" +System.getenv("AWS_RDS_ENDPOINT")+ "/bankingapp";
        this.username = System.getenv("RDS_USERNAME");
        this.password = System.getenv("RDS_PASSWORD");
    }

    public AccountsDaoImpl(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }


    public List<AccountsModel> getAllAccounts(Integer client_id){
        List<AccountsModel> accountsList = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT * FROM accounts WHERE client_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, client_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                accountsList.add(new AccountsModel(rs.getInt(1), rs.getInt(2),
                        rs.getDouble(3),rs.getString(4), rs.getBoolean(5)));
            }
            }
        catch(SQLException e){
            logger.error(e);
        }
        return accountsList;
    }

    @Override
    public AccountsModel getOneAccount(Integer client_id, Integer account_id) {

        AccountsModel account = null;

        try (Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT * FROM accounts WHERE client_id = ? AND account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, client_id);
            ps.setInt(2, account_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                account = new AccountsModel(rs.getInt(1), rs.getInt(2),
                        rs.getDouble(3),rs.getString(4), rs.getBoolean(5));
            }
        }
        catch(SQLException e){
            logger.error(e);
        }
        return account;
    }

    @Override
    public void createAccount(AccountsModel account, Integer client_id) {

        try (Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "INSERT INTO accounts VALUES(?, ?, ?, DEFAULT, DEFAULT);";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, account.getAccount_id());
            ps.setInt(2, client_id);
            ps.setDouble(3, account.getBalance());

            ps.executeUpdate();
        }
        catch(SQLException e){
            logger.error(e);
        }

    }

    @Override
    public Integer updateAccount(Integer client_id, Integer account_id) {
        int count = 0;
        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "UPDATE accounts SET account_is_open = FALSE WHERE client_id = ? AND account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, client_id);
            ps.setInt(2, account_id);

            count = ps.executeUpdate();

        }
        catch(SQLException e){
            logger.error(e);
        }
        return count;
    }

    @Override
    public Integer deleteAccount(Integer client_id, Integer account_id) {
        int count = 0;
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "DELETE FROM accounts WHERE account_id = ? AND client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(2, client_id);
            ps.setInt(1, account_id);

            count = ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return count;

    }

    @Override
    public List<AccountsModel> getAllAccounts(Integer client_id, Integer amountLessThan, Integer amountGreaterThan){
        List<AccountsModel> accountsList = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "";


                sql = "SELECT * FROM accounts WHERE client_id = ? AND balance >= ? AND balance <= ?;";
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setInt(1, client_id);
                ps.setInt(3, amountLessThan);
                ps.setInt(2, amountGreaterThan);

                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    accountsList.add(new AccountsModel(rs.getInt(1), rs.getInt(2),
                            rs.getDouble(3),rs.getString(4), rs.getBoolean(5)));
                }
        }
        catch(SQLException e){
            logger.error(e);
        }
        return accountsList;
    }

    @Override
    public Integer changeBalance(Integer client_id, Integer account_id, BalanceModel balance){

        int count = 0;
        try(Connection conn = DriverManager.getConnection(url, username, password)){

            AccountsModel account = getOneAccount(client_id, account_id);

            Double bal = account.getBalance();
            Double newBal;

            if(balance.getWithdraw() == null){
                newBal = bal + balance.getDeposit();

                String sql = "UPDATE accounts SET balance = ? WHERE client_id = ? AND account_id = ?;";
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setDouble(1, newBal);
                ps.setInt(2, client_id);
                ps.setInt(3, account_id);

                count = ps.executeUpdate();
            }
            else{
                    newBal = bal - balance.getWithdraw();

                    String sql = "UPDATE accounts SET balance = ? WHERE client_id = ? AND account_id = ?;";
                    PreparedStatement ps = conn.prepareStatement(sql);

                    ps.setDouble(1, newBal);
                    ps.setInt(2, client_id);
                    ps.setInt(3, account_id);

                    count = ps.executeUpdate();

            }
        }
        catch(SQLException e){
            logger.error(e);
        }
        return count;
    }

    @Override
    public Integer transfer(Integer client_id, Integer account_id, Integer account_id2, BalanceModel balance) {

        int count = 0;
        BalanceModel balance1 = new BalanceModel();
        BalanceModel balance2 = new BalanceModel();

        balance1.setWithdraw(balance.getAmount());
        balance2.setDeposit(balance.getAmount());

        int withdraw = accountsService.changeBalance(client_id, account_id, balance1);
        int deposit = accountsService.changeBalance(client_id, account_id2, balance2);

        if(withdraw == -1 || deposit == -1){
            count = -1;
        }
        else{
            count = withdraw + deposit;
        }

        return count;
    }

}
