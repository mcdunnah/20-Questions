/**
 * @author Mia McDunnah
 * CS 145 QuestionTree Program
 */

public class QuestionNode {
   public String data; 
   public QuestionNode left;
   public QuestionNode right;
   
   /**
    * Constructs a new QuestionNode with the given data and no children.
    * @param data The data to be stored in the node.
    */
   public QuestionNode (String data) {
      this (data, null, null);
   }

   /**
    * Constructs a new QuestionNode with the given data and specified left and right children.
    * @param data The data to be stored in the node.
    * @param left The left child node.
    * @param right The right child node.
    */
   public QuestionNode (String data, QuestionNode left, QuestionNode right) {
      this.data = data;
      this.left = left;
      this.right = right;
   }

   /**
    * Checks if the node is a leaf node (answer node).
    * @return true if the node is an answer (leaf) node, false otherwise.
    */
   public boolean isAnswer() {
      return left == null && right == null;
   }
}