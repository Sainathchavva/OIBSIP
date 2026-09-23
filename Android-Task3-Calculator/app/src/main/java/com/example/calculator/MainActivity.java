package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Display
    private TextView display;

    // Stores the complete expression.
    // Example: 25+10×2
    private final StringBuilder expression = new StringBuilder();

    // Indicates whether the display currently contains
    // the result of the previous calculation.
    private boolean resultDisplayed = false;

    // ---------------------------------------------------------
    // ACTIVITY INITIALIZATION
    // ---------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initializeViews();
        setupNumberButtons();
        setupOperatorButtons();
        setupDecimalButton();
        setupEqualsButton();
        setupClearButton();
        setupBackspaceButton();
    }

    // ---------------------------------------------------------
    // VIEW INITIALIZATION
    // ---------------------------------------------------------

    private void initializeViews() {

        display = findViewById(R.id.display);
    }

    // ---------------------------------------------------------
    // NUMBER BUTTONS
    // ---------------------------------------------------------

    private void setupNumberButtons() {

        int[] numberButtonIds = {
                R.id.button0,
                R.id.button1,
                R.id.button2,
                R.id.button3,
                R.id.button4,
                R.id.button5,
                R.id.button6,
                R.id.button7,
                R.id.button8,
                R.id.button9
        };

        View.OnClickListener numberListener = view -> {

            Button button = (Button) view;

            String number = button.getText().toString();

            // Start a new expression after displaying a result.
            if (resultDisplayed) {
                clearExpression();
            }

            expression.append(number);

            updateDisplay();
        };

        for (int buttonId : numberButtonIds) {

            Button button = findViewById(buttonId);

            button.setOnClickListener(numberListener);
        }
    }

    // ---------------------------------------------------------
    // OPERATOR BUTTONS
    // ---------------------------------------------------------

    private void setupOperatorButtons() {

        int[] operatorButtonIds = {
                R.id.buttonAdd,
                R.id.buttonSubtract,
                R.id.buttonMultiply,
                R.id.buttonDivide
        };

        View.OnClickListener operatorListener = view -> {

            Button button = (Button) view;

            String operator = button.getText().toString();

            addOperator(operator);
        };

        for (int buttonId : operatorButtonIds) {

            Button button = findViewById(buttonId);

            button.setOnClickListener(operatorListener);
        }
    }

    /**
     * Adds an operator to the expression.
     * Prevents multiple operators from appearing consecutively.
     */
    private void addOperator(String operator) {

        if (expression.length() == 0) {
            return;
        }

        // If the previous action produced a result,
        // continue the calculation using that result.
        resultDisplayed = false;

        char lastCharacter =
                expression.charAt(expression.length() - 1);

        // Replace the existing operator instead of adding
        // another operator.
        if (isOperator(lastCharacter)) {

            expression.setCharAt(
                    expression.length() - 1,
                    getOperatorCharacter(operator)
            );

        } else {

            expression.append(
                    getOperatorCharacter(operator)
            );
        }

        updateDisplay();
    }

    // ---------------------------------------------------------
    // DECIMAL BUTTON
    // ---------------------------------------------------------

    private void setupDecimalButton() {

        Button decimalButton =
                findViewById(R.id.buttonDecimal);

        decimalButton.setOnClickListener(view -> {

            // Start a new expression if a result is displayed.
            if (resultDisplayed) {
                clearExpression();
            }

            // Check the current number only.
            String currentNumber =
                    getCurrentNumber();

            // Don't allow multiple decimal points.
            if (currentNumber.contains(".")) {
                return;
            }

            // If decimal is the first character,
            // automatically add zero.
            if (currentNumber.isEmpty()) {
                expression.append("0");
            }

            expression.append(".");

            updateDisplay();
        });
    }

    // ---------------------------------------------------------
    // EQUALS BUTTON
    // ---------------------------------------------------------

    private void setupEqualsButton() {

        Button equalsButton =
                findViewById(R.id.buttonEquals);

        equalsButton.setOnClickListener(view -> {

            calculateResult();
        });
    }

    /**
     * Evaluates the current expression.
     */
    private void calculateResult() {

        if (expression.length() == 0) {
            return;
        }

        // Don't calculate incomplete expressions.
        char lastCharacter =
                expression.charAt(expression.length() - 1);

        if (isOperator(lastCharacter)) {
            return;
        }

        try {

            double result =
                    evaluateExpression(expression.toString());

            // Protect against invalid mathematical results.
            if (Double.isNaN(result)
                    || Double.isInfinite(result)) {

                showError();

                return;
            }

            String formattedResult =
                    formatResult(result);

            display.setText(formattedResult);

            expression.setLength(0);
            expression.append(formattedResult);

            resultDisplayed = true;

        } catch (ArithmeticException exception) {

            // Division by zero.
            showError();

        } catch (Exception exception) {

            // Any unexpected invalid expression.
            showError();
        }
    }

    // ---------------------------------------------------------
    // CLEAR BUTTON
    // ---------------------------------------------------------

    private void setupClearButton() {

        Button clearButton =
                findViewById(R.id.buttonClear);

        clearButton.setOnClickListener(view -> {

            clearExpression();
        });
    }

    /**
     * Completely resets the calculator.
     */
    private void clearExpression() {

        expression.setLength(0);

        display.setText("0");

        resultDisplayed = false;
    }

    // ---------------------------------------------------------
    // BACKSPACE BUTTON
    // ---------------------------------------------------------

    private void setupBackspaceButton() {

        Button backspaceButton =
                findViewById(R.id.buttonBackspace);

        backspaceButton.setOnClickListener(view -> {

            deleteLastCharacter();
        });
    }

    /**
     * Removes the last character from the expression.
     */
    private void deleteLastCharacter() {

        if (expression.length() == 0) {
            display.setText("0");
            return;
        }

        expression.deleteCharAt(
                expression.length() - 1
        );

        resultDisplayed = false;

        updateDisplay();
    }

    // ---------------------------------------------------------
    // EXPRESSION EVALUATION
    // ---------------------------------------------------------

    /**
     * Evaluates an expression from left to right.
     *
     * Examples:
     *
     * 10+5       -> 15
     * 20-8       -> 12
     * 6×4        -> 24
     * 20÷5       -> 4
     */
    private double evaluateExpression(String input) {

        String[] numbers =
                input.split("[+\\-×÷]");

        if (numbers.length == 0) {
            throw new IllegalArgumentException();
        }

        double result =
                Double.parseDouble(numbers[0]);

        int numberIndex = 1;

        for (int i = 0; i < input.length(); i++) {

            char character =
                    input.charAt(i);

            if (!isOperator(character)) {
                continue;
            }

            if (numberIndex >= numbers.length) {
                throw new IllegalArgumentException();
            }

            double nextNumber =
                    Double.parseDouble(
                            numbers[numberIndex]
                    );

            switch (character) {

                case '+':

                    result += nextNumber;

                    break;

                case '-':

                    result -= nextNumber;

                    break;

                case '×':

                    result *= nextNumber;

                    break;

                case '÷':

                    if (nextNumber == 0) {
                        throw new ArithmeticException(
                                "Division by zero"
                        );
                    }

                    result /= nextNumber;

                    break;

                default:

                    throw new IllegalArgumentException();
            }

            numberIndex++;
        }

        return result;
    }

    // ---------------------------------------------------------
    // CURRENT NUMBER
    // ---------------------------------------------------------

    /**
     * Returns the number currently being entered.
     *
     * Example:
     *
     * 25+12.5
     *
     * Current number = 12.5
     */
    private String getCurrentNumber() {

        int startIndex =
                expression.length() - 1;

        while (startIndex >= 0) {

            if (isOperator(
                    expression.charAt(startIndex))) {

                break;
            }

            startIndex--;
        }

        return expression.substring(startIndex + 1);
    }

    // ---------------------------------------------------------
    // OPERATOR UTILITIES
    // ---------------------------------------------------------

    /**
     * Checks whether a character is an operator.
     */
    private boolean isOperator(char character) {

        return character == '+'
                || character == '-'
                || character == '×'
                || character == '÷';
    }

    /**
     * Converts the button text into the internal operator.
     */
    private char getOperatorCharacter(String operator) {

        switch (operator) {

            case "+":

                return '+';

            case "−":

                return '-';

            case "×":

                return '×';

            case "÷":

                return '÷';

            default:

                throw new IllegalArgumentException(
                        "Unknown operator"
                );
        }
    }

    // ---------------------------------------------------------
    // DISPLAY
    // ---------------------------------------------------------

    /**
     * Updates the calculator display with
     * the current expression.
     */
    private void updateDisplay() {

        if (expression.length() == 0) {

            display.setText("0");

        } else {

            display.setText(
                    expression.toString()
            );
        }
    }

    // ---------------------------------------------------------
    // ERROR HANDLING
    // ---------------------------------------------------------

    /**
     * Displays Error and safely resets the expression.
     */
    private void showError() {

        display.setText("Error");

        expression.setLength(0);

        resultDisplayed = true;
    }

    // ---------------------------------------------------------
    // RESULT FORMATTING
    // ---------------------------------------------------------

    /**
     * Removes unnecessary .0 from whole-number results.
     *
     * Example:
     *
     * 10.0 -> 10
     *
     * 10.5 -> 10.5
     */
    private String formatResult(double result) {

        if (result == (long) result) {

            return String.valueOf((long) result);
        }

        return String.format(
                        Locale.US,
                        "%.10f",
                        result
                ).replaceAll("0+$", "")
                .replaceAll("\\.$", "");
    }
}