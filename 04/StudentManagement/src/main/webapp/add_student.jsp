<%-- 
    Document   : process_add
    Created on : Apr 22, 2026, 2:56:45 PM
    Author     : islabre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add Student</title>
</head>
<body>
    <h1>Add Student</h1>
    
    <% if (request.getParameter("message") != null) { %>
        <div class="message">
            <%= request.getParameter("message") %>
        </div>
    <% } %>
    
    <form method="POST" action="process_add.jsp">
        <table>
            <tr>
                <td>Student Code</td>
                <td>
                    <input type="text" name="student-code" id="student-code" required>
                </td>
            </tr>
            <tr>
                <td>Full Name</td>
                <td>
                    <input type="text" name="full-name" id="full-name" required>
                </td>
            </tr>
            <tr>
                <td>Email</td>
                <td>
                    <input type="email" name="email" id="email">
                </td>
            </tr>
            <tr>
                <td>Major</td>
                <td>
                    <input type="text" name="major" id="major">
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input type="submit" value="Submit">
                    <a href="list_students.jsp">
                        <button type="button">Cancel</button>
                    </a>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
