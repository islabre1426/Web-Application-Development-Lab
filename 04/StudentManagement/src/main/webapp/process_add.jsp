<%-- 
    Document   : process_add
    Created on : Apr 22, 2026, 3:13:22 PM
    Author     : islabre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Student Adding Process</title>
</head>
<body>
<%
    String studentCode = request.getParameter("student-code");
    String fullName = request.getParameter("full-name");
    String email = request.getParameter("email");
    String major = request.getParameter("major");
    
    if (studentCode == null || studentCode.trim().isEmpty() ||
        fullName == null || fullName.trim().isEmpty()) {
        response.sendRedirect("add_student.jsp?message=Error: Required fields are missing.");
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
        String sql = "INSERT INTO students (student_code, full_name, email, major) VALUES (?, ?, ?, ?)";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, studentCode);
        pstmt.setString(2, fullName);
        pstmt.setString(3, email);
        pstmt.setString(4, major);
        
        int rowsAffected = pstmt.executeUpdate();
        
        if (rowsAffected > 0) {
            response.sendRedirect("list_students.jsp?message=Student added successfully");
        } else {
            response.sendRedirect("add_student.jsp?message=Error: Failed to add student");
        }
        
        // Handle errors
        } catch (ClassNotFoundException e) {
            response.sendRedirect("add_student.jsp?message=Error: JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            String errorMsg = e.getMessage();
            if (errorMsg.contains("Duplicate entry")) {
                response.sendRedirect("add_student.jsp?message=Error: Student code already exists");
            } else {
                response.sendRedirect("add_student.jsp?message=Error: Database error");
            }
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
