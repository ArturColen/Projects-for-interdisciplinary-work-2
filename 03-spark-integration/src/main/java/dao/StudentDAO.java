package dao;

import model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends DAO {    
    public StudentDAO() {
        super();
        openConnection();
    }
    
    public void finalize() {
        closeConnection();
    }
    
    public boolean insert(Student student) {
        boolean status = false;
        
        try {
            String sql = "INSERT INTO students (name, email, phone_number, enrollment_date, course) "
                       + "VALUES (?, ?, ?, ?, ?);";
            
            PreparedStatement st = connection.prepareStatement(sql);
            
            st.setString(1, student.getName());
            st.setString(2, student.getEmail());
            st.setString(3, student.getPhoneNumber());
            st.setDate(4, Date.valueOf(student.getEnrollmentDate()));
            st.setString(5, student.getCourse());
            st.executeUpdate();
            st.close();
            
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        
        return status;
    }

    public Student get(int id) {
        Student student = null;
        
        try {
            Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            String sql = "SELECT * FROM students WHERE id=" + id;
            
            ResultSet rs = st.executeQuery(sql);    
            
            if (rs.next()) {            
                student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    rs.getDate("enrollment_date").toLocalDate(),
                    rs.getString("course")
                );
            }
            
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        return student;
    }
    
    public List<Student> get() {
        return get("");
    }

    public List<Student> getOrderByID() {
        return get("id");        
    }

    public List<Student> getOrderByName() {
        return get("name");        
    }
    
    private List<Student> get(String orderBy) {
        List<Student> students = new ArrayList<Student>();
        
        try {
            Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            String sql = "SELECT * FROM students" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            
            ResultSet rs = st.executeQuery(sql);   
            
            while (rs.next()) {                    
                Student s = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    rs.getDate("enrollment_date").toLocalDate(),
                    rs.getString("course")
                );
                
                students.add(s);
            }
            
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        return students;
    }
    
    public List<Student> getOrderByCourse() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY course";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setCourse(rs.getString("course"));
                student.setEnrollmentDate(rs.getDate("enrollment_date").toLocalDate()); // Corrigi o campo
                students.add(student);
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
    
    public boolean update(Student student) {
        boolean status = false;
        
        try {  
            String sql = "UPDATE students SET name = ?, email = ?, phone_number = ?, "
                       + "enrollment_date = ?, course = ? WHERE id = ?";
            
            PreparedStatement st = connection.prepareStatement(sql);
            
            st.setString(1, student.getName());
            st.setString(2, student.getEmail());
            st.setString(3, student.getPhoneNumber());
            st.setDate(4, Date.valueOf(student.getEnrollmentDate()));
            st.setString(5, student.getCourse());
            st.setInt(6, student.getId());
            st.executeUpdate();
            
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        
        return status;
    }
    
    public boolean delete(int id) {
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
}