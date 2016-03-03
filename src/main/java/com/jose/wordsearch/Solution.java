package com.jose.wordsearch;

import com.jose.wordsearch.strategy.GetWordDiagonalBottomLeftTopRight;
import com.jose.wordsearch.strategy.GetWordDiagonalBottomRightTopLeft;
import com.jose.wordsearch.strategy.GetWordDiagonalTopLeftBottomRight;
import com.jose.wordsearch.strategy.GetWordDiagonalTopRightBottomLeft;
import com.jose.wordsearch.strategy.GetWordHorizontalBackwards;
import com.jose.wordsearch.strategy.GetWordHorizontalForward;
import com.jose.wordsearch.strategy.GetWordVerticalBackwards;
import com.jose.wordsearch.strategy.GetWordVerticalForwards;
import com.jose.wordsearch.strategy.Strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Programmatically play the word search game
 * Created by Jose Leon on 3/2/16.
 *
 * Write a function that takes a word and a grid of letters (the word search puzzle).
 This function should return the start position and end position of the word in the word search puzzle.

 The word should never be empty.
 The puzzle should never be empty.
 There should only be letter a-z in both the grid and the word.
 The word may not exist in the puzzle.
 The word can be in the grid horizontal, vertical, or diagonal. The word can be forwards or backwards in the grid. The word may wrap around the puzzle.
 The word cannot use any position in the grid twice.
 */
public class Solution {
//    static private Map<String, Range> puzzleResults = new HashMap<>();
    static private Map<String, List<Range>> puzzleResults = new HashMap<>();

    static private char[][] board;
    static int squareSize;

    public static void main(String[] args) {

        // Because capital letters are easier to read
        // Puzzle source from: http://codereview.stackexchange.com/questions/81790/java-word-search-solver
        // Solution is all original. Mine.
        String puzzle =
                "B E I E L P I T L U M X X\n" +
                "R S P E C I A L M C D X X\n" +
                "R R U N C O M M O N A X X\n" +
                "S E A N A M E N D T S X X\n" +
                "E V T L D O T S I C S X X\n" +
                "G I F L U R A D F N O X X\n" +
                "N D F E A G Y D Y I R X X\n" +
                "A D E S U F N O C T T X X\n" +
                "R A T M O S A I C S E X X\n" +
                "T T E T A R A P S I D X X\n" +
                "S H E E T A I V E D R X X\n" +
                "H A H A H A H A H A H X X\n" +
                "H A H A H A H A H A H X X\n";

        // making lower case since that's whats in the requirements
        puzzle = puzzle.toLowerCase();

        String[] linesInPuzzle = puzzle.split("\n");
        // TODO: Verify puzzle meets criteria - should puzzle be a square? Assume so for now

        // Lets figure out the size of the puzzle from the length of the first line since, for now, we expect a square puzzle
        String[] line1 = linesInPuzzle[0].split(" ");
        squareSize = line1.length;
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
        words.add("confuse");   // this should not be found since there are dupe positions with confused
        words.add("confused");  // vertical check
        words.add("distinct");  // vertical backwards check
        words.add("singular");  // diagonal backwards check
        words.add("sun");       // diagonal forward test
        words.add("doesNotExist");  // should show as Not Found
        words.add("rpes");      // should wrao diagonally forward
        words.add("haha");      // next two should not have the same coordinates
        words.add("ha");        // if these two are switched order, there's an edge case that needs sorting

        // Start looking for words in the puzzle
        for(String word : words) {
            // Start searching the puzzle row by row
            for(int i=0; i<squareSize; i++) {
                // Now searching column by column
                for(int j=0; j<squareSize; j++) {
                    // Look for the word once per strategy at each coordinate
                    // Strategies do NOT search for the word. They simply return a string from the puzzle of word.length
                    applyStrategy(new GetWordHorizontalForward(board, i, j, word.length()), word);
                    applyStrategy(new GetWordHorizontalBackwards(board, i, j, word.length()), word);
                    applyStrategy(new GetWordVerticalForwards(board, i, j, word.length()), word);
                    applyStrategy(new GetWordVerticalBackwards(board, i, j, word.length()), word);
                    applyStrategy(new GetWordDiagonalTopLeftBottomRight(board, i, j, word.length()), word);
                    applyStrategy(new GetWordDiagonalBottomRightTopLeft(board, i, j, word.length()), word);
                    applyStrategy(new GetWordDiagonalBottomLeftTopRight(board, i,j,word.length()), word);
                    applyStrategy(new GetWordDiagonalTopRightBottomLeft(board, i, j, word.length()), word);
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
        for (Map.Entry<String, List<Range>> entry : puzzleResults.entrySet()) {
            String key = entry.getKey();
            List<Range> value = entry.getValue();

            String locationFound;
            if (value == null) {
                locationFound = "Not Found";
            } else {
                StringBuilder sb = new StringBuilder();
                for(Range range : value) {
                    sb.append(String.format("%s -> %s, ", range.getStartPos(), range.getEndPos()));
                }
                locationFound = sb.toString();
            }

            System.out.println(String.format("%s %s", key, locationFound));
        }
    }

    private static void applyStrategy(Strategy strategy, String word) {
        Letter[] result = strategy.execute();
        search(result, word, strategy);
    }

    private static void search(Letter[] result, String word, Strategy strategy) {
        if (checkResult(result, word)) {
            // get the coordinate in the puzzle of the first letter in the word
            Coordinate firstLetter = new Coordinate(result[0].colPos, result[0].rowPos);
            // get the coordinate in the puzzle of the last letter in the word
            Coordinate lastLetter = new Coordinate(result[result.length - 1].colPos, result[result.length - 1].rowPos);

            Range foundRange = new Range(firstLetter, lastLetter);

            List<Range> coordinates;
            if (!puzzleResults.containsKey(word)) {
                coordinates = new ArrayList<> ();
            } else {
                coordinates = puzzleResults.get(word);
            }

            // Only add the Range if no other word already claimed it
            // TODO: This should really live somewhere else since this method is now doing too much; out of time to refactor
            Collection<List<Range>> allFoundCoordinates = puzzleResults.values();
            boolean alreadyExists = false;
            for (List<Range> rangeList : allFoundCoordinates) {
                for(Range range : rangeList) {
                    if (range.isWithinRange(strategy, foundRange.getStartPos(), squareSize)) {
                        alreadyExists = true;
                    }
                }
            }

            if (!alreadyExists) {
                coordinates.add(foundRange);
                puzzleResults.put(word, coordinates);
            }
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
