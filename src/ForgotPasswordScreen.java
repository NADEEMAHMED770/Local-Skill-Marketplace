import javax.swing.*;
import java.awt.*;

public class ForgotPasswordScreen extends JFrame {

    private JTextField txtUsername, txtPhone;
    private JPasswordField txtNewPassword;
    private JLabel lblError;

    public ForgotPasswordScreen() {
        setTitle("Reset Password");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("Reset Password", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // Username
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Username:"), gbc);
        txtUsername = new JTextField(15);
        gbc.gridx = 1; add(txtUsername, gbc);

        // Phone Number (for verification)
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Registered Phone:"), gbc);
        txtPhone = new JTextField(15);
        gbc.gridx = 1; add(txtPhone, gbc);

        // New Password
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("New Password:"), gbc);
        txtNewPassword = new JPasswordField(15);
        gbc.gridx = 1; add(txtNewPassword, gbc);

        // Error Label
        lblError = new JLabel(" ");
        lblError.setForeground(Color.RED);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(lblError, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnReset = new JButton("Reset Password");
        JButton btnBack = new JButton("Back to Login");
        buttonPanel.add(btnReset);
        buttonPanel.add(btnBack);

        gbc.gridy = 5;
        add(buttonPanel, gbc);

        // ==========================================
        // ACTIONS
        // ==========================================

        btnBack.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            dispose();
        });

        btnReset.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String phone = txtPhone.getText().trim();
            String newPassword = new String(txtNewPassword.getPassword());

            if (username.isEmpty() || phone.isEmpty() || newPassword.isEmpty()) {
                lblError.setText("✖ All fields are required.");
                return;
            }

            // Step 1: Verify the user owns this account
            if (DatabaseManager.verifyAccountForReset(username, phone)) {
                
                // Step 2: If verified, update the password
                if (DatabaseManager.updatePassword(username, newPassword)) {
                    JOptionPane.showMessageDialog(this, "✔ Password reset successfully! You can now log in.");
                    new LoginScreen().setVisible(true);
                    dispose();
                } else {
                    lblError.setText("✖ Database error. Could not reset.");
                }
            } else {
                lblError.setText("✖ Username and Phone number do not match.");
            }
        });
    }
}