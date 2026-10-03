package exam;

import java.util.ArrayList;
import java.util.List;

public class ExamData {

    public static List<Question> getQuestions() {

        List<Question> questions = new ArrayList<>();

        questions.add(new Question(
                "Which keyword is used to inherit a class in Java?",
                new String[]{"implements", "extends", "inherits", "super"},
                1
        ));

        questions.add(new Question(
                "Which method is the starting point of a Java application?",
                new String[]{"start()", "run()", "main()", "execute()"},
                2
        ));

        questions.add(new Question(
                "Which collection does not allow duplicate elements?",
                new String[]{"List", "ArrayList", "Set", "Vector"},
                2
        ));

        questions.add(new Question(
                "Which package contains Swing components?",
                new String[]{"java.io", "javax.swing", "java.sql", "java.net"},
                1
        ));

        questions.add(new Question(
                "Which keyword is used to create an object?",
                new String[]{"class", "new", "object", "create"},
                1
        ));

        questions.add(new Question(
                "Which data type is used to store true or false?",
                new String[]{"boolean", "bool", "logical", "bit"},
                0
        ));

        questions.add(new Question(
                "Which concept allows the same method name with different parameters?",
                new String[]{"Inheritance", "Encapsulation", "Method Overloading", "Abstraction"},
                2
        ));

        questions.add(new Question(
                "Which interface is used for grouping radio buttons in Swing?",
                new String[]{"ButtonGroup", "RadioGroup", "GroupButton", "OptionGroup"},
                0
        ));

        questions.add(new Question(
                "Which class is commonly used for a countdown timer in Swing?",
                new String[]{"ThreadTimer", "SwingTimer", "Timer", "CountTimer"},
                2
        ));

        questions.add(new Question(
                "Which layout allows switching between different panels?",
                new String[]{"FlowLayout", "GridLayout", "CardLayout", "BorderLayout"},
                2
        ));

        return questions;
    }
}