/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce191249_l03;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;
import java.util.Scanner;

/**
 * L03 - Title: Hangman.
 *
 * @author Le Thien Tri - CE191249 - Date: 19/03/2025
 */
public class Word {

// Declare a String array to store the words from the file
    private String[] listWords;

// Declare a String to store all words in a single concatenated format
    private String strWord;

    /**
     * This method initializes the word processing by resetting strWord and
     * calling the createWord method to load words from a file.
     *
     * @param filePath The path of the file containing words.
     */
    public void word(String filePath) {
        strWord = ""; // Reset the string to ensure it starts fresh
        createWord(filePath); // Call method to read words from the file

    }

    /**
     * Reads words from a file and stores them in a single string separated by
     * '@'. If the file does not exist, an error message is displayed.
     *
     * @param filePath The path of the file to be read.
     */
    public void createWord(String filePath) {
        try {
            // Create a File object with the specified path
            File file = new File(filePath);
            // Check if the file does not exist (this check is redundant since we already opened it)
            if (!file.exists()) {
                System.out.println("The file not found!"); // Display error message
                return; // Exit method
            }
            // Open the file for reading
            FileInputStream fileInputStream = new FileInputStream(file);
            Scanner sc = new Scanner(fileInputStream); // Scanner object to read file content
            boolean isEmpty = false; // File empty = true.
            // Read each line of the file and append it to strWord with '@' as a separator
            while (sc.hasNextLine()) {
                this.strWord += sc.nextLine() + "@";
            }
            if (this.strWord.isEmpty()) { // File empty true 
                isEmpty = true;
            }
            if (isEmpty) { // Display file empty
                System.out.println("This File is empty!");
                return; // return menu
            } else {
                getWordToList(); // Convert the concatenated string into a list
                getPuzzel(); // Start the puzzle game
            }
        } catch (Exception e) {
            System.out.println("The file not found!"); // Handle any errors in file reading
        }
    }

    /**
     * Splits the strWord string using '@' and stores the words in the listWords
     * array.
     */
    public void getWordToList() {
        listWords = strWord.split("@"); // Split the string into an array using '@' as a separator

    }

    /**
     * Selects a random word from the list, converts it to uppercase, and
     * replaces the letters with underscores for the puzzle.
     */
    public void getPuzzel() {
        Random obj = new Random(); // Create a Random object
        int ran_num = obj.nextInt(listWords.length); // Get a random index within the word list
        String word = listWords[ran_num]; // Select a random word
        word = word.toUpperCase(); // Convert the word to uppercase

        // Replace all letters with underscores and separate them with spaces
        String wordAfterReplace = word.replaceAll("[A-Z]", "_ ");

        startGame(word, wordAfterReplace); // Call the game logic with the chosen word
    }

    /**
     * Starts the Hangman-style word guessing game.
     *
     * @param word The actual word to be guessed.
     * @param wordAfterReplace The word displayed with underscores replacing letters.
     */
    public void startGame(String word, String wordAfterReplace) {
        System.out.println("Let's play the game"); // Display game start message
        Scanner sc = new Scanner(System.in); // Create Scanner for user input
        int guess_ = 0; // Counter for the number of guesses
        int wrong = 0; // Counter for wrong guesses
        String guess; // Variable to store user input
        char letter; // Stores the guessed letter
        boolean guessescontainsguess; // Checks if letter was already guessed
        String guesses = ""; // Stores all guessed letters
        boolean guessinword; // Checks if letter is in the word

        // Loop until the user reaches the maximum wrong guesses or completes the word
        while (wrong < 5 && wordAfterReplace.contains("_")) {
            System.out.println(wordAfterReplace + "\n"); // Display the current puzzle word
            int temp = 5 - wrong; // Calculate remaining attempts

            // Display remaining attempts if wrong guesses were made
            if (wrong != 0) {
                System.out.println("You have " + temp + " guesses left.");
            }
            boolean isValid = true;
            while (isValid) {
                System.out.print("Your Guess: "); // Prompt for user input
                guess = sc.nextLine().trim(); // Read user input
                if (guess.isEmpty()) { // User enter empty display notice and allow user enter again.
                    System.out.println("Can't be enter empty.");
                    continue;
                }
                guess = guess.toUpperCase(); // Convert input to uppercase
                letter = guess.charAt(0); // Extract the first character from input
                if (checkInput(guess, "Can't be enter special character", "[a-zA-Z]+")) {
                    continue;
                }

                isValid = false;
                // Check if the letter has already been guessed
                guessescontainsguess = (guesses.indexOf(letter)) != -1;
                guesses += letter; // Add guessed letter to the list of previous guesses

                // Check if the guessed letter is in the word
                guessinword = (word.indexOf(letter)) != -1;
                System.out.println(); // create space

                // If the letter was already guessed
                if (guessescontainsguess) {
                    System.out.println("You ALREADY guessed " + letter + ". \n");
                }

                // If the guessed letter is in the word
                if (guessinword) {
                    System.out.println(letter + " is present in the word.\n");

                    // Replace underscores with the guessed letter in the correct positions
                    for (int i = 0; i < word.length(); i++) { 
                        if (word.charAt(i) == letter && wordAfterReplace.charAt(i) != letter) { // meaning letter i = word i and letter i != wordAfterReplace(letter != "_") code below will be run else run nothing skip.
                            wordAfterReplace = wordAfterReplace.replaceAll("_ ", "_"); // Remove extra spaces for easier replacement
                            String word2 = wordAfterReplace.substring(0, i) + letter + wordAfterReplace.substring(i + 1); // 
                            word2 = word2.replaceAll("_", "_ "); // Restore space-separated format
                            wordAfterReplace = word2; // Update the word display
                        }
                    }
                } else { // If the guessed letter is not in the word
                    System.out.println(letter + " is not present in the word.");
                    if (guessescontainsguess) { // if Already guessed and wrong not minus wrong.
                        continue;
                    } else {
                        wrong++; // Increment wrong guess count
                    }
                }

                guess_++; // Increment total guess count
            }
        }

        // If the player reaches the maximum wrong guesses, they lose
        if (wrong == 5) {
            System.out.println("");
            System.out.println("The word was: " + word); // show word after loose
            System.out.println("YOU LOST! Maximum limit of incorrect guesses reached.");
        } else {
            // If the word is fully guessed, the player wins
            System.out.println("The word is: " + wordAfterReplace + "\nWell Played, you did it!!\n");
        }
    }

    /**
     * Validates user input against a regular expression pattern.
     *
     * @param input The user input to validate.
     * @param msg Error message to display if validation fails.
     * @param keyWord Regular expression pattern to match against.
     * @return boolean indicating if input is invalid (true = invalid, false =
     * valid).
     */
    public boolean checkInput(String input, String msg, String keyWord) {
        try {
            if (!input.matches(keyWord)) { // Check if input matches the pattern
                throw new Exception(msg); // Throw exception if invalid
            }
            return false; // Input is valid
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Print error message
            return true; // Input is invalid
        }
    }
}
