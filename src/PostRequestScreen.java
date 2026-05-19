import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PostRequestScreen extends JFrame {

    private JComboBox<Person> cbMembers;
    private JTextField txtSkillNeeded;
    private JTextArea txtDescription;
    private JLabel lblError;

    public PostRequestScreen() {
        setTitle("Post a Service Request");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Title
        JLabel lblTitle = new JLabel("Post a Service Request");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // 2. Select Member Dropdown (Who is posting the request?)
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Posted By *"), gbc);
        
        cbMembers = new JComboBox<>();
        List<Person> people = DatabaseManager.getAllPersons();
        for (Person p : people) {
            cbMembers.addItem(p);
        }
        gbc.gridx = 1;
        add(cbMembers, gbc);

        // 3. Skill Needed Input
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Skill Needed * (e.g. Laptop repair)"), gbc);
        txtSkillNeeded = new JTextField(20);
        gbc.gridx = 1;
        add(txtSkillNeeded, gbc);

        // 4. Description Text Area
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Description (Optional)"), gbc);
        
        txtDescription = new JTextArea(4, 20); // 4 rows tall
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        gbc.gridx = 1;
        add(scrollDesc, gbc);

        // 5. Error Label
        lblError = new JLabel(" ");
        lblError.setForeground(Color.RED);
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        add(lblError, gbc);

        // 6. Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnSubmit = new JButton("Submit Request");
        JButton btnBack = new JButton("Back to Dashboard");
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnBack);

        gbc.gridy = 5;
        add(buttonPanel, gbc);

        // ==========================================
        // BUTTON ACTIONS
        // ==========================================

        btnBack.addActionListener(e -> dispose());

        btnSubmit.addActionListener(e -> {
            Person selectedPerson = (Person) cbMembers.getSelectedItem();
            String skillNeeded = txtSkillNeeded.getText().trim();
            String description = txtDescription.getText().trim();

            // Validation: Cannot be empty
            if (selectedPerson == null || skillNeeded.isEmpty()) {
                lblError.setText("✖ Error: Skill Needed field cannot be empty.");
                return;
            }

            lblError.setText(" "); // Clear error

            // Create ServiceRequest object (Constructor auto-sets status to 'open')
            ServiceRequest newRequest = new ServiceRequest(selectedPerson.getUserId(), skillNeeded, description);
            
            // Save to Database
            boolean isSaved = DatabaseManager.addServiceRequest(newRequest);

            if (isSaved) {
                JOptionPane.showMessageDialog(this, "✔ Request posted! Status = OPEN");
                dispose(); // Close window and return to dashboard
            } else {
                lblError.setText("✖ Database error. Could not post request.");
            }
        });
    }
}