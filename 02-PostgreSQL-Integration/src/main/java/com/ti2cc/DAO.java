package com.ti2cc;
import java.sql.*;

public class DAO {
    private Connection connection;
    
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
    
    public boolean insertStudent(Student student) {
        boolean status = false;
        
        try {  
            Statement st = connection.createStatement();
            st.executeUpdate("INSERT INTO students (name, email, phone_number, enrollment_date, course) "
                           + "VALUES ('" + student.getName() + "', '"  
                           + student.getEmail() + "', '" + student.getPhoneNumber() + "', '"
                           + student.getEnrollmentDate() + "', '" + student.getCourse() + "');");
            
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        
        return status;
    }
    
    public boolean updateStudent(Student student) {
        boolean status = false;
        
        try {  
            Statement st = connection.createStatement();
            String sql = "UPDATE students SET name = '" + student.getName() + "', email = '"  
                       + student.getEmail() + "', phone_number = '" + student.getPhoneNumber() + "',"
                       + " enrollment_date = '" + student.getEnrollmentDate() + "', course = '" 
                       + student.getCourse() + "' WHERE id = " + student.getId();
            
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        
        return status;
    }
    
    public boolean deleteStudent(int id) {
        boolean status = false;
        
        try {  
            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM students WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        
        return status;
    }
    
    
    public Student[] getStudents() {
        Student[] students = null;
        
        try {
            Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM students");        
            
             if(rs.next()) {
                 rs.last();
                 students = new Student[rs.getRow()];
                 rs.beforeFirst();

                 for(int i = 0; rs.next(); i++) {
                    students[i] = new Student(rs.getInt("id"), rs.getString("name"), 
                                              rs.getString("email"), rs.getString("phone_number"),
                                              rs.getString("enrollment_date"), rs.getString("course"));
                 }
              }
             
              st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        return students;
    }
}