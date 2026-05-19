import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class BrowseSkillsScreen extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public BrowseSkillsScreen() {
        setTitle("Browse Skills");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Top Panel: Live Search Bar
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.add(new JLabel("Search skills or category: "));
        
        JTextField txtSearch = new JTextField(30);
        topPanel.add(txtSearch);
        add(topPanel, BorderLayout.NORTH);

        // 2. Center Panel: The Data Table
        // We add a hidden 7th column ("UserID") to store the ID for the contact popup
        String[] columns = {"Skill Name", "Category", "Provider", "Area", "Price", "Available", "UserID"};
        
        // Prevent users from editing the text inside the table directly
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        table = new JTable(tableModel);
        table.setSelectionBackground(Color.YELLOW); // Highlights row yellow as requested
        table.setSelectionForeground(Color.BLACK);
        
        // Setup the live sorter/filter
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Hide the "UserID" column so it doesn't show on screen
        table.getColumnModel().getColumn(6).setMinWidth(0);
        table.getColumnModel().getColumn(6).setMaxWidth(0);
        table.getColumnModel().getColumn(6).setWidth(0);

        // Load data from MySQL
        loadTableData();

        // Wrap table in a scroll pane so we get scrollbars if there are many rows
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(scrollPane, BorderLayout.CENTER);

        // 3. Bottom Panel: Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnContact = new JButton("Show Contact Info");
        JButton btnBack = new JButton("Back to Dashboard");
        
        bottomPanel.add(btnContact);
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        // ==========================================
        // ACTIONS & LISTENERS
        // ==========================================

        // Action: Close window
        btnBack.addActionListener(e -> dispose());

        // Action: Live Search Filtering
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }
            
            private void filter() {
                String text = txtSearch.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null); // Show all
                } else {
                    // (?i) makes the search case-insensitive
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // Action: Show Contact Popup
        btnContact.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            
            // Error handling: No row selected
            if (viewRow == -1) {
                JOptionPane.showMessageDialog(this, 
                    "⚠ No row selected — click a row first, then click Show Contact Info", 
                    "Warning", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convert view row to model row (in case the table is currently filtered/sorted)
            int modelRow = table.convertRowIndexToModel(viewRow);
            
            // Get the hidden UserID we stored in column 6
            int userId = (int) tableModel.getValueAt(modelRow, 6);
            
            // Fetch the person from database to get their phone number
            Person provider = DatabaseManager.getPersonById(userId);
            
            if (provider != null) {
                String message = provider.getName() + " | Phone: " + provider.getPhone() + " | Area: " + provider.getArea();
                JOptionPane.showMessageDialog(this, message, "Contact Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // Helper method to pull data from DB and fill the table
    private void loadTableData() {
        List<Skill> skills = DatabaseManager.getAllSkills();
        for (Skill s : skills) {
            Person p = DatabaseManager.getPersonById(s.getUserId());
            if (p != null) {
                tableModel.addRow(new Object[]{
                    s.getSkillName(),
                    s.getCategory(),
                    p.getName(),
                    p.getArea(),
                    "Rs " + s.getPricePerHour(),
                    s.isAvailable() ? "✔ Yes" : "✗ No",
                    s.getUserId() // Hidden column data
                });
            }
        }
    }
}