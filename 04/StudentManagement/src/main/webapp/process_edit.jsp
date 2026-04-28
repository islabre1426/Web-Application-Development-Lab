<%-- 
    Document   : process_edit
    Created on : Apr 27, 2026, 8:06:38 PM
    Author     : Islabre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Student Editing Process</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    String studentCode = request.getParameter("student-code");
    String fullName = request.getParameter("full-name");
    String email = request.getParameter("email");
    String major = request.getParameter("major");
    
    // Missing required fields
    if (studentCode == null || studentCode.trim().isEmpty() ||
        fullName == null || fullName.trim().isEmpty()) {
        response.sendRedirect("edit_student.jsp?message=Error: Required fields (Full Name, Student Code) are missing.");
        return;
    }
    
    // Invalid Student Code
    if (!studentCode.matches("[A-Z]{2}[0-9]{3,}")) {
        response.sendRedirect("edit_student.jsp?message=Error: Invalid Student Code format");
        return;
    }
    
    if (email != null && !email.isEmpty()) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            // Invalid email format
            response.sendRedirect("edit_student.jsp?message=Error: Invalid email format");
            return;
        }
    }
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    try {
        // Connect to database
        Class.forName("com.mysql.cj.jdbc.Driver");

        conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/student_management",
            "root",
            "Q@jaP*96T8xY#2^7&3@7"
        );

        // Insert info into database (prepare statement first to prevent SQL Injection Attack)
        String sql = "UPDATE students SET student_code = ?, full_name = ?, email = ?, major = ? WHERE id = ?";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, studentCode);
        pstmt.setString(2, fullName);
        pstmt.setString(3, email);
        pstmt.setString(4, major);
        pstmt.setString(5, id);
        
        int rowsAffected = pstmt.executeUpdate();
        
        if (rowsAffected > 0) {
            response.sendRedirect("list_students.jsp?message=Student updated successfully");
        } else {
            response.sendRedirect("edit_student.jsp?message=Error: Failed to update student");
        }
        
        // Handle errors
        } catch (ClassNotFoundException e) {
            response.sendRedirect("edit_student.jsp?message=Error: JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            response.sendRedirect("edit_student.jsp?message=Error: Database error: " + e.getMessage());
            e.printStackTrace();
            
        // Close database connection
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
%>
</body>
</html>
