<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${student != null}">Edit Student</c:when>
            <c:otherwise>Add New Student</c:otherwise>
        </c:choose>
    </title>
    <style>
        button a {
            color: inherit;
            text-decoration: none;
        }
        
        input[readonly] {
            cursor: not-allowed;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>
            <c:choose>
                <c:when test="${student != null}">Edit Student</c:when>
                <c:otherwise>Add New Student</c:otherwise>
            </c:choose>
        </h1>
        
        <form action="student" method="POST">
            <input type="hidden" name="action" value="${student != null ? 'update' : 'insert'}">
            
            <c:if test="${student != null}">
                <input type="hidden" name="id" value="${student.getId()}">
            </c:if>
                
            <table>
                <tr>
                    <td>Student Code</td>
                    <td>
                        <input type="text" name="studentCode"
                               value="${student.getStudentCode()}" ${student != null ? 'readonly' : 'required'}>
                    </td>
                </tr>
                <tr>
                    <td>Full Name</td>
                    <td>
                        <input type="text" name="fullName" value="${student != null ? student.getFullName() : ''}">
                    </td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td>
                        <input type="email" name="email" value="${student != null ? student.getEmail() : ''}">
                    </td>
                </tr>
                <tr>
                    <td>Major</td>
                    <td>
                        <input type="text" name="major" value="${student != null ? student.getMajor() : ''}">
                    </td>
                </tr>
                <tr>
                    <td>
                        <button>
                            <a href="student?action=list">Cancel</a>
                        </button>
                    </td>
                    <td>
                        <input type="submit" value="${student != null ? 'Save' : 'Add'}">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>