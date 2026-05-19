import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class ViewRequestsScreen extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public ViewRequestsScreen() {
        setTitle("Service Requests");
        setSize(850, 500); // Made slightly wider for the new button
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Top Panel: Filter Buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAll = new JButton("All");
        JButton btnOpen = new JButton("Open Only");
        JButton btnFulfilled = new JButton("Fulfilled");
        JButton btnCancelled = new JButton("Cancelled");

        topPanel.add(new JLabel("Filter: "));
        topPanel.add(btnAll);
        topPanel.add(btnOpen);
        topPanel.add(btnFulfilled);
        topPanel.add(btnCancelled);
        add(topPanel, BorderLayout.NORTH);

        // 2. Center Panel: The Data Table
        String[] columns = {"ID", "Skill Needed", "Posted By", "Description", "Status"};
        
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        table = new JTable(tableModel);
        table.setSelectionBackground(Color.YELLOW);
        table.setSelectionForeground(Color.BLACK);
        
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(0).setMaxWidth(60);

        loadTableData(); 

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(scrollPane, BorderLayout.CENTER);

        // 3. Bottom Panel: Action Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnMarkFulfilled = new JButton("Mark as Fulfilled");
        JButton btnMarkCancelled = new JButton("Mark Cancelled");
        JButton btnFindMatch = new JButton("🔍 Find Matches"); // NEW BUTTON
        JButton btnBack = new JButton("Back to Dashboard");

        bottomPanel.add(btnFindMatch);
        bottomPanel.add(btnMarkFulfilled);
        bottomPanel.add(btnMarkCancelled);
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        // ==========================================
        // ACTIONS & LISTENERS
        // ==========================================

        btnBack.addActionListener(e -> dispose());

        btnAll.addActionListener(e -> sorter.setRowFilter(null));
        btnOpen.addActionListener(e -> sorter.setRowFilter(RowFilter.regexFilter("(?i)open", 4)));
        btnFulfilled.addActionListener(e -> sorter.setRowFilter(RowFilter.regexFilter("(?i)fulfilled", 4)));
        btnCancelled.addActionListener(e -> sorter.setRowFilter(RowFilter.regexFilter("(?i)cancelled", 4)));

        btnMarkFulfilled.addActionListener(e -> updateStatus("fulfilled"));
        btnMarkCancelled.addActionListener(e -> updateStatus("cancelled"));

        // NEW: Action for the Smart Matcher
        btnFindMatch.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            
            if (viewRow == -1) {
                JOptionPane.showMessageDialog(this, "⚠ Please select a request row first.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int modelRow = table.convertRowIndexToModel(viewRow);
            String skillNeeded = (String) tableModel.getValueAt(modelRow, 1); // Get text from "Skill Needed" column
            
            // Run the Engine!
            List<Skill> matches = RecommendationEngine.findMatchesForRequest(skillNeeded);
            
            if (matches.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No available skills match this request right now.", "Smart Match", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder result = new StringBuilder("We found community members who can help!\n\n");
                for (Skill s : matches) {
                    Person p = DatabaseManager.getPersonById(s.getUserId());
                    if (p != null) {
                        result.append("• ").append(p.getName())
                              .append(" | Phone: ").append(p.getPhone())
                              .append(" | Skill: ").append(s.getSkillName())
                              .append(" (Rs ").append(s.getPricePerHour()).append(")\n");
                    }
                }
                JOptionPane.showMessageDialog(this, result.toString(), "Smart Match Results", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void loadTableData() {
        tableModel.setRowCount(0); 
        List<ServiceRequest> requests = DatabaseManager.getAllServiceRequests();
        
        for (ServiceRequest req : requests) {
            Person poster = DatabaseManager.getPersonById(req.getPostedBy());
            String posterName = (poster != null) ? poster.getName() : "Unknown";
            
            tableModel.addRow(new Object[]{
                req.getRequestId(),
                req.getSkillNeeded(),
                posterName,
                req.getDescription(),
                req.getStatus()
            });
        }
    }

    private void updateStatus(String newStatus) {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) return; // Silent return, handled by other buttons
        
        int modelRow = table.convertRowIndexToModel(viewRow);
        int requestId = (int) tableModel.getValueAt(modelRow, 0); 

        boolean isUpdated = DatabaseManager.updateRequestStatus(requestId, newStatus);
        if (isUpdated) {
            JOptionPane.showMessageDialog(this, "✔ Status updated to " + newStatus);
            loadTableData(); 
        }
    }
}