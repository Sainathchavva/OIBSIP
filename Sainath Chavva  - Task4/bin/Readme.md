# Online Examination System

## Project Overview

The Online Examination System is a Java Swing based desktop application designed to conduct a simple multiple-choice examination.

The application provides a student login, profile update screen, timed examination interface, question navigation, automatic submission, manual submission, and result analysis.

## Objective

The main objective of this project is to demonstrate how Java Swing can be used to develop a graphical examination application with multiple screens and a countdown timer.

## Technologies Used

* Java
* Java Swing
* AWT
* Eclipse IDE
* Object-Oriented Programming
* CardLayout
* javax.swing.Timer

## Features

### 1. Login System

The application starts with a login screen containing:

* Username field
* Password field
* Login button

Demo credentials:

```text
Username: student
Password: 12345
```

### 2. Profile Update

After successful login, the student can:

* View the username
* Change the display name
* Change the password
* Start the examination
* Logout

### 3. Examination Screen

The examination screen displays:

* One multiple-choice question at a time
* Four answer options
* Question number
* Previous button
* Next button
* Submit Exam button
* Countdown timer

### 4. Countdown Timer

The examination has a 30-minute countdown timer.

The timer is implemented using:

```java
javax.swing.Timer
```

When the timer reaches zero, the examination is automatically submitted.

### 5. Question Navigation

The student can move between questions using:

* Previous
* Next

Previously selected answers are retained when navigating between questions.

### 6. Manual Submission

The student can submit the examination before the timer expires.

A confirmation dialog is displayed before submission.

### 7. Result Screen

After submission, the application displays:

* Total score
* Number of correct answers
* Number of incorrect answers
* Time taken
* Individual question breakdown
* Selected answer
* Correct answer

### 8. Session Management

If the student attempts to close the application while an examination is running, the system displays:

```text
Are you sure you want to quit?
```

This prevents accidental closing of the examination.

### 9. Logout

After viewing the result, the student can click Logout to return to the login screen.

## Project Structure

```text
OnlineExaminationSystem
│
├── src
│   └── exam
│       ├── Main.java
│       ├── Question.java
│       ├── ExamData.java
│       ├── LoginPanel.java
│       ├── ProfilePanel.java
│       ├── ExamPanel.java
│       ├── ResultPanel.java
│       └── ExamFrame.java
│
└── README.md
```

## Class Description

### Main.java

Starts the Java Swing application.

### ExamFrame.java

Acts as the main application window and manages different screens using `CardLayout`.

### Question.java

Represents an individual multiple-choice question.

It stores:

* Question text
* Four options
* Correct answer

### ExamData.java

Stores the examination questions and their correct answers.

### LoginPanel.java

Provides the username and password login interface.

### ProfilePanel.java

Allows the student to update their display name and password before beginning the examination.

### ExamPanel.java

Controls:

* Question display
* Answer selection
* Previous/Next navigation
* Countdown timer
* Exam submission

### ResultPanel.java

Calculates and displays the final examination result.

## Important Java Concepts Used

### CardLayout

`CardLayout` is used to switch between the login, profile, examination, and result screens.

### ButtonGroup

`ButtonGroup` ensures that only one option can be selected for each MCQ.

### JRadioButton

`JRadioButton` is used to display the four multiple-choice options.

### javax.swing.Timer

`javax.swing.Timer` controls the countdown timer.

### JOptionPane

`JOptionPane` is used for:

* Login errors
* Profile messages
* Submission confirmation
* Quit confirmation
* Time-up notification

### Event Handling

Action listeners are used to respond to:

* Login
* Profile update
* Start examination
* Next
* Previous
* Submit
* Logout

## How to Run

1. Open Eclipse.
2. Import or create the `OnlineExaminationSystem` project.
3. Make sure all Java files are inside the `exam` package.
4. Open `Main.java`.
5. Right-click `Main.java`.
6. Select:

```text
Run As → Java Application
```

## Login Details

```text
Username: student
Password: 12345
```

## Examination Process

```text
Login
  ↓
Profile
  ↓
Start Examination
  ↓
Answer Questions
  ↓
Next / Previous
  ↓
Submit or Timer Expires
  ↓
Result
  ↓
Logout
```

## Future Improvements

The project can be extended by adding:

* Database connectivity
* Multiple student accounts
* Password hashing
* Admin panel
* Question randomization
* Different subjects
* Difficulty levels
* Persistent examination history
* Database-based result storage
* Student registration
* More detailed performance statistics

## Conclusion

The Online Examination System demonstrates the use of Java Swing and object-oriented programming to create a functional GUI-based examination application.

The project combines GUI components, event handling, timers, question management, navigation, session handling, and result calculation in a single desktop application.
