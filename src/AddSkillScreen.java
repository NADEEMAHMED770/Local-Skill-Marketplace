import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddSkillScreen extends JFrame {

    private JComboBox<Person> cbMembers;
    private JComboBox<String> cbCategory;
    private JTextField txtSkillName;
    private JTextField txtPrice;
    private JCheckBox chkAvailable;
    private JLabel lblError;

    public AddSkillScreen() {
        setTitle("Add New Skill");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Title
        JLabel lblTitle = new JLabel("Add New Skill");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // 2. Select Member Dropdown
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Select Member *"), gbc);
        
        // Fetch real people from Database!
        cbMembers = new JComboBox<>();
        List<Person> people = DatabaseManager.getAllPersons();
        for (Person p : people) {
            cbMembers.addItem(p);
        }
        gbc.gridx = 1;
        add(cbMembers, gbc);

        // 3. Category Dropdown
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Category *"), gbc);
        String[] categories = {"Education", "Repair", "Domestic", "Technology", "Other"};
        cbCategory = new JComboBox<>(categories);
        gbc.gridx = 1;
        add(cbCategory, gbc);

        // 4. Skill Name Input
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Skill Name * (e.g. Math Tutor)"), gbc);
        txtSkillName = new JTextField(20);
        gbc.gridx = 1;
        add(txtSkillName, gbc);

        // 5. Price / Hour Input
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Price / Hour (Rs)"), gbc);
        txtPrice = new JTextField(20);
        gbc.gridx = 1;
        add(txtPrice, gbc);

        // 6. Availability Checkbox
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        chkAvailable = new JCheckBox("Currently Available for Hire", true);
        add(chkAvailable, gbc);

        // 7. Error Label
        lblError = new JLabel(" ");
        lblError.setForeground(Color.RED);
        gbc.gridy = 6;
        add(lblError, gbc);

        // 8. Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Save Skill");
        JButton btnBack = new JButton("Back to Dashboard");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnBack);

        gbc.gridy = 7;
        add(buttonPanel, gbc);

        // ==========================================
        // BUTTON ACTIONS
        // ==========================================

        btnBack.addActionListener(e -> dispose());

        btnSave.addActionListener(e -> {
            Person selectedPerson = (Person) cbMembers.getSelectedItem();
            String category = (String) cbCategory.getSelectedItem();
            String skillName = txtSkillName.getText().trim();
            String priceStr = txtPrice.getText().trim();
            boolean isAvailable = chkAvailable.isSelected();

            // Basic Empty Check
            if (selectedPerson == null || skillName.isEmpty() || priceStr.isEmpty()) {
                lblError.setText("✖ Error: Please fill all required fields.");
                return;
            }

            double price = 0;
            // Strict Exception Handling for Numbers
            try {
                price = Double.parseDouble(priceStr);
                lblError.setText(" "); // Clear error
            } catch (NumberFormatException ex) {
                lblError.setText("✖ Error: Enter valid number for price (no letters).");
                return; // Stop saving
            }

            // Save to Database
            Skill newSkill = new Skill(selectedPerson.getUserId(), skillName, category, "", price, isAvailable);
            boolean isSaved = DatabaseManager.addSkill(newSkill);

            if (isSaved) {
                JOptionPane.showMessageDialog(this, "✔ Skill saved successfully!");
                dispose(); // Close window after saving
            } else {
                lblError.setText("✖ Database error. Could not save skill.");
            }
        });
    }
}