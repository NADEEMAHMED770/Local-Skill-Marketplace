import javax.swing.*;
import java.awt.*;

public class ExportReportScreen extends JFrame {

    public ExportReportScreen() {
        setTitle("Export Community Report");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Top Title
        JLabel lblTitle = new JLabel("Export Database to Text File", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // 2. Center Instructions / Preview Area
        JTextArea txtPreview = new JTextArea();
        txtPreview.setEditable(false);
        txtPreview.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtPreview.setText("Click 'Generate & Save Report' below to create a plain text (.txt) file containing:\n\n" +
                           "  • Total user and skill counts\n" +
                           "  • A complete directory of all available skills\n" +
                           "  • A list of all currently open service requests\n\n" +
                           "The file will be saved securely in the 'reports/' folder of this project.");
        
        txtPreview.setMargin(new Insets(15, 15, 15, 15));
        txtPreview.setBackground(new Color(240, 240, 240));
        add(new JScrollPane(txtPreview), BorderLayout.CENTER);

        // 3. Bottom Panel: Button & Status Label
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        JLabel lblStatus = new JLabel(" "); // Hidden status text
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 12));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnGenerate = new JButton("▶ Generate & Save Report");
        JButton btnBack = new JButton("Back to Dashboard");
        
        buttonPanel.add(btnGenerate);
        buttonPanel.add(btnBack);

        bottomPanel.add(lblStatus);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);

        // ==========================================
        // BUTTON ACTIONS
        // ==========================================

        btnBack.addActionListener(e -> dispose());

        btnGenerate.addActionListener(e -> {
            lblStatus.setText("Generating...");
            lblStatus.setForeground(Color.BLACK);

            // Call our FileExporter class
            String savedFilePath = FileExporter.exportReport();

            if (savedFilePath != null) {
                // Success
                lblStatus.setText("✔ Report saved successfully! 📁 Location: " + savedFilePath);
                lblStatus.setForeground(new Color(34, 139, 34)); // Green
            } else {
                // IOException Failure
                lblStatus.setText("✖ Error: Could not save file — IOException caught");
                lblStatus.setForeground(Color.RED);
            }
        });
    }
}