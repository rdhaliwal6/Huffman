package com.raj;

import java.util.Scanner;
/**
 * @author Rajpreet Dhaliwal
 * @version 1.0
 * This program will call the methods of the HuffmanEncoding.java.
 * The program will prompt the user for string and then display
 * the frequency. From the frequency it encodes and add to a map.
 * Then the map can be used to decode the encoded text.
 */
public class HuffmanTest {
    public static final int SIXTEEN = 16;
    private static String ogText;
    private static int textSize;
    /**
     * This is the main function.
     * It will call the other functions.
     * @param args default
     */
    public static void main(String[] args) {
        welcomesUser();
    }

    private static void welcomesUser(){
        System.out.println("Welcome to my Huffman Encoding Program!");
        System.out.println("***************************************");
        Scanner scanner = new Scanner(System.in); //creates a scanner for user input
        System.out.print("Please enter a string: "); //asks the user for the input.
        ogText = scanner.nextLine(); //gets the user input and puts it into a string variable.
        textSize = ogText.length();
//        //test to see if scanner works
//        System.out.println(input);
        if(ogText.length() < 2){   //if no text or a small text is enter, rerun.
            System.out.println("Please Re-run and insert a bigger string");
        }
        else {
            new HuffmanEncoding(ogText);
            showResults(); //prints out results
        }

}

    private static void encodeAndDecodeString(){
        String encode = HuffmanEncoding.encode();
        System.out.println("Encoded text: " + encode);
        System.out.println("Encoded text length: " + encode.length());
        System.out.println();
        System.out.println("Decoded text: " + HuffmanEncoding.decode(encode));
    }

    private static void showResults(){
        System.out.println("Original text: " + ogText);
        System.out.println("Original text length: " + (textSize*8) + " - " + (textSize* SIXTEEN) + " bits");
        System.out.println();
        encodeAndDecodeString(); //prints the encode and decode results;
    }
}
