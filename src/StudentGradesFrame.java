import javax.swing.*;
import java.awt.*;

public class StudentGradesFrame extends JFrame implements GradesReport {

    public StudentGradesFrame(String username) {
        setTitle("Student's Exam Grades");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLookAndFeel();

        String gradesData = Utility.loadPreviousGrades(username);

        if (gradesData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No grades available!");
            dispose();
            return;
        }

        String[] columns = {"Exam Name", "Grade"};
        String[][] data = formatGradesData(gradesData);

        JTable table = new JTable(data, columns);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Exam Grades for " + username, JLabel.CENTER), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        setVisible(true);

        // Call the method from the interface
        displayGradesReport();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[][] formatGradesData(String gradesData) {
        String[] lines = gradesData.split("\n");
        String[][] data = new String[lines.length][2];

        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split(", ");
            data[i][0] = parts[0];  // Exam name
            data[i][1] = parts[1];  // Grade
        }
        return data;
    }

    @Override
    public void displayGradesReport() {
        // Additional logic can be added for behavior like logging or reporting
        System.out.println("Displaying student's exam grades report.");
    }


}
