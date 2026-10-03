package exam;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {

    private ExamFrame frame;

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPanel(ExamFrame frame) {

        this.frame = frame;

        setLayout(new BorderLayout());

        JLabel title = new JLabel(
                "ONLINE EXAMINATION SYSTEM",
                JLabel.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 26));

        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(50, 100, 50, 100)
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");

        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");

        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;

        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;

        formPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;

        formPanel.add(loginButton, gbc);

        JLabel demoLabel = new JLabel(
                "Demo Login: student / 12345",
                JLabel.CENTER
        );

        add(formPanel, BorderLayout.CENTER);

        add(demoLabel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> login());
    }

    private void login() {

        String username = usernameField.getText().trim();

        String password = new String(
                passwordField.getPassword()
        );

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (username.equals("student")
                && password.equals("12345")) {

            frame.showProfile(username);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void clearFields() {

        usernameField.setText("");
        passwordField.setText("");
    }
}