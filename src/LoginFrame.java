import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame implements LoginFrameInterface {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public LoginFrame() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLookAndFeel();

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Welcome to Eduzest");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        styleTextField(usernameField);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        styleTextField(passwordField);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(roleLabel, gbc);

        roleComboBox = new JComboBox<>(new String[]{"Student", "Admin"});
        styleComboBox(roleComboBox);
        gbc.gridx = 1;
        panel.add(roleComboBox, gbc);

        JButton loginButton = createButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(loginButton, gbc);

        JButton registerButton = createButton("Register");
        gbc.gridx = 1;
        panel.add(registerButton, gbc);

        loginButton.addActionListener((ActionEvent e) -> {
            String username = getUsername();
            String password = getPassword();
            String role = getRole();

            if (Utility.authenticateUser(username, password, role)) {
                showLoginSuccess(username, role);
            } else {
                showLoginFailure("Invalid credentials");
            }
        });

        registerButton.addActionListener((ActionEvent e) -> {
            navigateToRegistration();
        });

        add(panel);
        setVisible(true);
    }

    @Override
    public void showLoginSuccess(String username, String role) {
        JOptionPane.showMessageDialog(this, "Login Successful");
        dispose();
        if (role.equals("Admin")) {
            new AdminFrame();
        } else {
            new StudentFrame(username);
        }
    }

    @Override
    public void showLoginFailure(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    @Override
    public String getUsername() {
        return usernameField.getText();
    }

    @Override
    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    @Override
    public String getRole() {
        return (String) roleComboBox.getSelectedItem();
    }

    @Override
    public void navigateToRegistration() {
        new RegistrationFrame();
        dispose();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void styleTextField(JTextField textField) {
        textField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(200, 30));
    }

    private void styleTextField(JPasswordField passwordField) {
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setPreferredSize(new Dimension(200, 30));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
