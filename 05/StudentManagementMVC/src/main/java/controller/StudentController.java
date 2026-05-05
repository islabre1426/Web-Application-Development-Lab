/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.StudentDAO;
import model.Student;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Islabre
 */
@WebServlet("/student")
public class StudentController extends HttpServlet {
    private StudentDAO studentDAO;
    
    @Override
    public void init() {
        this.studentDAO = new StudentDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) action = "list";
        
        switch (action) {
            case "list": listStudents(request, response); break;
            case "new": showNewForm(request, response); break;
            case "edit": showEditForm(request, response); break;
            case "delete": deleteStudent(request, response); break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String action = request.getParameter("action");
    
        switch (action) {
            case "insert": insertStudent(request, response); break;
            case "update": updateStudent(request, response); break;
        }
    }
    
    
    private void listStudents(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Student> students = studentDAO.getAllStudents();
        request.setAttribute("students", students);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student-list.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Student student = studentDAO.getStudentById(Integer.valueOf(id));
        
        if (student != null) {
            request.setAttribute("student", student);
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String id = request.getParameter("id");
        
        boolean success = studentDAO.deleteStudent(Integer.valueOf(id));
        String message = "";
        
        if (success) {
            message = "Student deleted successfully";
            response.sendRedirect("student?action=list&message=" + message);
        } else {
            message = "Error: Failed to delete student";
            response.sendRedirect("student?action=list&error=" + message);   
        }
    }
    
    private void insertStudent(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String studentCode = request.getParameter("studentCode");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String major = request.getParameter("major");
        
        Student student = new Student(studentCode, fullName, email, major);
        
        boolean success = studentDAO.addStudent(student);
        String message = "";
        
        if (success) {
            message = "Student added successfully";
            response.sendRedirect("student?action=list&message=" + message);
        } else {
            message = "Error: Failed to add student";
            response.sendRedirect("student?action=list&error=" + message);   
        }
    }
    
    private void updateStudent(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String studentCode = request.getParameter("studentCode");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String major = request.getParameter("major");
        
        Student student = new Student(studentCode, fullName, email, major);
        student.setId(Integer.valueOf(id));
        
        boolean success = studentDAO.updateStudent(student);
        String message = "";
        
        if (success) {
            message = "Student updated successfully";
            response.sendRedirect("student?action=list&message=" + message);
        } else {
            message = "Error: Failed to update student";
            response.sendRedirect("student?action=list&error=" + message);   
        }
    }
}
