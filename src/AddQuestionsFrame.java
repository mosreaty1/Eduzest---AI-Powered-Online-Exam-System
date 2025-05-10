import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AddQuestionsFrame extends JFrame implements QuestionManagement {
    private String examName;
    private int numQuestions;
    private int questionsAdded = 0;

    public AddQuestionsFrame(String examName, int numQuestions) {
        this.examName = examName;
        this.numQuestions = numQuestions;

        setTitle("Add Questions to " + examName);
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLookAndFeel();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField questionField = new JTextField(20);
        JTextField optionAField = new JTextField(10);
        JTextField optionBField = new JTextField(10);
        JTextField optionCField = new JTextField(10);
        JTextField optionDField = new JTextField(10);
        JTextField correctAnswerField = new JTextField(10);

        JLabel questionLabel = new JLabel("Question:");
        JLabel optionsLabel = new JLabel("Options: (A, B, C, D)");
        JLabel correctAnswerLabel = new JLabel("Correct Answer:");

        JButton addButton = createButton("Add Question");
        JButton finishButton = createButton("Finish");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(questionLabel, gbc);
        gbc.gridx = 1;
        panel.add(questionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(optionsLabel, gbc);
        gbc.gridx = 1;
        panel.add(optionAField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(optionBField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(optionCField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(optionDField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(correctAnswerLabel, gbc);
        gbc.gridx = 1;
        panel.add(correctAnswerField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(finishButton, gbc);

        addButton.addActionListener((ActionEvent e) -> {
            if (questionField.getText().isEmpty() || optionAField.getText().isEmpty() || optionBField.getText().isEmpty() || optionCField.getText().isEmpty() || optionDField.getText().isEmpty() || correctAnswerField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                addQuestion(questionField.getText(), optionAField.getText(), optionBField.getText(), optionCField.getText(), optionDField.getText(), correctAnswerField.getText());
                questionsAdded++;

                questionField.setText("");
                optionAField.setText("");
                optionBField.setText("");
                optionCField.setText("");
                optionDField.setText("");
                correctAnswerField.setText("");

                if (questionsAdded >= numQuestions) {
                    addButton.setEnabled(false);
                    JOptionPane.showMessageDialog(this, "All questions added!");
                }
            }
        });

        finishButton.addActionListener((ActionEvent e) -> {
            finishExam();
        });

        add(panel);
        setVisible(true);
    }

    @Override
    public void addQuestion(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(examName + ".txt", true))) {
            writer.write(question + "," + optionA + "," + optionB + "," + optionC + "," + optionD + "," + correctAnswer + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finishExam() {
        JOptionPane.showMessageDialog(this, "Exam Creation Finished!");
        dispose();
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

}
