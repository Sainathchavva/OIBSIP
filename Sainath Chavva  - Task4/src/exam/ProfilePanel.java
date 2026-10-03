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

public class ProfilePanel extends JPanel {

    private ExamFrame frame;

    private JLabel usernameLabel;

    private JTextField displayNameField;

    private JPasswordField passwordField;

    public ProfilePanel(ExamFrame frame) {

        this.frame = frame;

        setLayout(new BorderLayout());

        JLabel title = new JLabel(
                "STUDENT PROFILE",
                JLabel.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));

        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(40, 100, 40, 100)
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        usernameLabel = new JLabel();

        JLabel displayNameLabel =
                new JLabel("Display Name:");

        displayNameField =
                new JTextField(20);

        JLabel passwordLabel =
                new JLabel("New Password:");

        passwordField =
                new JPasswordField(20);

        JButton updateButton =
                new JButton("Update Profile");

        JButton startButton =
                new JButton("Start Examination");

        JButton logoutButton =
                new JButton("Logout");

        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;

        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(displayNameLabel, gbc);

        gbc.gridx = 1;

        formPanel.add(displayNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;

        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;

        formPanel.add(updateButton, gbc);

        gbc.gridx = 1;

        formPanel.add(startButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;

        formPanel.add(logoutButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        updateButton.addActionListener(e -> updateProfile());

        startButton.addActionListener(e -> startExam());

        logoutButton.addActionListener(e -> frame.showLogin());
    }

    public void setUserDetails(
            String username,
            String displayName,
            String password) {

        usernameLabel.setText(username);

        if (displayName == null || displayName.isEmpty()) {
            displayNameField.setText(username);
        } else {
            displayNameField.setText(displayName);
        }

        if (password == null || password.isEmpty()) {
            passwordField.setText("12345");
        } else {
            passwordField.setText(password);
        }
    }

    private void updateProfile() {

        String name =
                displayNameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Display name cannot be empty."
            );

            return;
        }

        if (password.length() < 5) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 5 characters."
            );

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Profile updated successfully."
        );
    }

    private void startExam() {

        String name =
                displayNameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a display name."
            );

            return;
        }

        if (password.length() < 5) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 5 characters."
            );

            return;
        }

        frame.startExam(name, password);
    }
}