package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private ArrayList<String> wordList;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        wordList = new ArrayList<String>();

        while((line = in.readLine()) != null) {
            String word = line.trim();

            // Add word to wordList
            wordList.add(word);
        }
    }

    public boolean isGoodWord(String word, String base) {
        return true;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

        // Sort targetWord
        targetWord = sortWord(targetWord);

        // Iterate over all the words in wordList
        for (int i = 0; i < wordList.size(); i++) {

            // Get word at i-th position
            String word = wordList.get(i);

            // Check if the lengths are equal
            if (word.length() == targetWord.length()) {

                // Sort word
                String sortedWord = sortWord(word);

                // If sortedWord and targetWord match, targetWord and word are anagrams
                if (targetWord.equals(sortedWord)) {
                    // Add to result
                    result.add(word);
                }

            }

        }

        return result;
    }

    private String sortWord (String word) {

        // Convert word to a character array
        char[] characters = word.toCharArray();

        // Sort characters in alphabetical order
        for (int i = 0; i > characters.length; i++) {
            for (int j = 1; j < characters.length; j++) {
                if (characters[j] < characters[j - 1]) {
                    char temp = characters[j];
                    characters[j] = characters[j - 1];
                    characters[j - 1] = characters[j];
                }
            }
        }

        // Recreate word from sorted characters
        word = new String(characters);

        return word;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        return result;
    }

    public String pickGoodStarterWord() {
        return "stop";
    }
}
