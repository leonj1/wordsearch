package com.jose.wordsearch;

import com.jose.wordsearch.strategy.GetWordDiagonalBottomLeftTopRight;
import com.jose.wordsearch.strategy.GetWordDiagonalBottomRightTopLeft;
import com.jose.wordsearch.strategy.GetWordDiagonalTopLeftBottomRight;
import com.jose.wordsearch.strategy.GetWordDiagonalTopRightBottomLeft;
import com.jose.wordsearch.strategy.GetWordHorizontalBackwards;
import com.jose.wordsearch.strategy.GetWordHorizontalForward;
import com.jose.wordsearch.strategy.GetWordVerticalBackwards;
import com.jose.wordsearch.strategy.GetWordVerticalForwards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Programmatically play the word search game
 * Created by Jose Leon on 3/2/16.
 */
public class Solution {
    static private Map<String, Range> puzzleResults = new HashMap<>();;
    static private char[][] board;

    public static void main(String[] args) {

        // Because capital letters are easier to read
        String puzzle =
                "B E I E L P I T L U M\n" +
                "R S P E C I A L M C D\n" +
                "R R U N C O M M O N A\n" +
                "S E A N A M E N D T S\n" +
                "E V T L D O T S I C S\n" +
                "G I F L U R A D F N O\n" +
                "N D F E A G Y D Y I R\n" +
                "A D E S U F N O C T T\n" +
                "R A T M O S A I C S E\n" +
                "T T E T A R A P S I D\n" +
                "S H E E T A I V E D R\n";

        // making lower case since that's whats in the requirements
        puzzle = puzzle.toLowerCase();

        String[] linesInPuzzle = puzzle.split("\n");
        // TODO: Verify puzzle meets criteria - should puzzle be a square? Assume so for now

        // Lets figure out the size of the puzzle from the length of the first line since, for now, we expect a square puzzle
        String[] line1 = linesInPuzzle[0].split(" ");
        int squareSize = line1.length;
        board = new char[squareSize][squareSize];

        // Put the Puzzle string into a char array or arrays
        for(int i=0; i<linesInPuzzle.length;i++) {
            line1 = linesInPuzzle[i].split(" ");
            for(int j=0; j<squareSize; j++) {
                board[i][j] = line1[j].charAt(0);
            }
        }

        // A set of words to search
        // helps us test each scenario; this would typically be in unit tests, but short on time
        List<String> words = new ArrayList<>();
        words.add("pit");   // horizontal check
        words.add("assorted");  // vertical check
        words.add("confuse");   // this should not use : confuse [9, 8] -> [3, 8]
        words.add("confused");
//        words.add("contrast");
//        words.add("deviate");
//        words.add("disparate");
//        words.add("diverse");
        words.add("distinct");  // vertical backwards check
//        words.add("mosaics");
//        words.add("multiple");
        words.add("singular");  // diagonal backwards check
//        words.add("special");
//        words.add("strange");
//        words.add("uncommon");
        words.add("sun");   // diagonal forward test
        words.add("doesNotExist");
        words.add("rpes"); // should wrao diagonally forward

        // Start looking for words in the puzzle
        for(String word : words) {
            // Start searching the puzzle row by row
            for(int i=0; i<squareSize; i++) {
                // Now searching column by column
                for(int j=0; j<squareSize; j++) {
                    // Look for the word once per strategy at each coordinate
                    // Strategies do NOT search for the word. They simply return a string from the puzzle of word.length
                    Letter[] result = new GetWordHorizontalForward(board, i,j, word.length()).execute();
                    // search() is where we determine if the returned word matches the word we're searching for.
                    search(result, word);
                    result = new GetWordHorizontalBackwards(board, i,j,word.length()).execute();
                    search(result, word);
                    result = new GetWordVerticalForwards(board, i,j,word.length()).execute();
                    search(result, word);
                    result = new GetWordVerticalBackwards(board, i,j,word.length()).execute();
                    search(result, word);
                    result = new GetWordDiagonalTopLeftBottomRight(board, i,j,word.length()).execute();
                    search(result, word);
                    result = new GetWordDiagonalBottomRightTopLeft(board, i,j,word.length()).execute();
                    search(result, word);
                    result = new GetWordDiagonalBottomLeftTopRight(board, i,j,word.length()).execute();
                    search(result, word);
                    result = new GetWordDiagonalTopRightBottomLeft(board, i,j,word.length()).execute();
                    search(result, word);
                }
            }
        }

        // Now back fill any words that where not found - surely there's a more elegant way to do this
        for(String word : words) {
            if (!puzzleResults.containsKey(word)) {
                puzzleResults.put(word, null);
            }
        }

        System.out.println("Puzzle Results");
        for (Map.Entry<String, Range> entry : puzzleResults.entrySet()) {
            String key = entry.getKey();
            Range value = entry.getValue();

            String locationFound;
            if (value == null) {
                locationFound = "Not Found";
            } else {
                locationFound = String.format("%s -> %s", value.getStartPos(), value.getEndPos());
            }

            System.out.println(String.format("%s %s", key, locationFound));
        }
    }

    private static void search(Letter[] result, String word) {
        if (checkResult(result, word)) {
            // get the coordinate in the puzzle of the first letter in the word
            Coordinate firstLetter = new Coordinate(result[0].colPos, result[0].rowPos);
            // get the coordinate in the puzzle of the last letter in the word
            Coordinate lastLetter = new Coordinate(result[result.length - 1].colPos, result[result.length - 1].rowPos);
            puzzleResults.put(word, new Range(firstLetter, lastLetter));
            //found, so break out of this loop looking for the word using alternate strategies - really?
        }
    }

    private static boolean checkResult(Letter[] input, String word) {
        StringBuilder sb = new StringBuilder();
        for (Letter letter : input) {
            sb.append(letter.toString());
        }

        return sb.toString().equals(word);
    }
}
