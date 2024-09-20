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
		String url = "jdbc:postgresql://" + serverName + ":" + port +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, username, password);
			status = (connection == null);
			System.out.println("Conexão efetuada com o postgres.");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão não efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão não efetuada com o postgres -- " + e.getMessage());
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