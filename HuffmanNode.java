// Emanuel Monzon
// 03/10/2020
// CSE 143
// Kevin Song

// Class HuffmanNode can be used to store a left subtree and a right subtree. With the information
// given to us by the client, it proceeds to store a single leaf node with a frequency and a number
// representing the character it represents. 

import java.util.*;

public class HuffmanNode implements Comparable<HuffmanNode> {
   public int characterValue;           // Represents a number associated to an ASCII character.
   public int frequency;                // Represents a number that tells how many a character.
   public HuffmanNode left;             // Stores the left subtree.
   public HuffmanNode right;            // Stores the right subtree.
   
   // post: Constructs a HuffmanNode with the given characterValue and frequency. It proceeds to 
   //       store both informations and a left and right empty subtree.
   public HuffmanNode(int characterValue, int frequency) {
      this.characterValue = characterValue;
      this.frequency = frequency;
      left = null;
      right = null;
      if (frequency != 0) {
         System.out.println(characterValue + " " + frequency);
      }
   }
   
   // post Constructs  HuffmanNode with a given left subtree, right subtree and given frequency.
   public HuffmanNode(HuffmanNode left, HuffmanNode right, int frequency) {
      this.left = left;
      this.right = right;
      this.frequency = frequency;
   }
   
   // post: returns an integer that can be either: 0 (frequency of this node equal as frequency
   //       of other node frequency. < 0 (frequency of this node less than frequency of other node.
   //       frequency. > 0 (frequency of this node greater than other node frequency.
   public int compareTo(HuffmanNode other) {
      return frequency - other.frequency;
   }
}