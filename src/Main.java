import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final List<Integer> pontuations = new ArrayList<>();
    private static final List<String> dificultys = new ArrayList<>();

    public static void main(String[] args) {
        guessingGame();

    }

    public static void guessingGame() {
        Scanner sc = new Scanner(System.in);
        int option = 1;
        while (option > 0) {
            Random rand = new Random();
            int initialNumber = 1;
            int finalNumber = 100;
            int numberToGuess = rand.nextInt(initialNumber, finalNumber + 1);
            int attempts = startMenu();

            long startTime = System.currentTimeMillis();
            playingGuessingGame(numberToGuess, attempts);
            long endTime = System.currentTimeMillis();

            long elapsedTime = endTime - startTime;

            long minutes = elapsedTime / 60000;
            long seconds = elapsedTime / 1000;

            System.out.println("Elapsed time: " + minutes + " minutes, " + seconds + " seconds, " + elapsedTime / 100 + " milliseconds");

            System.out.println();
            System.out.println("Wanna play again ?");
            System.out.println("""
                    1. Yes
                    0. No
                    """);
            option = sc.nextInt();
            sc.nextLine();

        }
        System.out.println("Thanks for playing!");
        System.out.println("Your pontuations was");
        for (int i = 0; i < pontuations.size(); i++) {
            System.out.println(pontuations.get(i) + "(" + dificultys.get(i) + ")");
        }

    }

    public static void playingGuessingGame(int numberToGuess, int attempts) {

        Scanner sc = new Scanner(System.in);

        int choice;
        int attemptsSpent = 0;

        boolean isGuessed = false;


        for (int i = attempts; i > 0; i--) {
            System.out.println();
            System.out.print("Enter your guess: ");
            choice = sc.nextInt();
            sc.nextLine();
            if (choice > numberToGuess) {
                System.out.println("Incorrect! The number is less than " + choice);
            } else if (choice < numberToGuess) {
                System.out.println("Incorrect! The number is greater than " + choice);
            } else {
                attemptsSpent = (attempts - i + 1);
                isGuessed = true;
                System.out.println();
                System.out.println("Congratulations! You guessed the correct number in " + attemptsSpent + " attempts.");
                System.out.println();
                break;
            }
            if (i == 1) {
                System.out.println();
                System.out.println("Sorry! You Lost");
                System.out.println();
            }
        }
        pointsSystem(attempts, attemptsSpent, isGuessed);
    }

    public static int startMenu() {
        Scanner sc = new Scanner(System.in);
        int attempts = 0;
        String difficulty = "";
        System.out.println();
        System.out.println(
                """
                        Welcome to the Number Guessing Game!
                        I'm thinking of a number between 1 and 100.
                        """);
        System.out.println("""
                Please select the difficulty level:
                1. Easy (10 chances)
                2. Medium (5 chances)
                3. Hard (3 chances)
                """);
        System.out.print("Enter your choice: ");
        int chosenNumber = sc.nextInt();
        sc.nextLine();
        switch (chosenNumber) {
            case 1:
                attempts = 10;
                difficulty = selectDificulty(attempts);
                break;
            case 2:
                attempts = 5;
                difficulty = selectDificulty(attempts);
                break;
            case 3:
                attempts = 3;
                difficulty = selectDificulty(attempts);
                break;
            default:
                System.out.println("Invalid choice");
                startMenu();
        }
        System.out.println();
        System.out.println("Great! You have selected the " + difficulty + " difficulty level.");
        System.out.println("You have " + attempts + " chances to guess the correct number.");
        System.out.println("Let's start the game!");


        return attempts;
    }

    public static void pointsSystem(int attempts, int attemptsSpent, boolean isGuessed) {

        int basePoints = 1000;
        double difficultyMultiplier = .75;
        String dificulty = selectDificulty(attempts);
        difficultyMultiplier = switch (attempts) {
            case 3 -> {
                basePoints = 5000;
                yield 3;
            }
            case 5 -> {
                basePoints = 3000;
                yield 1.5;
            }
            default -> difficultyMultiplier;
        };


        double pontuation = (basePoints);

        int bonus = (attempts - attemptsSpent) * 100;

        if (!isGuessed) {
            pontuation = 0;
        } else {
            pontuation = Math.max(pontuation + bonus, (double) basePoints / 5);
            pontuation *= difficultyMultiplier;
        }

        System.out.println("Your pontuation is " + (int) pontuation);
        pontuations.add((int) pontuation);
        dificultys.add(dificulty);
    }

    public static String selectDificulty(int attempts) {
        return switch (attempts) {
            case 3 -> "Hard";
            case 5 -> "Medium";
            case 10 -> "Easy";
            default -> "";
        };
    }
}