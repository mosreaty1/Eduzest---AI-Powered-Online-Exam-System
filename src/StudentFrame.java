import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StudentFrame extends JFrame {

    public StudentFrame(String username) {
        setTitle("Student Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLookAndFeel();

        // Main content panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton viewExamsButton = createButton("View and Start Exams");
        JButton viewGradesButton = createButton("View My Grades");
        JButton logoutButton = createButton("Logout");
        JButton viewGradeReportButton = createButton("View Grade Report");
        JButton feedbackButton = createButton("Your Feedback");

        // Add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        panel.add(viewExamsButton, gbc);

        gbc.gridy = 2;
        panel.add(viewGradesButton, gbc);

        gbc.gridy = 3;
        panel.add(viewGradeReportButton, gbc);

        gbc.gridy = 4;
        panel.add(logoutButton, gbc);

        gbc.gridy = 5;
        panel.add(feedbackButton, gbc);

        // Wrap the panel in a JScrollPane to enable scrolling
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        // Button actions
        viewExamsButton.addActionListener((ActionEvent e) -> new StartExamFrame(username));

        viewGradesButton.addActionListener((ActionEvent e) -> {
            String grades = Utility.loadPreviousGrades(username);
            if (grades.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No grades available.");
            } else {
                JOptionPane.showMessageDialog(this, "Your grades:\n" + grades);
            }
        });

        viewGradeReportButton.addActionListener((ActionEvent e) -> new StudentGradeReportFrame(username));

        logoutButton.addActionListener((ActionEvent e) -> {
            dispose();
            new LoginFrame();
        });

        feedbackButton.addActionListener((ActionEvent e) -> openFeedbackDialog(username));

        setVisible(true);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    private void openFeedbackDialog(String username) {
        JDialog feedbackDialog = new JDialog(this, "Your Feedback", true);
        feedbackDialog.setSize(400, 300);
        feedbackDialog.setLocationRelativeTo(this);
        feedbackDialog.setLayout(new BorderLayout());

        // Rating selection (1-5 stars)
        JPanel ratingPanel = new JPanel();
        ratingPanel.setLayout(new GridLayout(1, 5));
        ButtonGroup ratingGroup = new ButtonGroup();
        JRadioButton[] ratingButtons = new JRadioButton[5];
        for (int i = 0; i < 5; i++) {
            ratingButtons[i] = new JRadioButton(String.valueOf(i + 1));
            ratingGroup.add(ratingButtons[i]);
            ratingPanel.add(ratingButtons[i]);
        }

        // Comment area
        JTextArea commentArea = new JTextArea(4, 30);
        JScrollPane scrollPane = new JScrollPane(commentArea);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener((ActionEvent e) -> {
            String rating = getSelectedRating(ratingButtons);
            String comment = commentArea.getText();
            if (rating != null && !comment.isEmpty()) {
                saveFeedback(username, rating, comment);
                JOptionPane.showMessageDialog(feedbackDialog, "Feedback submitted successfully!");
                feedbackDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(feedbackDialog, "Please provide a rating and comment.");
            }
        });

        // Wrap the components in a JScrollPane to make the dialog scrollable
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(ratingPanel);
        contentPanel.add(scrollPane);
        contentPanel.add(submitButton);

        JScrollPane scrollablePanel = new JScrollPane(contentPanel);
        feedbackDialog.add(scrollablePanel, BorderLayout.CENTER);

        feedbackDialog.setVisible(true);
    }

    private String getSelectedRating(JRadioButton[] ratingButtons) {
        for (int i = 0; i < 5; i++) {
            if (ratingButtons[i].isSelected()) {
                return String.valueOf(i + 1);
            }
        }
        return null;
    }

    private void saveFeedback(String username, String rating, String comment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {
            writer.write("Username: " + username);
            writer.newLine();
            writer.write("Rating: " + rating + " stars");
            writer.newLine();
            writer.write("Comment: " + comment);
            writer.newLine();
            writer.write("-------------------------------------------------");
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving feedback.");
        }
    }


}
