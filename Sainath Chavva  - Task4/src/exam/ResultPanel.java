package exam;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class ResultPanel extends JPanel {

    private ExamFrame frame;

    private JLabel scoreLabel;
    private JLabel timeLabel;

    private JTextArea breakdownArea;

    public ResultPanel(ExamFrame frame) {

        this.frame = frame;

        setLayout(new BorderLayout(15, 15));

        setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );

        JLabel title =
                new JLabel(
                        "EXAMINATION RESULT",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        add(title, BorderLayout.NORTH);

        JPanel summaryPanel =
                new JPanel(new GridLayout(2, 1));

        scoreLabel =
                new JLabel(
                        "Score: 0 / 0",
                        SwingConstants.CENTER
                );

        scoreLabel.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        timeLabel =
                new JLabel(
                        "Time Taken: 00:00",
                        SwingConstants.CENTER
                );

        timeLabel.setFont(
                new Font("Arial", Font.PLAIN, 17)
        );

        summaryPanel.add(scoreLabel);
        summaryPanel.add(timeLabel);

        JPanel centerPanel =
                new JPanel(new BorderLayout(10, 10));

        centerPanel.add(
                summaryPanel,
                BorderLayout.NORTH
        );

        breakdownArea =
                new JTextArea();

        breakdownArea.setEditable(false);

        breakdownArea.setFont(
                new Font("Monospaced", Font.PLAIN, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(breakdownArea);

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(
                centerPanel,
                BorderLayout.CENTER
        );

        JButton logoutButton =
                new JButton("Logout");

        logoutButton.addActionListener(
                e -> frame.showLogin()
        );

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.add(logoutButton);

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );
    }

    public void displayResult(
            int score,
            int total,
            long timeTaken,
            List<Question> questions,
            int[] selectedAnswers) {

        int incorrect =
                total - score;

        scoreLabel.setText(
                "Score: "
                        + score
                        + " out of "
                        + total
                        + " | Correct: "
                        + score
                        + " | Incorrect: "
                        + incorrect
        );

        long minutes =
                timeTaken / 60;

        long seconds =
                timeTaken % 60;

        timeLabel.setText(
                String.format(
                        "Time Taken: %02d:%02d",
                        minutes,
                        seconds
                )
        );

        StringBuilder result =
                new StringBuilder();

        result.append(
                "ANSWER BREAKDOWN\n"
        );

        result.append(
                "==============================\n\n"
        );

        for (int i = 0; i < questions.size(); i++) {

            Question question =
                    questions.get(i);

            int selected =
                    selectedAnswers[i];

            int correct =
                    question.getCorrectAnswer();

            result.append(
                    "Question "
                            + (i + 1)
                            + ": "
            );

            if (selected == correct) {

                result.append("CORRECT\n");

            } else {

                result.append("INCORRECT\n");
            }

            result.append(
                    "Your Answer: "
            );

            if (selected == -1) {

                result.append(
                        "Not Answered\n"
                );

            } else {

                result.append(
                        question.getOptions()[selected]
                                + "\n"
                );
            }

            result.append(
                    "Correct Answer: "
                            + question.getOptions()[correct]
                            + "\n"
            );

            result.append(
                    "------------------------------\n"
            );
        }

        breakdownArea.setText(
                result.toString()
        );
    }
}