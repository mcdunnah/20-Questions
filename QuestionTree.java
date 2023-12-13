/**
 * @author Mia McDunnah
 * CS 145 QuestionTree Program
 */

import java.io.*;
import java.util.*;

public class QuestionTree {
   private UserInterface ui;
   private QuestionNode root;
   private int gamesPlayed;
   private int gamesWon;

   /**
    * Constructs a QuestionTree with the given UserInterface.
    * @param ui The user interface for input/output.
    */
   public QuestionTree(UserInterface ui) {
      this.ui = ui;
      root = new QuestionNode("Jedi");
      gamesPlayed = 0;
      gamesWon = 0;
   }

   /**
    * Plays one complete guessing game with the user.
    */
   public void play() {
      root = playGame(root);
   }

   /**
    * Recursively plays the 20 Questions game.
    * @param node The current node in the tree.
    * @return The updated node after playing the game.
    */
   private QuestionNode playGame(QuestionNode node) {
      // Base case: if current node is an answer node
      if (node.isAnswer()) {
         gamesPlayed++;
         ui.print("Would your object happen to be " + node.data + "? ");
         boolean computerWins = ui.nextBoolean();
         if (computerWins) {
            gamesWon++;
            ui.println("I win!");
         } else {
            // If computer loses, learn a new object
            node = learnNewObject(node);
         }
      } else {
         // Recursive case: if current node is a question node
         ui.print(node.data);
         boolean userAnswer = ui.nextBoolean();
         if (userAnswer) {
            // Traverse left if user answers yes
            node.left = playGame(node.left);
         } else {
            // Traverse right if user answers no
            node.right = playGame(node.right);
         }
      }
      return node;
   }

   /**
    * Handles the case where the computer loses and learns a new object.
    * @param oldAnswerNode The node representing the old incorrect answer.
    * @return The updated node after learning the new object.
    */
   private QuestionNode learnNewObject(QuestionNode oldAnswerNode) {
      // Ask user for  new object and a question
      ui.print("I lose. What is your object? ");
      String userObject = ui.nextLine();
      ui.print("Type a yes/no question to distinguish your item from " + oldAnswerNode.data + ": ");
      String newQuestion = ui.nextLine();
      ui.print("And what is the answer for your object? ");
      boolean userAnswer = ui.nextBoolean();

      // Create new question node with new question and answer
      QuestionNode newQuestionNode = new QuestionNode(newQuestion);
      QuestionNode userObjectNode = new QuestionNode(userObject);
      if (userAnswer) {
         newQuestionNode.left = userObjectNode;
         newQuestionNode.right = oldAnswerNode;
      } else {
         newQuestionNode.left = oldAnswerNode;
         newQuestionNode.right = userObjectNode;
      }
      return newQuestionNode;
   }

   /**
    * Saves the current tree state to an output file.
    * @param output The PrintStream representing the output file.
    */
   public void save(PrintStream output) {
      saveTree(root, output);
   }

   /**
    * Recursively saves the tree to an output file.
    * @param node The current node being processed.
    * @param output The PrintStream representing the output file.
    */
   private void saveTree(QuestionNode node, PrintStream output) {
      if (node == null) {
         return;
      }
      if (node.isAnswer()) {
         output.println("A: " + node.data);
      } else {
         // Recursive calls for left and right children
         output.println("Q: " + node.data);
         saveTree(node.left, output);
         saveTree(node.right, output);
      }
   }

   /**
    * Replaces the current tree by reading another tree from a file.
    * @param input The Scanner reading from the input file.
    */
    public void load(Scanner input) {
        root = loadTree(input);
    }

   /**
    * Recursively loads the tree from an input file.
    * @param input The Scanner reading from the input file.
    * @return The root node of the loaded tree.
    */
   private QuestionNode loadTree(Scanner input) {
      if (!input.hasNext()) {
         return null;
      }

      String line = input.nextLine();
      String[] parts = line.split(" ", 2);
      String type = parts[0];
      String data = parts[1].trim();

      if (type.equals("A:")) {
         // Base case: create and return an answer node
         return new QuestionNode(data);
      } else if (type.equals("Q:")) {
         // Create question node and set its left and right children
         QuestionNode node = new QuestionNode(data);
         node.left = loadTree(input);
         node.right = loadTree(input);
         return node;
      } else {
         throw new IllegalArgumentException("Invalid input format");
      }
   }

   /**
    * Returns the total number of games played.
    * @return The total number of games played.
    */
   public int totalGames() {
      return gamesPlayed;
   }

   /**
    * Returns the number of games won by the computer.
    * @return The number of games won by the computer.
    */
   public int gamesWon() {
      return gamesWon;
   }
}