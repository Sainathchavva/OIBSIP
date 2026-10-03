# Number Guessing Game

## 📌 Project Overview

The **Number Guessing Game** is a Java console-based game where the computer randomly generates a number, and the player attempts to guess it within a limited number of attempts.

After every guess, the game provides a hint such as **"Too High!"**, **"Too Low!"**, or **"Correct!"**. The game also keeps track of attempts and scores across multiple rounds.

## 🎯 Objective

The main objective of this project is to implement a simple interactive game while practicing fundamental Java programming concepts such as:

* Random number generation
* User input using `Scanner`
* `while` loops
* `if-else` statements
* Variables and data types
* Methods
* Basic game logic

## 🛠️ Technologies Used

* **Programming Language:** Java
* **Application Type:** Console Application
* **Java Concepts:** `Random`, `Scanner`, loops, conditional statements, methods

## ✨ Features

* Generates a random number at the beginning of every round.
* Allows the user to enter guesses through the console.
* Displays:

  * `Too High!`
  * `Too Low!`
  * `Correct!`
* Displays the current attempt number.
* Limits the player to a maximum of **7 attempts**.
* Displays **"You Lost!"** when the maximum attempts are reached.
* Reveals the correct number after losing.
* Allows the player to start another round.
* Tracks the number of attempts used in each round.
* Displays a round summary.
* Supports multiple rounds during a single execution.

## 🎮 How the Game Works

1. The program starts a new round.
2. The computer generates a random number between **1 and 100**.
3. The player enters a guess.
4. The program compares the guess with the generated number.
5. A hint is displayed:

   * If the guess is greater → `Too High!`
   * If the guess is smaller → `Too Low!`
   * If the guess matches → `Correct!`
6. The attempt counter is updated after each guess.
7. The round ends when:

   * The player guesses the number correctly, or
   * The maximum number of attempts is reached.
8. The player can choose whether to play another round.
9. A summary of the completed round is displayed.

## 📊 Example Output

```text
=================================
       NUMBER GUESSING GAME
=================================

Guess a number between 1 and 100.
You have 7 attempts.

Enter your guess: 50
Too High!
Attempts remaining: 6

Enter your guess: 25
Too Low!
Attempts remaining: 5

Enter your guess: 37
Too High!
Attempts remaining: 4

Enter your guess: 32
Correct!

Round 1 — guessed in 4 attempts.

Do you want to play again? (yes/no): yes

Starting Round 2...
```

## 📁 Project Structure

```text
NumberGuessingGame
│
├── src
│   └── NumberGuessingGame.java
│
└── README.md
```

## ▶️ How to Run

### Using Eclipse

1. Open **Eclipse IDE**.
2. Create or import the `NumberGuessingGame` Java project.
3. Open `NumberGuessingGame.java`.
4. Right-click the file.
5. Select **Run As → Java Application**.
6. Enter your guesses in the Eclipse Console.

### Using Command Prompt / Terminal

Compile the program:

```bash
javac NumberGuessingGame.java
```

Run the program:

```bash
java NumberGuessingGame
```

## 🎲 Game Rules

| Rule             | Description                      |
| ---------------- | -------------------------------- |
| Number Range     | 1 – 100                          |
| Maximum Attempts | 7                                |
| Correct Guess    | Round completed                  |
| Too High         | Guess is greater than the number |
| Too Low          | Guess is smaller than the number |
| Failed Round     | Number is revealed               |
| Replay           | Yes/No option                    |

## ⭐ Bonus Feature — Difficulty Levels

The game can be extended with different difficulty levels:

| Difficulty | Number Range | Attempts |
| ---------- | -----------: | -------: |
| Easy       |       1 – 50 |       10 |
| Medium     |      1 – 100 |        7 |
| Hard       |      1 – 200 |        5 |

## 📚 Learning Outcomes

After completing this project, the developer gains practical experience with:

* Java `Scanner`
* Java `Random`
* `while` loops
* `if-else` conditions
* User input validation
* Counters
* Methods
* Basic program control flow
* Console-based application development

## 🔮 Future Enhancements

Possible improvements include:

* Add difficulty selection.
* Add a total score system.
* Add input validation for invalid values.
* Add a graphical interface using Java Swing.
* Store high scores.
* Add a timer for each round.
* Add more detailed game statistics.

##
