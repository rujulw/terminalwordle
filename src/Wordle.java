import java.util.*;
import java.io.*;

public class Wordle {

    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";

    public static void main(String args[]) {
        Scanner scnr = new Scanner(System.in);

        String option = displayStart(scnr);

        String wordle = generateWordle(option).toUpperCase();

        System.out.println("Loading WORDLE...");
        allGuesses(option, wordle, scnr);

        scnr.close(); // Close the scanner to prevent resource leak
    }

    public static String displayStart(Scanner scnr) {
        System.out.println("             Welcome to WORDLE!");
        System.out.println("             A word guessing game.");
        System.out.println();
        System.out.println("Please select a difficulty level to get started");
        System.out.println();
        System.out.println("                EASY - E");
        System.out.println("                MEDIUM - M");
        System.out.println("                HARD - H");

        System.out.println();

        System.out.print("Choose selection: ");
        String option = scnr.next().toUpperCase();

        if (option.equals("E") || option.equals("M") || option.equals("H")) {
            return option;
        } else {
            System.out.println("Invalid option. Please choose E, M, or H.");
            return displayStart(scnr);
        }
    }

    public static String generateWordle(String option) {
        Scanner read = null; // Declare read outside the if-else blocks

        try {
            if (option.equals("E")) {
                read = new Scanner(new FileInputStream("/Users/rujulw/Desktop/Java Projects/Wordle Clone/lib/5letterwords.txt"));
            } else if (option.equals("M")) {
                read = new Scanner(new FileInputStream("/Users/rujulw/Desktop/Java Projects/Wordle Clone/lib/6letterwords.txt"));
            } else if (option.equals("H")) {
                read = new Scanner(new FileInputStream("/Users/rujulw/Desktop/Java Projects/Wordle Clone/lib/7letterwords.txt"));
            }

            Random randomizer = new Random();
            int num = randomizer.nextInt(500) + 1;

            String wordle = null; // Declare wordle outside the loop
            for (int i = 1; i <= num && read.hasNext(); i++) {
                wordle = read.next(); // Assign the word read from file to wordle
            }

            return wordle;
        } catch (FileNotFoundException e) {
            System.out.println("System error code: 401261");
            return null;
        } finally {
            if (read != null) {
                read.close(); // Close the scanner in finally block to ensure it always gets closed
            }
        }
    }

    public static void allGuesses(String option, String wordle, Scanner scnr) {
        int diff = 0; // Initialize diff to avoid compilation error

        if (option.equals("E")) {
            diff = 5;
        } else if (option.equals("M")) {
            diff = 6;
        } else if (option.equals("H")) {
            diff = 7;
        }

        String guess = "";
        boolean hasWon = false;

        for (int i = 1; i <= 6; i++) {
            System.out.println();
            System.out.print("Guess " + i + ": ");
            guess = guess(diff, scnr);
            System.out.println();
            String result = correctGuesses(guess, wordle);
            if (checkWin(result, wordle)) {
                System.out.println();
                System.out.println("CONGRATULATIONS! YOU SOLVED THE WORDLE!");
                System.out.println();
                hasWon = true;
                break;
            }
        }

        if (!hasWon) {
            System.out.println();
            System.out.println("You have run out of tries! The correct word was: " + wordle);
            System.out.println("Better luck next time!");
            System.out.println();
        }
    }

    public static String correctGuesses(String guess, String wordle) {
        StringBuilder finalGuess = new StringBuilder();

        for (int i = 0; i < guess.length(); i++) {
            char char1 = guess.charAt(i);
            char char2 = wordle.charAt(i);

            if (char1 == char2) {
                // Same letter at the same position
                finalGuess.append(GREEN).append("[").append(char1).append("]").append(RESET);
            } else if (wordle.indexOf(char1) != -1) {
                // Letter in different position
                finalGuess.append(YELLOW).append("[").append(char1).append("]").append(RESET);
            } else {
                // Default case
                finalGuess.append("[").append(char1).append("]");
            }
        }

        System.out.println(finalGuess.toString());
        return finalGuess.toString();
    }

    public static String guess(int diff, Scanner scnr) {
        String guess = "";
        while (guess.length() != diff) {
            guess = scnr.next().toUpperCase();
        }
        return guess;
    }

    public static boolean checkWin(String result, String wordle) {
        // Remove color codes and check if the result matches the wordle
        String cleanResult = result.replace(GREEN, "").replace(RESET, "").replace(YELLOW, "").replace("[", "").replace("]", "");
        return cleanResult.equals(wordle);
    }
}
