package com.google.engedu.anagrams;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private ArrayList<String> wordList;
    private HashSet<String> wordSet;
    private HashMap<String, ArrayList<String>> lettersToWord;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        wordList = new ArrayList<String>();
        wordSet = new HashSet<String>();
        lettersToWord = new HashMap<String, ArrayList<String>>();

        while((line = in.readLine()) != null) {
            String word = line.trim();

            // Add word to wordList
            wordList.add(word);

            // Add word to wordSet;
            wordSet.add(word);

            // Get sorted word
            String sortedWord = sortLetters(word);

            // Check if sortedWord exists in lettersToWord
            if (lettersToWord.containsKey(sortedWord)) {

                // Get anagrams mapped to sortedWord
                ArrayList<String> anagrams = lettersToWord.get(sortedWord);

                // Add current word
                anagrams.add(word);

                // Map anagrams back to sortedWord
                lettersToWord.put(sortedWord, anagrams);

            }
            // sortedWord doesn't exist in lettersToWord
            else {

                // Create anagrams list
                ArrayList<String> anagrams = new ArrayList<String>();

                // Add current word
                anagrams.add(word);

                // Map anagrams to sortedWord
                lettersToWord.put(sortedWord, anagrams);

            }

        }
    }

    public boolean isGoodWord(String word, String base) {

        // word => word entered by user
        // base => the base word for which user has to form anagrams

        // Check if base is a substring of word
        if (word.contains(base)) {
            return false;
        }

        // Check if word is a valid word
        if (wordSet.contains(word)) {
            return true;
        }

        // Word is invalid
        return false;
    }

    public ArrayList<String> getAnagrams(String targetWord) {

        ArrayList<String> result = new ArrayList<String>();

        // Sort targetWord
        targetWord = sortLetters(targetWord);

        // Iterate over all the words in wordList
        for (int i = 0; i < wordList.size(); i++) {

            // Get word at i-th position
            String word = wordList.get(i);

            // Check if the lengths are equal
            if (word.length() == targetWord.length()) {

                // Sort word
                String sortedWord = sortLetters(word);

                // If sortedWord and targetWord match, targetWord and word are anagrams
                if (targetWord.equals(sortedWord)) {
                    // Add to result
                    result.add(word);
                }

            }

        }

        return result;
    }

    private String sortLetters (String word) {

        // Convert word to a character array
        char[] characters = word.toCharArray();

        // Sort characters in alphabetical order
        for (int i = 0; i < characters.length; i++) {
            for (int j = 1; j < characters.length; j++) {
                if (characters[j] < characters[j - 1]) {
                    char temp = characters[j];
                    characters[j] = characters[j - 1];
                    characters[j - 1] = temp;
                }
            }
        }

        // Recreate word from sorted characters
        word = new String(characters);

        return word;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {

        // Instantiate result
        ArrayList<String> result = new ArrayList<String>();

        // Add one letter to word iteratively
        for (char i = 'a'; i <= 'z'; i++) {

            // Add character
            String newWord = word + i;

            // Sort newWord
            String sortedNewWord = sortLetters(newWord);

            String b = "false";
            if (lettersToWord.containsKey(sortedNewWord)) {
                b = "true";
            }

            // If anagrams exist for sortedNewWord, add them to result
            ArrayList<String> anagrams;
            if (lettersToWord.containsKey(sortedNewWord)) {
                // Anagrams exist
                anagrams = lettersToWord.get(sortedNewWord);
            } else {
                // Anagrams don't exist
                anagrams = new ArrayList<String>();
            }
            for (int x = 0; x < anagrams.size(); x++) {
                // Get word
                String wordToAdd = anagrams.get(x);

                // If word is permissible, add it to result
                if (isGoodWord(wordToAdd, word)) {
                    result.add(wordToAdd);
                }
            }

        }

        return result;
    }

    public String pickGoodStarterWord() {

        // Create empty randomWord
        String randomWord = "";

        // Get randomWord until the word matches criteria
        while (randomWord.length() < DEFAULT_WORD_LENGTH || randomWord.length() > MAX_WORD_LENGTH) {
            randomWord = wordList.get(random.nextInt(wordList.size()));
        }

        // Get anagrams with one more letter of randomWord
        ArrayList<String> anagrams = getAnagramsWithOneMoreLetter(randomWord);

        // If number of anagrams doesn't match criteria, look for another word
        if (anagrams.size() < MIN_NUM_ANAGRAMS) {
            return pickGoodStarterWord();
        } else {
            return randomWord;
        }

    }
    
}
