<%-- 
    Document   : edit_student
    Created on : Apr 27, 2026, 8:05:47 PM
    Author     : Islabre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Student</title>
    <style>
        input[readonly] {
            cursor: not-allowed;
        }
    </style>
</head>
<body>
    <h1>Edit Student</h1>
    
    <% if (request.getParameter("message") != null) { %>
        <div class="message">
            <%= request.getParameter("message") %>
        </div>
    <% } %>
    
    <form method="POST" action="process_edit.jsp">
        <table>
    <%
        String id = request.getParameter("id");
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_management",
                "root",
                "Q@jaP*96T8xY#2^7&3@7"
            );
            
            String sql = "SELECT * FROM students WHERE id = ?";
        
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String studentCode = rs.getString("student_code");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String major = rs.getString("major");
    %>
            <tr>
                <td>Student Code</td>
                <td>
                    <input type="text" name="student-code" id="student-code" required readonly value="<%= studentCode %>">
                </td>
            </tr>
            <tr>
                <td>Full Name</td>
                <td>
                    <input type="text" name="full-name" id="full-name" required value="<%= fullName %>">
                </td>
            </tr>
            <tr>
                <td>Email</td>
                <td>
                    <input type="email" name="email" id="email" value="<%= email %>">
                </td>
            </tr>
            <tr>
                <td>Major</td>
                <td>
                    <input type="text" name="major" id="major" value="<%= major %>">
                </td>
            </tr>
            <input type="hidden" name="id" id="id" value="<%= id %>">
            <tr>
                <td></td>
                <td>
                    <input type="submit" value="Submit">
                    <a href="list_students.jsp">
                        <button type="button">Cancel</button>
                    </a>
                </td>
            </tr>
    <%
            }
        // Handle errors
        } catch (ClassNotFoundException e) {
            response.sendRedirect("edit_student.jsp?message=Error: JDBC Driver not found!");
        } catch (SQLException e) {
            response.sendRedirect("edit_student.jsp?message=Error: Database Error: " + e.getMessage());
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
        </table>
    </form>
</body>
</html>
