package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountUtil {

    public static String url = "jdbc:h2:./h2/db";
    public static String username = "sa";
    public static String password = "sa";


    public static void createTable(){
        try{
            Connection conn = DriverManager.getConnection(url, username, password);

            String sql = "CREATE TABLE accounts(\n" +
                    "\taccount_id int,\n" +
                    "\tclient_id int REFERENCES clients(client_id) ON DELETE CASCADE,\n" +
                    "\tbalance double PRECISION DEFAULT 500.00,\n" +
                    "\ttype varchar(50) DEFAULT 'checking',\n" +
                    "\taccount_is_open boolean DEFAULT TRUE,\n" +
                    "\tPRIMARY KEY (account_id,client_id)\n" +
                    ");\n";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            conn.close();

        } catch (SQLException e){

        }
    }

    public static void dropTable(){
        try{
            Connection conn = DriverManager.getConnection(url, username, password);

            String sql = "DROP TABLE accounts";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            conn.close();
        }
        catch(SQLException e){

        }
    }
}
