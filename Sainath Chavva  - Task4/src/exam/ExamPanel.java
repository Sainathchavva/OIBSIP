package exam;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class ExamPanel extends JPanel {

    private ExamFrame frame;

    private List<Question> questions;

    private int[] selectedAnswers;

    private int currentQuestion;

    private int remainingSeconds;

    private long examStartTime;

    private Timer timer;

    private JLabel timerLabel;
    private JLabel questionNumberLabel;
    private JLabel questionLabel;

    private JRadioButton[] optionButtons;

    private ButtonGroup optionGroup;

    private JButton previousButton;
    private JButton nextButton;
    private JButton submitButton;

    public ExamPanel(ExamFrame frame) {

        this.frame = frame;

        setLayout(new BorderLayout(15, 15));

        setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );

        JPanel topPanel = new JPanel(new BorderLayout());

        questionNumberLabel =
                new JLabel("Question 1");

        questionNumberLabel.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        timerLabel =
                new JLabel("Time Left: 30:00");

        timerLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        timerLabel.setHorizontalAlignment(
                SwingConstants.RIGHT
        );

        topPanel.add(
                questionNumberLabel,
                BorderLayout.WEST
        );

        topPanel.add(
                timerLabel,
                BorderLayout.EAST
        );

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel =
                new JPanel(new BorderLayout(10, 20));

        questionLabel =
                new JLabel();

        questionLabel.setFont(
                new Font("Arial", Font.BOLD, 19)
        );

        centerPanel.add(
                questionLabel,
                BorderLayout.NORTH
        );

        JPanel optionsPanel =
                new JPanel(new GridLayout(4, 1, 5, 10));

        optionButtons =
                new JRadioButton[4];

        optionGroup =
                new ButtonGroup();

        for (int i = 0; i < 4; i++) {

            optionButtons[i] =
                    new JRadioButton();

            optionButtons[i].setFont(
                    new Font("Arial", Font.PLAIN, 16)
            );

            optionGroup.add(
                    optionButtons[i]
            );

            optionsPanel.add(
                    optionButtons[i]
            );
        }

        centerPanel.add(
                optionsPanel,
                BorderLayout.CENTER
        );

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel =
                new JPanel(new GridLayout(1, 3, 10, 10));

        previousButton =
                new JButton("Previous");

        nextButton =
                new JButton("Next");

        submitButton =
                new JButton("Submit Exam");

        bottomPanel.add(previousButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(submitButton);

        add(bottomPanel, BorderLayout.SOUTH);

        previousButton.addActionListener(
                e -> previousQuestion()
        );

        nextButton.addActionListener(
                e -> nextQuestion()
        );

        submitButton.addActionListener(
                e -> confirmSubmit()
        );
    }

    public void startNewExam() {

        questions = ExamData.getQuestions();

        selectedAnswers =
                new int[questions.size()];

        for (int i = 0; i < selectedAnswers.length; i++) {
            selectedAnswers[i] = -1;
        }

        currentQuestion = 0;

        remainingSeconds = 30 * 60;

        examStartTime =
                System.currentTimeMillis();

        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(
                1000,
                e -> updateTimer()
        );

        timer.start();

        showQuestion();

        updateTimerLabel();
    }

    private void updateTimer() {

        remainingSeconds--;

        updateTimerLabel();

        if (remainingSeconds <= 0) {

            timer.stop();

            JOptionPane.showMessageDialog(
                    this,
                    "Time is over. Your examination will be submitted automatically.",
                    "Time Up",
                    JOptionPane.INFORMATION_MESSAGE
            );

            submitExam();
        }
    }

    private void updateTimerLabel() {

        int minutes =
                remainingSeconds / 60;

        int seconds =
                remainingSeconds % 60;

        timerLabel.setText(
                String.format(
                        "Time Left: %02d:%02d",
                        minutes,
                        seconds
                )
        );
    }

    private void showQuestion() {

        Question question =
                questions.get(currentQuestion);

        questionNumberLabel.setText(
                "Question "
                        + (currentQuestion + 1)
                        + " of "
                        + questions.size()
        );

        questionLabel.setText(
                "<html>"
                        + (currentQuestion + 1)
                        + ". "
                        + question.getQuestionText()
                        + "</html>"
        );

        String[] options =
                question.getOptions();

        optionGroup.clearSelection();

        for (int i = 0; i < 4; i++) {

            optionButtons[i].setText(
                    (char) ('A' + i)
                            + ". "
                            + options[i]
            );
        }

        int previousAnswer =
                selectedAnswers[currentQuestion];

        if (previousAnswer >= 0) {

            optionButtons[previousAnswer]
                    .setSelected(true);
        }

        previousButton.setEnabled(
                currentQuestion > 0
        );

        nextButton.setEnabled(
                currentQuestion < questions.size() - 1
        );
    }

    private void saveCurrentAnswer() {

        for (int i = 0; i < 4; i++) {

            if (optionButtons[i].isSelected()) {

                selectedAnswers[currentQuestion] = i;

                return;
            }
        }

        selectedAnswers[currentQuestion] = -1;
    }

    private void nextQuestion() {

        saveCurrentAnswer();

        if (currentQuestion
                < questions.size() - 1) {

            currentQuestion++;

            showQuestion();
        }
    }

    private void previousQuestion() {

        saveCurrentAnswer();

        if (currentQuestion > 0) {

            currentQuestion--;

            showQuestion();
        }
    }

    private void confirmSubmit() {

        saveCurrentAnswer();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to submit the exam?",
                        "Confirm Submission",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice == JOptionPane.YES_OPTION) {

            submitExam();
        }
    }

    private void submitExam() {

        if (timer != null) {
            timer.stop();
        }

        saveCurrentAnswer();

        int score = 0;

        for (int i = 0; i < questions.size(); i++) {

            if (selectedAnswers[i]
                    == questions.get(i).getCorrectAnswer()) {

                score++;
            }
        }

        long timeTaken =
                (System.currentTimeMillis()
                        - examStartTime) / 1000;

        frame.showResult(
                score,
                questions.size(),
                timeTaken,
                questions,
                selectedAnswers
        );
    }
}