package fx.finalproject;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {

    public static Connection getConnect() {
        String username ="admin";
        String password ="admin";
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521",username,password);
            System.out.println("[+] Connected to the db");
            return con;
        }catch (Exception e){
            System.out.println("[-] Error "+ e);
        }
        return null;
    }
}
