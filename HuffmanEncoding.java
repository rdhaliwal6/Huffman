package com.raj;

import java.util.*;
/**
 * @author Rajpreet Dhaliwal
 * @version 1.0
 * This is the back-bone of the HuffmanTest.
 * This is where all the function are located.
 * This program will create all the maps, queues and nodes used
 * to encode and decode a huffman tree.
 */
public class HuffmanEncoding {

    private Map<Character, Double> frequencyMap = new TreeMap<>();
    private static Map<Character, String> characterEncodings = new HashMap<>();
    private static Queue<HuffmanNode> tree = new PriorityQueue<>();
    private double sizeOfString;
    private static String input;
    /**
     * This function gets the source input.
     * It then calls the other functions.
     * @param sourceText text input to encode.
     */
    public HuffmanEncoding(String sourceText){
//        //test to see if calling function works.
//        System.out.println(sourceText);
        setSizeOfString(sourceText.length());
        setInput(sourceText);
        analyzeText();
    }

    private void analyzeText(){
        //this for loop goes thru the input string and adds the charter.
        //if the character is already in the map it updates the frequency.
        for (int i = 0; i < getSizeOfString(); i++) {
            char current = getInput().charAt(i);
            if(!getFrequencyMap().containsKey(current)){
                getFrequencyMap().put(current,1.0/ getSizeOfString());
            }
            else{
                getFrequencyMap().put(current,(getFrequencyMap().get(current)+(1/ getSizeOfString())));
            }
        }
        //calls the print function to print frequencies.
        printCharacterFrequencies();

        //this for loop creates nodes for each element in the frequency map.
        for (Character key: getFrequencyMap().keySet()) {
            tree.add(new HuffmanNode(key, getFrequencyMap().get(key),null, null));
        }
        //        tester to see output
//        System.out.println(tree);

        //this while loop removes two of the lowest frequencies and adds them with the data being the null char
        // and the probability being the addition of the 2 frequencies.
        while (tree.size()!= 1){
            HuffmanNode alpha = tree.remove();
            HuffmanNode bravo = tree.remove();
            tree.add(new HuffmanNode('\u0000', (alpha.probability+bravo.probability), alpha, bravo));
        }
//        tester to see output
//        System.out.println(tree);
    }

    /**
     * This function prints the frequency.
     */
    public void printCharacterFrequencies(){
//        //test to check out put of map
//        frequencyMap.put('a',2.99);
        //this is a for each loop to print the each key and value.
        System.out.println("Character frequencies from text:");
        for (Character key: getFrequencyMap().keySet()) {
            System.out.println(key + " -> " + getFrequencyMap().get(key));
        }
    }

// --Commented out by Inspection START (1/21/2020 10:10 AM):
//    /**
//     * This function gets the queue.
//     * @return queue
//     */
//    public Queue<HuffmanNode> getTree() {
//        return tree;
//    }
// --Commented out by Inspection STOP (1/21/2020 10:10 AM)

//    /**
//     * This functions sets the queue;
//     * @param tree the queue/
//
//   public void setTree(Queue<HuffmanNode> tree) {
//       HuffmanEncoding.tree = tree;
//     }
//    */


    public static class HuffmanNode implements Comparable<HuffmanNode>
    {
        private final char data;
        private final double probability;
        private final HuffmanNode left;
        private final HuffmanNode right;

        /**
         * This function creates a node with the given values.
         * @param character character being putin.
         * @param frequency probability in the text.
         * @param left left node
         * @param right right node.
         */
        public HuffmanNode(char character, double frequency, HuffmanNode left, HuffmanNode right){
            data = character;
            probability = frequency;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(HuffmanNode other) {
            return Double.compare(this.probability, other.probability);
        }

        @Override
        public String toString() {
            return "HuffmanNode{" +
                    "data=" + data +
                    ", probability=" + probability +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    /**
     * This function encodes the text.
     * @return encoded text.
     */
    public static String encode(){
        String encode = "";
        StringBuilder returnString = new StringBuilder();
        HuffmanNode last = tree.peek();
        assert last != null;
        encodeInorder(last, encode);
//        tester code to see encode values
//   System.out.println(characterEncodings);
        System.out.println("Huffman encoding map for text:");
        for (Character key: characterEncodings.keySet()) {
            System.out.println(key + " -> " + characterEncodings.get(key));
        }

        for (int i = 0; i <input.length() ; i++) {
            char letter = input.charAt(i);
        for (Character key: characterEncodings.keySet()) {
            if(letter == key) {
                returnString.append(characterEncodings.get(key));
            }
            }
        }
        return returnString.toString();
    }

    private static void encodeInorder(HuffmanNode node, String encode){
        if (node.data != '\u0000') {
            characterEncodings.put(node.data, encode);
            return;
        }
        /* first recur on left child */
        encode = encode+"1";
        encodeInorder(node.left, encode);

        //when going right it removes the 1 and adds the 0.

        encode = encode.substring(0,encode.length()-1)+"0";
        /* now recur on right child */
        encodeInorder(node.right, encode);
    }

    /**
     * This function  decodes the text.
     * @param encodedText text to be decoded
     * @return decoded text
     */
    public static String decode(String encodedText){
        //this passes the tree and the encoded text to a helper function.
        return deCoderSearch(tree.peek(), encodedText);
    }

    private static String deCoderSearch(HuffmanNode node, String encodedText){
        StringBuilder decodedText = new StringBuilder();
        char index;
        HuffmanNode current = node;

        //this for loop goes thru each index of the encoded text and the goes left if its 1 or right
        // if its 0 it goes to the left. If it finds a letter it adds it to the decoded string and resets
        //to the head and repeated till the end of the encoded text.

        for (int i = 0; i < encodedText.length() ; i++) {
            index = encodedText.charAt(i);
            if(index == '1' ){
                current = current.left;
                if(current.data != '\u0000'){
                    decodedText.append(current.data);
                    current = node;
                }
            }
            else {
                current = current.right;
                if(current.data != '\u0000') {
                    decodedText.append(current.data);
                    current = node;
                }
            }
        }
        return decodedText.toString();
    }

    @Override
    public String toString() {
        return "HuffmanEncoding{" +
                "frequencyMap=" + getFrequencyMap() +
                ", characterEncodings=" + getCharacterEncodings() +
                ", sizeOfString=" + getSizeOfString() +
                ", input='" + getInput() + '\'' +
                '}';
    }

    /**
     * This function is a getter for map.
     * @return characterEncodings map
     */
    public Map<Character, String> getCharacterEncodings() {
        return characterEncodings;
    }

//    /**
//     * This function is the setter for map but is never used.
//     * @param characterEncodings map
//     */
//    public void setCharacterEncodings(Map<Character, String> characterEncodings) {
//        HuffmanEncoding.characterEncodings = characterEncodings;
//    }

    /**
     * This function is a getter for frequency map;
     * @return frequency map;
     */
    public Map<Character, Double> getFrequencyMap() {
        return frequencyMap;
    }

//    /**
//     * This function sets the frequency map but is never used.
//     * @param frequencyMap frequency map
//     */
//    public void setFrequencyMap(Map<Character, Double> frequencyMap) {
//        this.frequencyMap = frequencyMap;
//    }

    /**
     * This function return size of string.
     * @return size of string
     */
    public double getSizeOfString() {
        return sizeOfString;
    }

    /**
     * This function sets the size of string.
     * @param sizeOfString the size of string.
     */
    public void setSizeOfString(double sizeOfString) {
        this.sizeOfString = sizeOfString;
    }

    /**
     * This function gets the input.
     * @return the input
     */
    public String getInput() {
        return input;
    }

    /**
     * This function sets the input.
     * @param input the input
     */
    public void setInput(String input) {
        HuffmanEncoding.input = input;
    }
}
