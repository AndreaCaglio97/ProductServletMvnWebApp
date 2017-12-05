package it.intre.ProductServletMvn.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class ConnectionManager {
    private static ConnectionManager connManager;
    private Connection conn;

    private ConnectionManager() {
    }

    public Statement createStatement() {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("ERROR! statement NOT successfully created");
        }
        return stmt;
    }

    public static ConnectionManager getConnectionSingleton() {
        if (connManager == null) {
            connManager = new ConnectionManager();
            try {
                String url = "jdbc:postgresql://localhost:5432/receiptdb";
                Properties props = new Properties();
                props.setProperty("user", "postgres");
                props.setProperty("password", "intre$2017");

                connManager.conn = DriverManager.getConnection(url, props);

                System.out.println("Successful connection");

            }catch (SQLException e) {
                System.out.println("ERROR! Connection NOT successfully completed");
            }
        }

        return connManager;
    }

    public void closeConnectionToDB() {
        try {
            conn.close();
            System.out.println("Successful close connection");
        }catch (SQLException e) {
            System.out.println("ERROR! Close connection NOT successfully completed");
        }
    }
}
