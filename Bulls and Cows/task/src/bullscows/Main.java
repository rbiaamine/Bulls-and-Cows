package bullscows;

import java.util.Scanner;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask for the length of the secret code
        System.out.print("Please, enter the secret code's length: ");
        int length = 0;
        if (!scanner.hasNextInt()) {
            System.out.println("Error: the length isn't a valid number.");
            return;
        }
        length = scanner.nextInt();

        // Ask for the number of possible symbols in the code
        System.out.print("Please, enter the number of possible symbols in the code: ");
        int symbolRange = 0;
        if (!scanner.hasNextInt()) {
            System.out.println("Error: the range isn't a valid number.");
            return;
        }
        symbolRange = scanner.nextInt();

        // Validate the inputs
        if (length <= 0 || symbolRange <= 0 || symbolRange > 36 || length > symbolRange) {
            if (length > symbolRange) {
                System.out.println("Error: it's not possible to generate a code with a length of " + length + " with " + symbolRange + " unique symbols.");
            } else if (symbolRange > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            } else {
                System.out.println("Error: invalid input.");
            }
            return;
        }

        // Generate the secret code
        String secretCode = generateSecretCode(length, symbolRange);
        System.out.println("The secret is prepared: " + "*".repeat(length) + " (" + getSymbols(symbolRange) + ").");

        System.out.println("Okay, let's start a game!");

        // Start the game loop
        int turn = 1;
        while (true) {
            System.out.println("Turn " + turn + ":");
            String guess = scanner.next();
            String result = gradeGuess(secretCode, guess);

            System.out.println(result);

            if (guess.equals(secretCode)) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }
            turn++;
        }

        scanner.close();
    }

    // Generates a random secret code with unique digits and symbols using Random
    private static String generateSecretCode(int length, int symbolRange) {
        String possibleSymbols = getSymbols(symbolRange);
        Random random = new Random();
        Set<Character> usedSymbols = new HashSet<>();
        StringBuilder code = new StringBuilder();

        while (code.length() < length) {
            char symbol = possibleSymbols.charAt(random.nextInt(symbolRange));

            // Ensure the symbol is unique
            if (!usedSymbols.contains(symbol)) {
                code.append(symbol);
                usedSymbols.add(symbol);
            }
        }
        return code.toString();
    }

    // Get the available symbols based on the symbol range (0-9, a-z)
    private static String getSymbols(int symbolRange) {
        StringBuilder symbols = new StringBuilder();

        // Add digits (0-9)
        for (char c = '0'; c <= '9'; c++) {
            symbols.append(c);
        }

        // Add lowercase letters (a-z)
        for (char c = 'a'; c <= 'z'; c++) {
            symbols.append(c);
        }

        // Return a substring based on the symbol range
        return symbols.substring(0, symbolRange);
    }

    // Grades the guess in terms of bulls and cows
    private static String gradeGuess(String secretCode, String guess) {
        int bulls = 0;
        int cows = 0;

        if (guess.length() != secretCode.length()) {
            return "Error: The guess should be " + secretCode.length() + " symbols long.";
        }

        for (int i = 0; i < secretCode.length(); i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                bulls++;
            } else if (secretCode.contains(String.valueOf(guess.charAt(i)))) {
                cows++;
            }
        }

        if (bulls == 0 && cows == 0) {
            return "Grade: None.";
        } else if (bulls > 0 && cows > 0) {
            return "Grade: " + bulls + " bull(s) and " + cows + " cow(s).";
        } else if (bulls > 0) {
            return "Grade: " + bulls + " bull(s).";
        } else {
            return "Grade: " + cows + " cow(s).";
        }
    }
}
