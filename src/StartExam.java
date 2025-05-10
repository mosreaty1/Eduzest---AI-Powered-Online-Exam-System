import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StartExam extends JFrame implements ExamActions {
    private ArrayList<String[]> questions = new ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;

    public StartExam(String examName) {
        setTitle("Start Exam: " + examName);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLookAndFeel();

        loadQuestions(examName);

        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions available for this exam!");
            dispose();
            return;
        }

        displayQuestion();
    }

    @Override
    public void loadQuestions(String examName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(examName + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                questions.add(line.split(","));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void displayQuestion() {
        if (currentIndex >= questions.size()) {
            JOptionPane.showMessageDialog(this, "Exam finished! Your score is: " + score);
            dispose();
            return;
        }

        String[] questionData = questions.get(currentIndex);
        String question = questionData[0];
        String optionA = questionData[1];
        String optionB = questionData[2];
        String optionC = questionData[3];
        String optionD = questionData[4];
        String correctAnswer = questionData[5];

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel questionLabel = new JLabel(question);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        questionLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(questionLabel, gbc);

        JRadioButton optionAButton = new JRadioButton(optionA);
        JRadioButton optionBButton = new JRadioButton(optionB);
        JRadioButton optionCButton = new JRadioButton(optionC);
        JRadioButton optionDButton = new JRadioButton(optionD);

        ButtonGroup group = new ButtonGroup();
        group.add(optionAButton);
        group.add(optionBButton);
        group.add(optionCButton);
        group.add(optionDButton);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(optionAButton, gbc);
        gbc.gridy = 2;
        panel.add(optionBButton, gbc);
        gbc.gridy = 3;
        panel.add(optionCButton, gbc);
        gbc.gridy = 4;
        panel.add(optionDButton, gbc);

        JButton nextButton = createButton("Next");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(nextButton, gbc);

        nextButton.addActionListener((ActionEvent e) -> nextQuestion(optionAButton, optionBButton,
                optionCButton, optionDButton, correctAnswer));

        add(panel);
        setVisible(true);
    }

    @Override
    public void nextQuestion(JRadioButton optionAButton, JRadioButton optionBButton,
                             JRadioButton optionCButton, JRadioButton optionDButton,
                             String correctAnswer) {
        if ((optionAButton.isSelected() && correctAnswer.equals("A")) ||
                (optionBButton.isSelected() && correctAnswer.equals("B")) ||
                (optionCButton.isSelected() && correctAnswer.equals("C")) ||
                (optionDButton.isSelected() && correctAnswer.equals("D"))) {
            score++;
        }
        currentIndex++;
        getContentPane().removeAll();
        displayQuestion();
    }

    @Override
    public void setLookAndFeel() {
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
