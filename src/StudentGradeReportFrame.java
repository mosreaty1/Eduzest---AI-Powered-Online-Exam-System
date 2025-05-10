import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;

public class StudentGradeReportFrame extends JFrame implements GradeReport {

    public StudentGradeReportFrame(String username) {
        setTitle("Student Grade Report");
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

        JButton printButton = new JButton("Print Report");
        printButton.addActionListener((ActionEvent e) -> printReport(table));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Grade Report for " + username, JLabel.CENTER), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(printButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        // Call the display method from the interface
        displayGradeReport();
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
    public void printReport(JTable table) {
        try {
            boolean printed = table.print();
            if (printed) {
                JOptionPane.showMessageDialog(this, "Report printed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Report printing failed.");
            }
        } catch (PrinterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error printing report.");
        }
    }

    @Override
    public void displayGradeReport() {
        // You could implement additional behavior here, like logging or setting flags
        System.out.println("Displaying grade report.");
    }


}
