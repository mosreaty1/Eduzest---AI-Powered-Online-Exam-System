import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class AdminFrame extends JFrame implements AdminOperations {

    public AdminFrame() {
        setTitle("Admin Dashboard");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLookAndFeel();

        // Buttons for managing exams and students
        JButton createExamButton = createButton("Create Exam");
        JButton editExamButton = createButton("Edit Exam");
        JButton deleteExamButton = createButton("Delete Exam");
        JButton manageStudentButton = createButton("Manage Students");
        JButton logoutButton = createButton("Logout");

        // Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = new JLabel("Admin: Manage Exams and Students");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Title takes up two columns
        panel.add(titleLabel, gbc);

        // Place buttons in grid
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Buttons span across both columns
        panel.add(createExamButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(editExamButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(deleteExamButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(manageStudentButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(logoutButton, gbc);

        // Button Action Listeners
        createExamButton.addActionListener((ActionEvent e) -> createExam());
        editExamButton.addActionListener((ActionEvent e) -> {
            String examName = JOptionPane.showInputDialog(this, "Enter the name of the exam to edit:");
            if (examName != null && !examName.trim().isEmpty()) {
                editExam(examName);
            }
        });
        deleteExamButton.addActionListener((ActionEvent e) -> {
            String examName = JOptionPane.showInputDialog(this, "Enter the name of the exam to delete:");
            if (examName != null && !examName.trim().isEmpty()) {
                deleteExam(examName);
            }
        });
        manageStudentButton.addActionListener((ActionEvent e) -> {
            showStudentManagementDialog();
        });
        logoutButton.addActionListener((ActionEvent e) -> logout());

        // Add panel to the frame
        add(panel);
        setVisible(true);
    }

    // Set system look and feel
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create a styled button
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180)); // Blue color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    // Dialog for student management (add, delete, update)
    private void showStudentManagementDialog() {
        String[] options = {"Add Student", "Delete Student", "Update Student", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this, "Select an operation:", "Student Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0: // Add Student
                addStudent();
                break;
            case 1: // Delete Student
                deleteStudent();
                break;
            case 2: // Update Student
                updateStudent();
                break;
            default:
                break;
        }
    }

    // Implementing AdminOperations interface methods
    @Override
    public void createExam() {
        new CreateExamFrame();
    }

    @Override
    public void editExam(String examName) {
        new ModifyExamFrame(examName);
    }

    @Override
    public void deleteExam(String examName) {
        File examFile = new File(examName + ".txt");
        if (examFile.exists()) {
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this exam?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                if (examFile.delete()) {
                    JOptionPane.showMessageDialog(this, "Exam deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error deleting exam.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Exam file not found.");
        }
    }

    @Override
    public void addStudent() {
        String studentName = JOptionPane.showInputDialog(this, "Enter student name:");
        if (studentName != null && !studentName.trim().isEmpty()) {
            File studentFile = new File(studentName + ".txt");
            try {
                if (studentFile.createNewFile()) {
                    JOptionPane.showMessageDialog(this, "Student added successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Student already exists.");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error adding student.");
            }
        }
    }

    @Override
    public void deleteStudent() {
        String studentName = JOptionPane.showInputDialog(this, "Enter the student name to delete:");
        if (studentName != null && !studentName.trim().isEmpty()) {
            File studentFile = new File(studentName + ".txt");
            if (studentFile.exists()) {
                int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    if (studentFile.delete()) {
                        JOptionPane.showMessageDialog(this, "Student deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error deleting student.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Student file not found.");
            }
        }
    }

    @Override
    public void updateStudent() {
        String studentName = JOptionPane.showInputDialog(this, "Enter the student name to update:");
        if (studentName != null && !studentName.trim().isEmpty()) {
            // Assuming you have a file for each student, you can ask for new details and update the file here
            // Example: Modify student details, we can extend this to include file editing.
            JOptionPane.showMessageDialog(this, "Student information updated (not implemented).");
        }
    }

    @Override
    public void logout() {
        dispose();
        new LoginFrame(); // Assuming there's a LoginFrame for login screen
    }


}
