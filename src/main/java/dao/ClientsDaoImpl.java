package dao;

import io.javalin.http.Context;
import models.ClientsModel;
import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.client.io.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientsDaoImpl implements ClientsDao {

    String url;
    String username;
    String password;

    static Logger logger = Logger.getLogger(ClientsDaoImpl.class);

    public ClientsDaoImpl(){
        this.url = "jdbc:postgresql://" +System.getenv("AWS_RDS_ENDPOINT")+ "/bankingapp";
        this.username = System.getenv("RDS_USERNAME");
        this.password = System.getenv("RDS_PASSWORD");
    }

    public ClientsDaoImpl(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<ClientsModel> getAllClients() {
        List<ClientsModel> clients = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url,username,password)){
            String sql = "SELECT * FROM clients;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                clients.add(new ClientsModel(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getBoolean(4)));
            }
        }
        catch(SQLException e){
            logger.error(e);
        }
        return clients;
    }

    @Override
    public ClientsModel getOneClient(Integer client_id) {

        ClientsModel client = null;

        try (Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM clients WHERE client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, client_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                client = new ClientsModel(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getBoolean(4));
            }

        }catch(SQLException e){
            e.printStackTrace();

        }
        return client;
    }

    @Override
    public void createClient(ClientsModel client) {

        try(Connection conn = DriverManager.getConnection(url,username, password)){
            String sql = "INSERT INTO clients VALUES(DEFAULT, ?, ?, DEFAULT);";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, client.getFirst_name());
            ps.setString(2, client.getLast_name());

            ps.executeUpdate();
        }
        catch (SQLException e){
            logger.error(e);
        }

    }

    @Override
    public Integer updateClient(Integer client_id) {

        int count = 0;
        try(Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "UPDATE clients SET active = FALSE WHERE client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, client_id);

            count = ps.executeUpdate();

        }
        catch(SQLException e){
            logger.error(e);
        }
        return count;
    }

    @Override
    public Integer deleteClient(Integer client_id) {
        int count = 0;
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "DELETE FROM clients WHERE client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, client_id);

            count = ps.executeUpdate();
        }
        catch(SQLException e){
                e.printStackTrace();
            }
        return count;

    }
}
