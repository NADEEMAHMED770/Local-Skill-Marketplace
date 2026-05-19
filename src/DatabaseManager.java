// This import gives us the Connection class — represents a live link to the database
import java.sql.Connection;

// This import gives us DriverManager — the class that actually opens the connection
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// This import gives us SQLException — the error that fires when database operations fail
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    // The URL tells Java where to find the database
    // "localhost" means the database is on this same computer
    // "3306" is the default MySQL port number
    // "skill_marketplace" is the database name we created in Workbench
    private static final String URL = "jdbc:mysql://localhost:3306/skill_marketplace";

    // The MySQL username — default is root
    private static final String USER = "root";

    // The password YOU set during MySQL installation — change this to your password
    private static final String PASSWORD = "admin";

    // This variable will hold our connection once it is opened
    private static Connection connection = null;

    // This method opens the connection and returns it
    // Any class in the project can call this to get the database connection
    public static Connection getConnection() {

        try {
            // Check if connection already exists and is still open
            // If yes, return it — no need to reconnect
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            // DriverManager.getConnection() opens the actual connection to MySQL
            // It uses the URL, username, and password we defined above
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // If we reach this line, connection succeeded
            System.out.println("Connected to MySQL successfully.");

            return connection;

        } catch (SQLException e) {
            // This runs if connection fails
            // e.getMessage() gives the exact reason it failed
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    // This method closes the connection cleanly when the app shuts down
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
    // ==========================================
    // 1. CREATE (INSERT) OPERATIONS
    // ==========================================

    // Registers a new member into the 'users' table
    public static boolean addPerson(Person person) {
        String query = "INSERT INTO users (name, phone, area) VALUES (?, ?, ?)";
        try {
            Connection conn = getConnection();
            if (conn == null) return false;

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, person.getName());
            pstmt.setString(2, person.getPhone());
            pstmt.setString(3, person.getArea());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Database error while adding person: " + e.getMessage());
            return false;
        }
    }

    // Adds a new skill into the 'skills' table
    public static boolean addSkill(Skill skill) {
        String query = "INSERT INTO skills (user_id, skill_name, category, description, price_per_hour, is_available) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = getConnection();
            if (conn == null) return false;

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, skill.getUserId());
            pstmt.setString(2, skill.getSkillName());
            pstmt.setString(3, skill.getCategory());
            pstmt.setString(4, skill.getDescription());
            pstmt.setDouble(5, skill.getPricePerHour());
            pstmt.setBoolean(6, skill.isAvailable());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Database error while adding skill: " + e.getMessage());
            return false;
        }
    }

    // Posts a new service request into the 'service_requests' table
    public static boolean addServiceRequest(ServiceRequest request) {
        String query = "INSERT INTO service_requests (posted_by, skill_needed, description, status) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = getConnection();
            if (conn == null) return false;

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, request.getPostedBy());
            pstmt.setString(2, request.getSkillNeeded());
            pstmt.setString(3, request.getDescription());
            pstmt.setString(4, request.getStatus());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Database error while adding service request: " + e.getMessage());
            return false;
        }
    }

    // ==========================================
    // 2. READ (SELECT) OPERATIONS
    // ==========================================

    // Fetches all registered members from the 'users' table
    public static List<Person> getAllPersons() {
        List<Person> people = new ArrayList<>();
        String query = "SELECT * FROM users";
        
        try {
            Connection conn = getConnection();
            if (conn == null) return people;

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Person p = new Person(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("area")
                );
                people.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Database error while fetching persons: " + e.getMessage());
        }
        return people;
    }

    // Fetches a specific person by their ID (useful for joining data in the UI)
    public static Person getPersonById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) return null;

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Person(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("area")
                );
            }
        } catch (SQLException e) {
            System.out.println("Database error while fetching person by ID: " + e.getMessage());
        }
        return null;
    }

    // Fetches all skills from the 'skills' table
    public static List<Skill> getAllSkills() {
        List<Skill> skills = new ArrayList<>();
        String query = "SELECT * FROM skills";
        
        try {
            Connection conn = getConnection();
            if (conn == null) return skills;

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Skill s = new Skill(
                    rs.getInt("skill_id"),
                    rs.getInt("user_id"),
                    rs.getString("skill_name"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getDouble("price_per_hour"),
                    rs.getBoolean("is_available")
                );
                skills.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Database error while fetching skills: " + e.getMessage());
        }
        return skills;
    }

    // Fetches all service requests from the 'service_requests' table
    public static List<ServiceRequest> getAllServiceRequests() {
        List<ServiceRequest> requests = new ArrayList<>();
        String query = "SELECT * FROM service_requests";
        
        try {
            Connection conn = getConnection();
            if (conn == null) return requests;

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ServiceRequest sr = new ServiceRequest(
                    rs.getInt("request_id"),
                    rs.getInt("posted_by"),
                    rs.getString("skill_needed"),
                    rs.getString("description"),
                    rs.getString("status")
                );
                requests.add(sr);
            }
        } catch (SQLException e) {
            System.out.println("Database error while fetching service requests: " + e.getMessage());
        }
        return requests;
    }

    // ==========================================
    // 3. UPDATE OPERATIONS
    // ==========================================

    // Updates the status of a service request (e.g., to "fulfilled" or "cancelled")
    public static boolean updateRequestStatus(int requestId, String newStatus) {
        String query = "UPDATE service_requests SET status = ? WHERE request_id = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) return false;

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, requestId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Database error while updating request status: " + e.getMessage());
            return false;
        }
    }
    // ==========================================
    // 4. AUTHENTICATION (LOGIN & REGISTER)
    // ==========================================

    // Checks if a username is already taken
    public static boolean isUsernameTaken(String username) {
        String query = "SELECT username FROM accounts WHERE username = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) return true; // Default to true to prevent errors

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next(); // Returns true if a record exists
        } catch (SQLException e) {
            System.out.println("Database error checking username: " + e.getMessage());
            return true; 
        }
    }

    // Registers a new account for logging in
    public static boolean registerAccount(String fullName, String username, String phone, String password) {
        String query = "INSERT INTO accounts (full_name, username, phone, password) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = getConnection();
            if (conn == null) return false;

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, fullName);
            pstmt.setString(2, username);
            pstmt.setString(3, phone);
            pstmt.setString(4, SecurityHelper.hashPassword(password));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Database error while registering account: " + e.getMessage());
            return false;
        }
    }

    // Verifies the username and password for login
    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM accounts WHERE username = ? AND password = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) return false;

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, SecurityHelper.hashPassword(password));
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next(); // Returns true if credentials match
        } catch (SQLException e) {
            System.out.println("Database error during authentication: " + e.getMessage());
            return false;
        }
    }
    // ==========================================
    // 5. PASSWORD RESET 
    // ==========================================

    // Verifies if the username and phone match an existing account
    public static boolean verifyAccountForReset(String username, String phone) {
        String query = "SELECT account_id FROM accounts WHERE username = ? AND phone = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) return false;

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, phone);
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next(); // Returns true if it finds a match
        } catch (SQLException e) {
            System.out.println("Database error during verification: " + e.getMessage());
            return false;
        }
    }

    // Updates the password for a specific user (hashes the new password first!)
    public static boolean updatePassword(String username, String newPassword) {
        String query = "UPDATE accounts SET password = ? WHERE username = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) return false;

            PreparedStatement pstmt = conn.prepareStatement(query);
            // Hash the new password using the SecurityHelper we built earlier
            pstmt.setString(1, SecurityHelper.hashPassword(newPassword)); 
            pstmt.setString(2, username);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Database error updating password: " + e.getMessage());
            return false;
        }
    }
}