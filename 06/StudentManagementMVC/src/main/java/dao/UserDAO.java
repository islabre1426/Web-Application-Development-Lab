package dao;

import model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class UserDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Q@jaP*96T8xY#2^7&3@7";
    
    // TODO: SQL queries constants
    private static final String SQL_AUTHENTICATE = 
        "SELECT * FROM users WHERE username = ? AND is_active = TRUE";
    
    private static final String SQL_UPDATE_LAST_LOGIN = 
        "UPDATE users SET last_login = NOW() WHERE id = ?";
    
    private static final String SQL_GET_BY_ID = 
        "SELECT * FROM users WHERE id = ?";
    
    /**
     * Connect to the database
     * @return Connection object if successful
     * @throws SQLException on database error
     */
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
    }
    
    /**
     * Authenticate user with username and password
     * @return User object if successful, null otherwise
     * @throws SQLException on database error
     */
    public User authenticate(String username, String password) {
        User user = null;
        
        try (
            Connection conn = getConnection();
            PreparedStatement authStmt = conn.prepareStatement(SQL_AUTHENTICATE);
        ) {
            authStmt.setString(1, username);
            
            ResultSet rs = authStmt.executeQuery();
            
            // User found in the databse
            while (rs.next()) {
                // Verify user password
                String hashedPassword = rs.getString("password");
                
                // Valid password
                if (BCrypt.checkpw(password, hashedPassword)) {
                    user = mapResultSetToUser(rs);
                    updateLastLogin(user.getId());
                }
            }
        } catch (SQLException e) {
            throw new Error("Database error: " + e.getMessage());
        }
        
        return user;
    }
    
    // Update user's last login when authentication successful
    private void updateLastLogin(int userId) {
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_LAST_LOGIN);
        ) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new Error("Database error: " + e.getMessage());
        }
    }
    
    // Query user by id
    public User getUserById(int id) {
        User user = null;
        
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL_GET_BY_ID);
        ) {
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            
            // User found in the database
            while (rs.next()) {
               user = mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            throw new Error("Database error: " + e.getMessage());
        }
        
        return user;
    }
    
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User(
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("full_name"),
            rs.getString("role")
        );
        
        user.setId(rs.getInt("id"));
        user.setIsActive(rs.getBoolean("is_active"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setLastLogin(rs.getTimestamp("last_login"));
        
        return user;
    }
    
    // Add this main method to test (remove after testing)
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        // Test authentication
        User user = dao.authenticate("admin", "password123");
        if (user != null) {
            System.out.println("Authentication successful!");
            System.out.println(user);
        } else {
            System.out.println("Authentication failed!");
        }

        // Test with wrong password
        User invalidUser = dao.authenticate("admin", "wrongpassword");
        System.out.println("Invalid auth: " + (invalidUser == null ? "Correctly rejected" : "ERROR!"));
    }
}