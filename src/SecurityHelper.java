import java.security.MessageDigest;

public class SecurityHelper {

    // This method takes a plain text password and returns a scrambled SHA-256 hash
    public static String hashPassword(String password) {
        try {
            // Get the SHA-256 algorithm built into Java
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Hash the password
            byte[] encodedhash = digest.digest(password.getBytes("UTF-8"));
            
            // Convert the raw bytes into a readable Hex string format
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
            
        } catch (Exception ex) {
            System.out.println("Error hashing password: " + ex.getMessage());
            return null;
        }
    }
}