import javax.swing.*;

public interface IRegistrationFrame {
    void setLookAndFeel();  // Sets the look and feel of the UI
    void styleTextField(JTextField textField);  // Styles a JTextField
    void styleTextField(JPasswordField passwordField);  // Styles a JPasswordField
    void styleComboBox(JComboBox<String> comboBox);  // Styles a JComboBox
    JButton createButton(String text);  // Creates a styled button
    void handleRegisterButtonClick(String username, String password, String confirmPassword, String role);  // Handles the registration logic
}
