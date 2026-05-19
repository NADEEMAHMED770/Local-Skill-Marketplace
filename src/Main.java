import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("Starting Local Skill Marketplace...");
        
        // 1. Initialize the database connection
        DatabaseManager.getConnection();

        // 2. Set the UI to match the operating system's modern look
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set modern UI: " + e.getMessage());
        }
        
        // 3. Launch the Login Screen safely
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginScreen login = new LoginScreen();
                login.setVisible(true);
            }
        });
    }
}