import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;

public class ModifyExamFrame extends JFrame implements ExamModifier {
    private String examName;
    private ArrayList<String[]> questions;

    public ModifyExamFrame(String examName) {
        this.examName = examName;
        this.questions = loadExamQuestions(examName);

        setTitle("Modify Exam: " + examName);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLookAndFeel();

        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions available to modify!");
            dispose();
            return;
        }

        displayQuestions();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String[]> loadExamQuestions(String examName) {
        ArrayList<String[]> questions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(examName + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                questions.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public void displayQuestions() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        for (String[] questionData : questions) {
            String question = questionData[0];
            String optionA = questionData[1];
            String optionB = questionData[2];
            String optionC = questionData[3];
            String optionD = questionData[4];
            String correctAnswer = questionData[5];

            gbc.gridx = 0;
            gbc.gridy = row++;
            panel.add(new JLabel("Question: "), gbc);

            JTextField questionField = new JTextField(question);
            gbc.gridx = 1;
            panel.add(questionField, gbc);

            gbc.gridx = 0;
            gbc.gridy = row++;
            panel.add(new JLabel("Option A: "), gbc);

            JTextField optionAField = new JTextField(optionA);
            gbc.gridx = 1;
            panel.add(optionAField, gbc);

            gbc.gridx = 0;
            gbc.gridy = row++;
            panel.add(new JLabel("Option B: "), gbc);

            JTextField optionBField = new JTextField(optionB);
            gbc.gridx = 1;
            panel.add(optionBField, gbc);

            gbc.gridx = 0;
            gbc.gridy = row++;
            panel.add(new JLabel("Option C: "), gbc);

            JTextField optionCField = new JTextField(optionC);
            gbc.gridx = 1;
            panel.add(optionCField, gbc);

            gbc.gridx = 0;
            gbc.gridy = row++;
            panel.add(new JLabel("Option D: "), gbc);

            JTextField optionDField = new JTextField(optionD);
            gbc.gridx = 1;
            panel.add(optionDField, gbc);

            gbc.gridx = 0;
            gbc.gridy = row++;
            panel.add(new JLabel("Correct Answer: "), gbc);

            JTextField correctAnswerField = new JTextField(correctAnswer);
            gbc.gridx = 1;
            panel.add(correctAnswerField, gbc);
        }

        JButton saveButton = createButton("Save Exam");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);

        saveButton.addActionListener((ActionEvent e) -> {
            saveExam(examName, panel);
            JOptionPane.showMessageDialog(this, "Exam saved successfully!");
            dispose();
        });

        // Wrap the panel inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Ensure vertical scroll is always available
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Optional: disable horizontal scrolling

        add(scrollPane); // Add the scrollPane instead of the panel
        setVisible(true);
    }

    @Override
    public void saveExam(String examName) {

    }

    @Override
    public void saveExam(String examName, JPanel panel) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(examName + ".txt"))) {
            for (Component comp : panel.getComponents()) {
                if (comp instanceof JTextField) {
                    JTextField textField = (JTextField) comp;
                    writer.write(textField.getText() + ",");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JButton createButton(String text) {
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
