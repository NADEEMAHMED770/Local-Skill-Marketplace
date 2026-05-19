import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblError;

    public LoginScreen() {
        setTitle("Aror university — Login");
        setSize(400, 400); // Slightly taller to fit the new button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("Login", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // Username
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Username:"), gbc);
        txtUsername = new JTextField(15);
        gbc.gridx = 1; add(txtUsername, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        txtPassword = new JPasswordField(15);
        gbc.gridx = 1; add(txtPassword, gbc);

        // Error Label
        lblError = new JLabel(" ");
        lblError.setForeground(Color.RED);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(lblError, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnLogin = new JButton("LOGIN");
        JButton btnRegister = new JButton("Register Here");
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);

        gbc.gridy = 4;
        add(buttonPanel, gbc);

        // NEW: Forgot Password Button
        JButton btnForgot = new JButton("Forgot Password?");
        btnForgot.setForeground(Color.BLUE);
        btnForgot.setBorderPainted(false);
        btnForgot.setContentAreaFilled(false);
        btnForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridy = 5;
        add(btnForgot, gbc);

        // ==========================================
        // ACTIONS
        // ==========================================

        btnRegister.addActionListener(e -> {
            new RegisterAccountScreen().setVisible(true);
            dispose();
        });

        // Action for Forgot Password
        btnForgot.addActionListener(e -> {
            new ForgotPasswordScreen().setVisible(true);
            dispose();
        });

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                lblError.setText("✖ Please enter username and password.");
                return;
            }

            if (DatabaseManager.authenticateUser(username, password)) {
                SkillMarketplaceApp dashboard = new SkillMarketplaceApp(username);
                dashboard.setVisible(true);
                dispose(); 
            } else {
                lblError.setText("✖ Invalid username or password — try again");
            }
        });
    }
}