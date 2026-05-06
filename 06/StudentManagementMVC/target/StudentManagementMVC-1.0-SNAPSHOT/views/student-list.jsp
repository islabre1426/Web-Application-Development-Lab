<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student List</title>
    <style>
        table, td, th {
            border: 1px solid;
            border-collapse: collapse;
        }
        
        th, td {
            padding: 0.25em;
        }
        
        button a {
            color: inherit;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <h1>Student Management (MVC)</h1>

    <c:if test="${not empty param.message}">
        <div class="success">${param.message}</div>
    </c:if>
    
    <c:if test="${not empty param.error}">
        <div class="success">${param.error}</div>
    </c:if>
    
    <button>
        <a href="student?action=new">
            Add Student
        </a>    
    </button>
    
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Code</th>
                <th>Name</th>
                <th>Email</th>
                <th>Major</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="student" items="${students}">
                <tr>
                    <td>${student.id}</td>
                    <td>${student.studentCode}</td>
                    <td>${student.fullName}</td>
                    <td>${student.email}</td>
                    <td>${student.major}</td>
                    <td>
                        <button>
                            <a href="student?action=edit&id=${student.id}">
                                Edit
                            </a>    
                        </button>
                                
                        <button>
                            <a href="student?action=delete&id=${student.id}"
                               onclick="confirm("Are you sure you want to delete this student?")">
                                Delete
                            </a>    
                        </button>
                    </td>
                </tr>
            </c:forEach>
            
            <c:if test="${empty students}">
                <tr>
                    <td colspan="6">No students found</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>