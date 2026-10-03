package exam;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ExamFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private LoginPanel loginPanel;
    private ProfilePanel profilePanel;
    private ExamPanel examPanel;
    private ResultPanel resultPanel;

    private String username;
    private String displayName;
    private String password;

    private boolean examRunning = false;

    public static final String LOGIN = "LOGIN";
    public static final String PROFILE = "PROFILE";
    public static final String EXAM = "EXAM";
    public static final String RESULT = "RESULT";

    public ExamFrame() {

        setTitle("Online Examination System");
        setSize(850, 600);
        setMinimumSize(new Dimension(750, 500));

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(this);
        profilePanel = new ProfilePanel(this);
        examPanel = new ExamPanel(this);
        resultPanel = new ResultPanel(this);

        mainPanel.add(loginPanel, LOGIN);
        mainPanel.add(profilePanel, PROFILE);
        mainPanel.add(examPanel, EXAM);
        mainPanel.add(resultPanel, RESULT);

        add(mainPanel);

        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                if (examRunning) {

                    int choice = JOptionPane.showConfirmDialog(
                            ExamFrame.this,
                            "Are you sure you want to quit?",
                            "Quit Examination",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        examRunning = false;
                        dispose();
                    }

                } else {
                    dispose();
                }
            }
        });

        setVisible(true);
    }

    public void showLogin() {

        examRunning = false;

        loginPanel.clearFields();

        cardLayout.show(mainPanel, LOGIN);
    }

    public void showProfile(String username) {

        this.username = username;

        profilePanel.setUserDetails(username, displayName, password);

        cardLayout.show(mainPanel, PROFILE);
    }

    public void startExam(String displayName, String password) {

        this.displayName = displayName;
        this.password = password;

        examRunning = true;

        examPanel.startNewExam();

        cardLayout.show(mainPanel, EXAM);
    }

    public void showResult(int score, int total, long timeTaken,
                           java.util.List<Question> questions,
                           int[] selectedAnswers) {

        examRunning = false;

        resultPanel.displayResult(
                score,
                total,
                timeTaken,
                questions,
                selectedAnswers
        );

        cardLayout.show(mainPanel, RESULT);
    }

    public void returnToProfile() {

        profilePanel.setUserDetails(username, displayName, password);

        cardLayout.show(mainPanel, PROFILE);
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }
}