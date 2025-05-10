import javax.swing.*;

public interface GradeReport {
    String[][] formatGradesData(String gradesData);
    void printReport(JTable table);
    void displayGradeReport();
}
