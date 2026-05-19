import javax.swing.*;
import java.awt.*;

public class RegisterMemberScreen extends JFrame {

    // Declare our input fields and error label at the class level 
    // so we can read them when the Save button is clicked
    private JTextField txtName, txtPhone, txtArea;
    private JLabel lblError;

    public RegisterMemberScreen() {
        setTitle("Register New Member");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Closes ONLY this window, not the whole app
        setLocationRelativeTo(null); // Centers window
        
        // GridBagLayout gives us a highly customizable grid for forms
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adds padding between elements
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Title
        JLabel lblTitle = new JLabel("Register New Member");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; // Spans across 2 columns
        add(lblTitle, gbc);

        // 2. Full Name Input
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Full Name *"), gbc);
        txtName = new JTextField(20);
        gbc.gridx = 1;
        add(txtName, gbc);

        // 3. Phone Number Input
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Phone Number *"), gbc);
        txtPhone = new JTextField(20);
        gbc.gridx = 1;
        add(txtPhone, gbc);

        // 4. Area / Locality Input
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Area / Locality *"), gbc);
        txtArea = new JTextField(20);
        gbc.gridx = 1;
        add(txtArea, gbc);

        // 5. Error Message Label (Hidden by default)
        lblError = new JLabel(" ");
        lblError.setForeground(Color.RED);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(lblError, gbc);

        // 6. Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Save Member");
        JButton btnBack = new JButton("Back to Dashboard");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnBack);

        gbc.gridy = 5;
        add(buttonPanel, gbc);

        // ==========================================
        // BUTTON ACTIONS
        // ==========================================
        
        btnBack.addActionListener(e -> dispose()); // Closes this screen to reveal Dashboard

        btnSave.addActionListener(e -> {
            // Get text and remove accidental spaces
            String name = txtName.getText().trim();
            String phone = txtPhone.getText().trim();
            String area = txtArea.getText().trim();

            // Validate inputs (Exception Handling as per project docs)
            if (name.isEmpty() || phone.isEmpty() || area.isEmpty()) {
                lblError.setText("✖ Error: All fields cannot be empty");
                return; // Stop execution here
            }

            lblError.setText(" "); // Clear errors if validation passes

            // Save to MySQL
            Person newMember = new Person(name, phone, area);
            boolean isSaved = DatabaseManager.addPerson(newMember);

            if (isSaved) {
                // Show green success toast/popup
                JOptionPane.showMessageDialog(this, "✔ Member '" + name + "' saved successfully!");
                
                // Ask if they want to add a skill immediately
                int choice = JOptionPane.showConfirmDialog(this, 
                    "Member saved! Add a skill for this member?", 
                    "Next Step", 
                    JOptionPane.YES_NO_OPTION);
                
                if (choice == JOptionPane.YES_OPTION) {
                    new AddSkillScreen().setVisible(true); // Opens the Add Skill screen
                    dispose(); // Closes the Registration screen
                } else {
                    dispose(); // Close form and return to dashboard
                }
            } else {
                lblError.setText("✖ Database error. Could not save.");
            }
        });
    }
}