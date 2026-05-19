import javax.swing.*;
import java.awt.*;

// Inherits from JFrame as required by OOP Class Design
public class SkillMarketplaceApp extends JFrame {

    public SkillMarketplaceApp(String loggedInUser) {
        // 1. Setup the Main Window
        setTitle("Local Skill Marketplace");
        setSize(800, 600); // 800x600 pixels as per GUI Layout Description
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window on screen
        setLayout(new BorderLayout()); // Main layout

        // 2. Create the Top Header Section
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185)); // Calm blue color
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel titleLabel = new JLabel("Local Skill Marketplace");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Connecting skills with community needs");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(subtitleLabel);

        // 3. Create the 2x3 Grid of Navigation Buttons
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(2, 3, 20, 20)); // 2 rows, 3 cols, 20px gaps
        gridPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JButton btnRegisterMember = new JButton("Register Member");
        // Find this line:
        // JButton btnRegisterMember = new JButton("Register Member");
        // ... other buttons ...

        // Add this action listener:
        btnRegisterMember.addActionListener(e -> {
            RegisterMemberScreen registerScreen = new RegisterMemberScreen();
            registerScreen.setVisible(true);
        });
        JButton btnAddSkill = new JButton("Add Skill");
        // Find this line:
        // JButton btnAddSkill = new JButton("Add Skill");

        // Add this action listener:
        btnAddSkill.addActionListener(e -> {
            AddSkillScreen skillScreen = new AddSkillScreen();
            skillScreen.setVisible(true);
        });
        JButton btnBrowseSkills = new JButton("Browse Skills");
        // Find this line:
        // JButton btnBrowseSkills = new JButton("Browse Skills");

        // Add this action listener:
        btnBrowseSkills.addActionListener(e -> {
            BrowseSkillsScreen browseScreen = new BrowseSkillsScreen();
            browseScreen.setVisible(true);
        });
        JButton btnPostRequest = new JButton("Post Request");

        // Find this line:
        // JButton btnPostRequest = new JButton("Post Request");

        // Add this action listener right below it:
        btnPostRequest.addActionListener(e -> {
            PostRequestScreen requestScreen = new PostRequestScreen();
            requestScreen.setVisible(true);
        });


        JButton btnViewRequests = new JButton("View Requests");

        // Find this line:
        // JButton btnViewRequests = new JButton("View Requests");

        // Add this action listener right below it:
        btnViewRequests.addActionListener(e -> {
            ViewRequestsScreen viewScreen = new ViewRequestsScreen();
            viewScreen.setVisible(true);
        });

        JButton btnExportReport = new JButton("Export Report");

        // Find this line:
        // JButton btnExportReport = new JButton("Export Report");

        // Add this action listener right below it:
        btnExportReport.addActionListener(e -> {
            ExportReportScreen exportScreen = new ExportReportScreen();
            exportScreen.setVisible(true);
        });

        // Make buttons look a bit nicer
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        JButton[] buttons = {btnRegisterMember, btnAddSkill, btnBrowseSkills, 
                             btnPostRequest, btnViewRequests, btnExportReport};
        for (JButton btn : buttons) {
            btn.setFont(buttonFont);
            btn.setFocusPainted(false);
            gridPanel.add(btn);
        }

        // 4. Create the Bottom Status Bar
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel statusLabel = new JLabel("Status: System Ready | Logged in: "+ loggedInUser);
        statusPanel.add(statusLabel);

        // 5. Add everything to the main window
        add(headerPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }
}