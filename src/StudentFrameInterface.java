import javax.swing.*;

public interface StudentFrameInterface {
    void setLookAndFeel();
    JButton createButton(String text);
    void openFeedbackDialog(String username);
    String getSelectedRating(JRadioButton[] ratingButtons);
    void saveFeedback(String username, String rating, String comment);
    void displayGrades(String username);
    void startExam(String username);
}
