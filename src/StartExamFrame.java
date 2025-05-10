import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

public class StartExamFrame extends JFrame {
    private ArrayList<String> availableExams = new ArrayList<>();
    private String username;

    public StartExamFrame(String username) {
        this.username = username;
        setTitle("Start Exam");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLookAndFeel();

        loadAvailableExams();

        if (availableExams.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No exams available!");
            dispose();
            return;
        }

        JComboBox<String> examComboBox = new JComboBox<>(availableExams.toArray(new String[0]));
        examComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        styleComboBox(examComboBox);

        JButton startExamButton = createButton("Start Exam");

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Select an Exam to Start");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(examComboBox, gbc);

        gbc.gridx = 1;
        panel.add(startExamButton, gbc);

        startExamButton.addActionListener((ActionEvent e) -> {
            String selectedExam = (String) examComboBox.getSelectedItem();
            if (selectedExam != null) {
                new StartExam(selectedExam);
                dispose();
            }
        });

        add(panel);
        setVisible(true);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        comboBox.setPreferredSize(new Dimension(200, 30));
        comboBox.setBackground(Color.WHITE);
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

    private void loadAvailableExams() {
        File examDirectory = new File("./");
        File[] files = examDirectory.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files != null) {
            for (File file : files) {
                availableExams.add(file.getName().replace(".txt", ""));
            }
        }
    }

}