package dao;

import java.sql.*;

public class DAO {
    protected Connection connection;
    
    public DAO() {
        connection = null;
    }
    
    public boolean openConnection() {
        String driverName = "org.postgresql.Driver";                    
        String serverName = "localhost";
        String mydatabase = "school";
        int port = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + port + "/" + mydatabase;
        String username = "ti2cc";
        String password = "ti@cc";
        boolean status = false;

        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, username, password);
            status = (connection == null);
            System.out.println("Connection established with PostgreSQL!");
        } catch (ClassNotFoundException e) { 
            System.err.println("Connection NOT established with PostgreSQL -- Driver not found -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Connection NOT established with PostgreSQL -- " + e.getMessage());
        }

        return status;
    }
    
    public boolean closeConnection() {
        boolean status = false;
        
        try {
            connection.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }
}