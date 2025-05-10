import javax.swing.*;

public interface ExamActions {
    void loadQuestions(String examName);
    void displayQuestion();
    void nextQuestion(JRadioButton optionAButton, JRadioButton optionBButton,
                      JRadioButton optionCButton, JRadioButton optionDButton,
                      String correctAnswer);
    void setLookAndFeel();
}
