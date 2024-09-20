package dao;

import model.Student;

import java.sql.*;
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
        
        String sql = "INSERT INTO students (name, email, phone_number, enrollment_date, course) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, student.getName());
            st.setString(2, student.getEmail());
            st.setString(3, student.getPhoneNumber());
            st.setTimestamp(4, Timestamp.valueOf(student.getEnrollmentDate()));
            st.setString(5, student.getCourse());
            st.executeUpdate();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        
        return status;
    }

    public Student get(int id) {
        Student student = null;
        String sql = "SELECT * FROM students WHERE id = ?";
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    student = new Student(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                                          rs.getString("phone_number"), 
                                          rs.getTimestamp("enrollment_date").toLocalDateTime(),
                                          rs.getString("course"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return student;
    }

    public List<Student> get() {
        return get("");
    }

    public List<Student> getOrderById() {
        return get("id");
    }

    public List<Student> getOrderByName() {
        return get("name");
    }

    private List<Student> get(String orderBy) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));

        try (Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Student s = new Student(rs.getInt("id"), rs.getString("name"), rs.getString("email"), 
                                        rs.getString("phone_number"), 
                                        rs.getTimestamp("enrollment_date").toLocalDateTime(),
                                        rs.getString("course"));
                students.add(s);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return students;
    }

    public boolean update(Student student) {
        boolean status = false;
        String sql = "UPDATE students SET name = ?, email = ?, phone_number = ?, enrollment_date = ?, course = ? WHERE id = ?";
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, student.getName());
            st.setString(2, student.getEmail());
            st.setString(3, student.getPhoneNumber());
            st.setTimestamp(4, Timestamp.valueOf(student.getEnrollmentDate()));
            st.setString(5, student.getCourse());
            st.setInt(6, student.getId());
            st.executeUpdate();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        
        return status;
    }

    public boolean delete(int id) {
        boolean status = false;
        String sql = "DELETE FROM students WHERE id = ?";
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        
        return status;
    }
}