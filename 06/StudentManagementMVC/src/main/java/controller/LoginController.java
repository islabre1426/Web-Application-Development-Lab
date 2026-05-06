package controller;

import dao.UserDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private UserDAO userDAO;
    
    @Override
    public void init() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Display login page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session != null && session.getAttribute("user") != null) {
            // Already logged in, redirect to dashboard
            response.sendRedirect("dashboard");
            return;
        }
        
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }
    
    /**
     * Process login form
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Check required fields
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            // Set error and forward back to login
            request.setAttribute("error", "Username and password are required");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }
        
        User user = userDAO.authenticate(username, password);
        
        // Authentication successful
        if (user != null) {
            // Invalidate older session if exists (security reasons)
            HttpSession oldSession = request.getSession(false);
            
            if (oldSession != null) {
                oldSession.invalidate();
            }
            
            // Create new session and store user info
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("user", user);
            newSession.setAttribute("role", user.getRole());
            newSession.setAttribute("fullName", user.getFullName());
            
            int timeoutInMinutes = 30 * 60;
            newSession.setMaxInactiveInterval(timeoutInMinutes);
            
            if (user.isAdmin()) {
                response.sendRedirect("dashboard");
            } else {
                response.sendRedirect("student?action=list");
            }
        } else {
            // Authentication failed
            request.setAttribute("error", "Invalid username or password");
            request.setAttribute("username", username); // Keep username in form
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }
}