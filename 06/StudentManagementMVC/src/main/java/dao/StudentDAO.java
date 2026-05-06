/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Islabre
 */
public class StudentDAO {
    private final String url = "jdbc:mysql://localhost:3306/student_management";
    private final String username = "root";
    private final String password = "Q@jaP*96T8xY#2^7&3@7";
    
    // Add this main method to test (remove after testing)
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();
        List<Student> students = dao.getAllStudents();
        for (Student s : students) {
            System.out.println(s);
        }
    }
    
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(this.url, this.username, this.password);
        } catch (ClassNotFoundException e) {
            throw new Error("Error: MySQL Driver not found");
        }
    }
    
    public List<Student> getAllStudents() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        List<Student> students = new ArrayList<>();
        
        String sql = "SELECT * FROM students ORDER BY id";
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Student s = new Student(
                    rs.getString("student_code"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("major")
                );
                
                s.setId(rs.getInt("id"));
                s.setCreatedAt(rs.getTimestamp("created_at"));
                
                students.add(s);
            }
            
            return students;
        } catch (SQLException e) {
            throw new Error("Database Error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
                if (pstmt != null) pstmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public int getTotalStudents() {
        int total = 0;
        String sql = "SELECT COUNT(*) as total FROM students";
        
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new Error("Database Error: " + e.getMessage());
        }
        
        return total;
    }
    
    public Student getStudentById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM students WHERE id = ?";
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Student s = new Student(
                    rs.getString("student_code"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("major")
                );
                
                s.setId(rs.getInt("id"));
                s.setCreatedAt(rs.getTimestamp("created_at"));
                
                return s;
            }
            
            return null;
        } catch (SQLException e) {
            throw new Error("Database Error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
                if (pstmt != null) pstmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean addStudent(Student student) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        String sql = "INSERT INTO students (student_code, full_name, email, major) VALUES (?, ?, ?, ?)";
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getStudentCode());
            pstmt.setString(2, student.getFullName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getMajor());
            
            int rowsAffected = pstmt.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new Error("Database Error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateStudent(Student student) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        String sql = "UPDATE students SET student_code = ?, full_name = ?, email = ?, major = ? WHERE id = ?";
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getStudentCode());
            pstmt.setString(2, student.getFullName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getMajor());
            pstmt.setInt(5, student.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new Error("Database Error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean deleteStudent(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        String sql = "DELETE FROM students WHERE id = ?";
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new Error("Database Error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
