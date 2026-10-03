import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int round = 1;
        int totalAttempts = 0;

        System.out.println("======================================");
        System.out.println("       NUMBER GUESSING GAME");
        System.out.println("======================================");

        boolean playAgain = true;

        while (playAgain) {

            // Difficulty selection
            System.out.println("\nSelect Difficulty Level:");
            System.out.println("1. Easy   (1-50, 10 attempts)");
            System.out.println("2. Medium (1-100, 7 attempts)");
            System.out.println("3. Hard   (1-200, 5 attempts)");
            System.out.print("Enter your choice: ");

            int difficulty = scanner.nextInt();

            int maxNumber;
            int maxAttempts;

            switch (difficulty) {
                case 1:
                    maxNumber = 50;
                    maxAttempts = 10;
                    break;

                case 2:
                    maxNumber = 100;
                    maxAttempts = 7;
                    break;

                case 3:
                    maxNumber = 200;
                    maxAttempts = 5;
                    break;

                default:
                    System.out.println("Invalid choice! Medium difficulty selected.");
                    maxNumber = 100;
                    maxAttempts = 7;
            }

            // Generate random number
            int secretNumber = random.nextInt(maxNumber) + 1;

            int attempts = 0;
            boolean correct = false;

            System.out.println("\n--------------------------------------");
            System.out.println("Round " + round);
            System.out.println("Guess a number between 1 and " + maxNumber);
            System.out.println("You have " + maxAttempts + " attempts.");
            System.out.println("--------------------------------------");

            // Guessing loop
            while (attempts < maxAttempts) {

                System.out.print("Enter your guess: ");
                int guess = scanner.nextInt();

                attempts++;

                System.out.println("Attempt: " + attempts + "/" + maxAttempts);

                if (guess > secretNumber) {
                    System.out.println("Too High!");

                } else if (guess < secretNumber) {
                    System.out.println("Too Low!");

                } else {
                    System.out.println("Correct!");
                    System.out.println("You guessed the number in "
                            + attempts + " attempts.");
                    correct = true;
                    break;
                }
            }

            // Game lost
            if (!correct) {
                System.out.println("\nYou Lost!");
                System.out.println("The correct number was: " + secretNumber);
            }

            // Round summary
            System.out.println("\n======================================");
            if (correct) {
                System.out.println("Round " + round
                        + " — guessed in " + attempts + " attempts");
                totalAttempts += attempts;
            } else {
                System.out.println("Round " + round
                        + " — failed after " + attempts + " attempts");
            }
            System.out.println("======================================");

            // Play again
            System.out.print("\nDo you want to play again? (yes/no): ");
            String answer = scanner.next();

            if (answer.equalsIgnoreCase("yes")
                    || answer.equalsIgnoreCase("y")) {

                playAgain = true;
                round++;

            } else {
                playAgain = false;
            }
        }

        // Final score
        System.out.println("\n======================================");
        System.out.println("          GAME OVER");
        System.out.println("======================================");
        System.out.println("Total Rounds Played: " + round);

        if (totalAttempts > 0) {
            System.out.println("Total Successful Attempts: " + totalAttempts);
        }

        System.out.println("Thank you for playing!");
        System.out.println("======================================");

        scanner.close();
    }
}