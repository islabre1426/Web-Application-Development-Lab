<%-- 
    Document   : list_students
    Created on : Apr 22, 2026, 2:16:39 PM
    Author     : islabre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Student Info</title>
    
    <style>
        table, td {
            border: 1px solid;
            border-collapse: collapse;
        }
        
        td {
            padding: 0.25em;
        }
    </style>
</head>
<body>
    <h1>Student Info</h1>
    
    <% if (request.getParameter("message") != null) { %>
        <div class="message">
            <%= request.getParameter("message") %>
        </div>
    <% } %>
    
    <table>
        <thead>
            <td>ID</td>
            <td>Student Code</td>
            <td>Full Name</td>
            <td>Email</td>
            <td>Major</td>
            <td>Created At</td>
        </thead>
        <tbody>
        <%
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            
            try {
                // Connect to database
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_management",
                    "root",
                    "Q@jaP*96T8xY#2^7&3@7"
                );
                
                // Query for students
                stmt = conn.createStatement();
                String sql = "SELECT * FROM students";
                rs = stmt.executeQuery(sql);
                
                // Get info
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String studentCode = rs.getString("student_code");
                    String fullName = rs.getString("full_name");
                    String email = rs.getString("email");
                    String major = rs.getString("major");
                    Timestamp createdAt = rs.getTimestamp("created_at");
        %>
                    <tr>
                        <td><%= id %></td>
                        <td><%= studentCode %></td>
                        <td><%= fullName %></td>
                        <td><%= email != null ? email : "N/A" %></td>
                        <td><%= major != null ? major : "N/A" %></td>
                        <td><%= createdAt %></td>
                    </tr>
        <%
                }
            // Handle errors
            } catch (ClassNotFoundException e) {
                out.println("<tr><td colspan='7'>Error: JDBC Driver not found!</td></tr>");
                e.printStackTrace();
            } catch (SQLException e) {
                out.println("<tr><td colspan='7'>Database Error: " + e.getMessage() + "</td></tr>");
                e.printStackTrace();
            // Close database connection
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        %>
        </tbody>
    </table>
</body>
</html>
