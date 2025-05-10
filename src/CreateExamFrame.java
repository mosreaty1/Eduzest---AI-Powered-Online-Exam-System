import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreateExamFrame extends JFrame implements ExamManagement {

    public CreateExamFrame() {
        setTitle("Create Exam");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel examNameLabel = new JLabel("Exam Name:");
        JTextField examNameField = new JTextField();
        examNameField.setToolTipText("Enter the name of the exam");
        styleTextField(examNameField);

        JLabel numQuestionsLabel = new JLabel("Number of Questions:");
        JTextField numQuestionsField = new JTextField();
        numQuestionsField.setToolTipText("Enter the total number of questions");
        styleTextField(numQuestionsField);

        JButton createButton = createButton("Create", "path/to/icon.png");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(examNameLabel, gbc);
        gbc.gridx = 1;
        panel.add(examNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(numQuestionsLabel, gbc);
        gbc.gridx = 1;
        panel.add(numQuestionsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(createButton, gbc);

        add(panel);

        createButton.addActionListener((ActionEvent e) -> {
            String examName = examNameField.getText().trim();
            try {
                int numQuestions = Integer.parseInt(numQuestionsField.getText().trim());
                if (examName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Exam name cannot be empty!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                createExam(examName, numQuestions); // Calling the createExam method from the interface
                new AddQuestionsFrame(examName, numQuestions);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number of questions!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    @Override
    public void createExam(String examName, int numQuestions) {
        // You can save the exam data or perform any other necessary actions here
        System.out.println("Exam Created: " + examName + " with " + numQuestions + " questions.");
    }

    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);

        if (iconPath != null) {
            button.setIcon(new ImageIcon(iconPath));
        }

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 150, 200));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });

        return button;
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        textField.setPreferredSize(new Dimension(200, 30));
    }


}
