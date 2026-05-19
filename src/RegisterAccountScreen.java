import javax.swing.*;
import java.awt.*;

public class RegisterAccountScreen extends JFrame {

    private JTextField txtFullName, txtUsername, txtPhone;
    private JPasswordField txtPassword;
    private JLabel lblError;

    public RegisterAccountScreen() {
        setTitle("Register New Account");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("Create Your Account", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // Inputs
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Full Name *"), gbc);
        txtFullName = new JTextField(20);
        gbc.gridx = 1; add(txtFullName, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Username *"), gbc);
        txtUsername = new JTextField(20);
        gbc.gridx = 1; add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Phone Number *"), gbc);
        txtPhone = new JTextField(20);
        gbc.gridx = 1; add(txtPhone, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Password *"), gbc);
        txtPassword = new JPasswordField(20); // Hides typed text
        gbc.gridx = 1; add(txtPassword, gbc);

        // Error Label
        lblError = new JLabel(" ");
        lblError.setForeground(Color.RED);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(lblError, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnRegister = new JButton("REGISTER");
        JButton btnBack = new JButton("Back to Login");
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);

        gbc.gridy = 6;
        add(buttonPanel, gbc);

        // ==========================================
        // ACTIONS
        // ==========================================

        btnBack.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            dispose();
        });

        btnRegister.addActionListener(e -> {
            String name = txtFullName.getText().trim();
            String username = txtUsername.getText().trim();
            String phone = txtPhone.getText().trim();
            String password = new String(txtPassword.getPassword()); // Get pass from JPasswordField

            // 1. Check for empty fields
            if (name.isEmpty() || username.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                lblError.setText("✖ Error: All fields are required.");
                return;
            }

            // 2. Check for duplicate username
            if (DatabaseManager.isUsernameTaken(username)) {
                lblError.setText("✖ Error: Username already taken.");
                return;
            }

            // 3. Save to database
            boolean isSaved = DatabaseManager.registerAccount(name, username, phone, password);

            if (isSaved) {
                JOptionPane.showMessageDialog(this, "✔ Registered successfully! Redirecting to Login...");
                new LoginScreen().setVisible(true);
                dispose(); // Close register screen
            } else {
                lblError.setText("✖ Database error. Could not register.");
            }
        });
    }
}