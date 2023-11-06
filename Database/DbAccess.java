package Database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess {

    private static final DbAccess instance = new DbAccess();
    private static Connection mydb;

    public static DbAccess getInstance()
    {
        return instance;
    }

    protected Connection getConnection( ) throws SQLException {
        if(mydb==null){
            Driver myDriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(myDriver);
            String URL = "jdbc:mysql://localhost:3306/mydb_java_8";
            mydb = DriverManager.getConnection(URL, "root", "root");

        }
        return mydb;
    }
}
