import javax.swing.*;
import java.util.ArrayList;

public interface ExamModifier {
    ArrayList<String[]> loadExamQuestions(String examName);

    void displayQuestions();

    void saveExam(String examName);

    void saveExam(String examName, JPanel panel);

    JButton createButton(String text);
}
