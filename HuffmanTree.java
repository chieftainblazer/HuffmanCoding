// Emanuel Monzon
// 03/10/2020
// CSE 143
// Kevin Son

// Class HuffmanTree can be used to store a series of letters which is a compression that is 
// decided by the file that the client inputs and the input has characters and the compression
// is through the frequency of the characters. The more frequent characters, it means that there
// will be less bits to be used and for the less frequent characters more bits. It will decompress
// the file as well later upon the clients decision.
import java.util.*;
import java.io.*;

public class HuffmanTree {
   private HuffmanNode overallTree;   // Keeps track of the structure of characters ASCII values.
   
   // post: Constructs a HuffmanTree with the given count. With the given count, it then proceeds
   //       to store each character ASCII value with the respective frequency which is given to 
   //       us in given count. It also stores an special character ASCII which has a frequency of
   //       1 which will then be used later within the program. Once it stores all of this, it then
   //       proceeds to build the whole structure with it.
   public HuffmanTree(int[] count) {
      Queue<HuffmanNode> nodesInQueue = new PriorityQueue<HuffmanNode>();
      for (int index = 0; index < count.length; index++) {
         if (count[index] > 0) {
            nodesInQueue.add(new HuffmanNode(index, count[index]));
         }
      }
      nodesInQueue.add(new HuffmanNode(count.length, 1));
      overallTree = buildHuffmanTree(nodesInQueue);
   }
   
   // post: returns a HuffmanNode using the given nodesInQueue. It proceeds to store two structures
   //       at a time that will be part of each HuffmanNode that will be built with the smaller
   //       character ASCII value being stored in the left side and the bigger one being stored
   //       in the right side. It then proceeds to combine the frequency of both which will act
   //       as the father for both. It keeps doing this until all of the character ASCII values
   //       have been processed and are now part of the structure that will be returned.
   private HuffmanNode buildHuffmanTree(Queue<HuffmanNode> nodesInQueue) {
      while (nodesInQueue.size() > 1) {
         HuffmanNode leftSmall = nodesInQueue.remove();
         HuffmanNode rightLarge = nodesInQueue.remove();
         int totalFrequency = leftSmall.frequency + rightLarge.frequency;
         HuffmanNode huffmanNode = new HuffmanNode(leftSmall, rightLarge, totalFrequency);
         nodesInQueue.add(huffmanNode);
      }
      return nodesInQueue.remove();
   }
   
   // post: Constructs a HuffmanTree with the given input. It reads the whole input and it
   //       then proceeds to add each character ASCII value to the structure with the respective
   //       frequency. The file won't be modified upon the end of this task.
   public HuffmanTree(Scanner input) {
      while (input.hasNext()) {
         int n = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         overallTree = buildHuffmanFileTree(n, code, overallTree);
      }
   }
   
   // post: returns a HuffmanNode which contains the whole structure. It uses the given n, the
   //       given code and a given root. The given n is the ASCII characterValue and it determines
   //       the position of each ASCII characterValue in the structure based on the given code 
   //       which is the special route from the top of the structure to where the character
   //       ASCII value is supposed to be. With the given root, it then uses the given root
   //       to place all values in the correct spot. 
   private HuffmanNode buildHuffmanFileTree(int n, String code, HuffmanNode root) {
      if (code.length() == 0) {
         return new HuffmanNode(n, 0);
      } else {
         if (root == null) {
            root = new HuffmanNode(0, 0);
         }
         if (code.charAt(0) == '0') {
            root.left = buildHuffmanFileTree(n, code.substring(1), root.left);
         } else {   //code.charAt(0) == '1'
            root.right = buildHuffmanFileTree(n, code.substring(1), root.right);
         }
         return root;
      }
   }
   
   // post: writes to an output file with the given output and it writes each character ASCII
   //       value with the respective route it took from the top to reach it. The order of the
   //       printing will be from the left of the structure to the right of the structure. 
   public void write(PrintStream output) {
      write(output, overallTree, "");
   }
   
   // post: used to complete the task of writing to the output file by using a given output
   //       a given root and a given zeroOne. The given output will be used to print to a file
   //       and the given root is the one that will be used to gather each value that will be 
   //       be printed. The given zeroOne is the one that contains the route to each character
   //       ASCII value in our structure. 
   private void write(PrintStream output, HuffmanNode root, String zeroOne) {
      if (root.left == null && root.right == null) {
         output.println(root.characterValue);
         output.println(zeroOne);
      } else {
         write(output, root.left, zeroOne + "0");
         write(output, root.right, zeroOne + "1");
      }
   } 
   
   // post: prints a whole file which contains the words from where the structure that we stored
   //       gathered the words to and place them with the respective frequency. The whole point
   //       of this method is to read the bits and generate the output with the corresponding
   //       character. The given output is used to write all the characters. 
   //       The given eof is the value of the special character that was 
   //       included in the structure that signals to end the run. The given special character 
   //       won't be printed. The structure won't be modified.
   public void decode(BitInputStream input, PrintStream output, int eof) {
      HuffmanNode decodeNode = overallTree;
      int number = 0;
      while (decodeNode.characterValue != eof) {
         number = input.readBit();
         if (number == 0) {
            decodeNode = decodeNode.left;
         } else {
            decodeNode = decodeNode.right;
         }
         int charValue = decodeNode.characterValue;
         if (charValue != 0 && charValue != eof) {
            output.write(charValue);
            decodeNode = overallTree;
         }
      }      
   }  
}