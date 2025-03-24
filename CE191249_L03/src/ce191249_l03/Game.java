/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce191249_l03;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * L03 - Title: Hangman.
 *
 * @author Le Thien Tri - CE191249 - Date: 19/03/2025
 */
public class Game {

// Declare a Word object to manage word-related operations
    private Word getWord = new Word();

// Variable to store the file name for topics
    private String fileName;

// Scanner object for taking user input
    private Scanner sc = new Scanner(System.in);

// Boolean flag to control loops
    private boolean miniLoop = true;

// Array to store words for custom topics
    private String[] array;
    
// Array to storage name of file.
    private ArrayList<String> newFileName = new ArrayList<>();

// File to store custom topic names
    private final String CUSTOM_TOPICS_FILE = "custom_topics.txt";
    

    /**
     * Displays the menu options for the user.
     */
    public void menu() {
        System.out.println("         MENU"); // Print menu title
        System.out.println("1. Choice Topic And Play."); // Option 1: Choose a topic and play
        System.out.println("2. Create New Topic."); // Option 2: Create a new custom topic
        System.out.println("3. Information."); // Option 3: Exit the program
        System.out.println("4. Exit."); // Option 3: Exit the program
    }

    /**
     * Handles the user's menu choice and directs them to the appropriate
     * functionality.
     */
    public void choice() {
        getWord.showInfo("guide1.txt");
        loadCustomTopics(); // load custom topic file name.
        miniLoop = true; // Reset the loop flag
        while (miniLoop) { // Start loop to keep showing the menu until exit
            menu(); // Display menu options

            String choice = sc.nextLine().trim(); // Get user input and trim spaces

            if (choice.isEmpty()) { // Check if input is empty
                System.out.println("Can't be enter empty"); // Display error message
                continue; // Continue loop
            }

            if (checkInput(choice, "Enter 1-4", "[1-4]+")) { // Validate input (must be 1-3)
                continue; // If invalid, continue loop
            }

            switch (choice) { // Switch case for different choices
                case "1":
                    choiceTopic(); // Call method to choose a topic
                    miniLoop = true; // Continue looping
                    break;
                case "2":
                    customNewTopic(); // Call method to create a new topic
                    miniLoop = true; // Continue looping
                    break;
                case "3":
                    getWord.getInfo();
                    break;
                case "4":
                    miniLoop = false;
                    break;
            }
        }
    }

    /**
     * Show file name when create new topic.
     */
    public void showNewFileName() {
        if (newFileName.isEmpty()) { // if no topic create it will be display nothing.
            return;
        } else {
            System.out.print("New Topic: "); // create new topic diplay the name of new topic
            for (int i = 0; i < newFileName.size(); i++) { // for loop to display all name
                System.out.print(newFileName.get(i)); // display all name
                if (i < newFileName.size() - 1) { // if i lower than array size print ","
                    System.out.print(",");
                } else {
                    System.out.println("."); // Print "." after the last name display
                }
            }
        }
    }

    /**
     * Allows the user to choose a topic from a predefined list and load it.
     */
    public void choiceTopic() {

        miniLoop = true;
        while (miniLoop) {
            System.out.println("Topic we have: Animal, food, historical, movie, holiday..."); // display file name topic classic dev create for user.
            showNewFileName(); // Show new file name when create.
            System.out.print("Enter topic you want to play: ");

            fileName = sc.nextLine().trim().toLowerCase(); // Get and format input

            if (fileName.isEmpty()) { // Check if input is empty
                System.out.println("Can't be enter empty");
                continue;
            }

            if (checkInput(fileName, "Can't be enter special character", "[a-zA-Z0-9 ]+")) { // Validate input
                continue;
            }

            if (checkInput(fileName, "Can't be enter space", "[a-zA-Z0-9]+")) { // Ensure no spaces
                continue;
            }

            if (checkInput(fileName, "Can't be enter number", "[a-zA-Z]+")) { // Ensure no numbers
                continue;
            }
            fileName = fileName + ".txt"; // Append .txt extension
            getWord.word(fileName); // Load the word file
            
            miniLoop = false; // Exit loop
        }
    }

    /**
     * Allows the user to create a new custom topic by entering words.
     */
    public void customNewTopic() {
        miniLoop = true;

        while (miniLoop) { // Loop to enter topic name
            System.out.println("Enter topic name:");
            String input = sc.nextLine().trim().toLowerCase();

            if (input.isEmpty()) { // Ensure input is not empty
                System.out.println("Can't be enter empty");
                continue;
            }

            if (checkInput(input, "Can be only enter characters", "[a-zA-Z]+")) { // Validate topic name
                continue;
            }
            String register; // Declare Register to storage file name
            register = input; // Storage file name
            input = input + ".txt"; // Append .txt extension

            File file = new File(input); // Create File object
            boolean isExists = false;
            if (file.exists()) { // Check if file already exists
                System.out.println("This file already exists");
                isExists = true;
                continue;
            }
            if (!isExists) {
                newFileName.add(register); // add file name in to array to show out
                saveCustomTopics(); // Save topic names after adding a new one
            }
            while (miniLoop) {
                System.out.println("Enter number of words for the new Topic:");
                String numberWords = sc.nextLine().trim(); // Read input

                if (numberWords.length() > 1) { // Limit topic to max 9 words
                    System.out.println("New Game can only create 9 words.");
                    continue;
                }

                if (numberWords.isEmpty()) { // Check if input is empty
                    System.out.println("Can't be enter empty");
                    continue;
                }

                if (checkInput(numberWords, "Can be only enter number", "[0-9]+")) { // Ensure input is numeric
                    continue;
                }
                if (Integer.parseInt(numberWords) < 1) { // Check if this number below 0 throw warrning and allow user enter again.
                    System.out.println("Can't be enter 0 please enter again.");
                    continue;
                }
                array = new String[Integer.parseInt(numberWords)]; // Create array for words

                for (int i = 0; i < array.length; i++) { // Loop to enter words
                    while (true) {
                        System.out.printf("Enter word %d: ", i + 1);
                        array[i] = sc.nextLine().trim().toLowerCase();

                        if (array[i].isEmpty()) { // Ensure input is not empty
                            System.out.println("Can't be enter empty");
                            continue;
                        }

                        if (checkInput(array[i], "Can be only enter characters", "[a-zA-Z ]+")) { // Validate input
                            continue;
                        }
                        break; // Exit inner loop if valid
                    }
                }

                createNewFile(input); // Create new file

                miniLoop = false; // Exit loop
            }
        }
    }

    /**
     * Creates a new file with the given name and writes the words to it.
     *
     * @param input The name of the file to create.
     */
    public void createNewFile(String input) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(input))) { // Open file for writing
            for (String word : array) { // Loop through the array and write each word
                writer.println(word);
            }
            System.out.println("The new topic has been created"); // Confirm success
        } catch (IOException e) {
            System.out.println("Warning! Failed to create new topic."); // Error message
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

    /**
     * Saves custom topic names to a file.
     */
    private void saveCustomTopics() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOM_TOPICS_FILE))) {  // Open file for writing
            for (String topicName : newFileName) { // Loop through the array and write each topic name
                writer.println(topicName);
            }
        } catch (IOException e) {
            System.out.println("Warning! Failed to save custom topics."); // Error message
        }
    }

    
    /**
     * Loads custom topic names from a file.
     */
    private void loadCustomTopics() {
        File file = new File(CUSTOM_TOPICS_FILE);  // Create File object
        if (file.exists()) { // if file exist 
            try (Scanner fileScanner = new Scanner(new FileInputStream(file))) { // Read file
                while (fileScanner.hasNextLine()) { // read next line in file 
                    String topicName = fileScanner.nextLine().trim(); // read each line and storage.
                    if (!topicName.isEmpty()) { // if topic name not empty
                        newFileName.add(topicName); // add for array topic name
                    }
                }
            } catch (IOException e) {
                System.out.println("Warning! Failed to load custom topics."); // Error message
            }
        }
    }

}
