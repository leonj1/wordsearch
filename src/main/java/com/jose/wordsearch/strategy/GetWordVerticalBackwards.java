package com.jose.wordsearch.strategy;

import java.util.ArrayList;
import java.util.List;

import com.jose.wordsearch.Coordinate;
import com.jose.wordsearch.Letter;
import com.jose.wordsearch.Range;

/**
 * Created by jose on 3/2/16.
 */
public class GetWordVerticalBackwards implements Strategy {
    char[][] board;
    int squareSize;
    int colPos;
    int rowPos;
    int numberOfLetters;
    int currentColPos;
    int currentRowPos;

    public GetWordVerticalBackwards(char[][] board, int colPos, int rowPos, int numberOfLetters){
        this.board = board;
        this.colPos = colPos;
        this.rowPos = rowPos;
        this.numberOfLetters = numberOfLetters;
        this.squareSize = board[0].length;
    }

    @Override
    public Letter[] execute() {
        int numberOfLettersRetrieved = 0;
        List<Letter> letters = new ArrayList<>();

        currentColPos = this.colPos;
        currentRowPos = this.rowPos;

        while (numberOfLettersRetrieved != this.numberOfLetters) {
            Letter letter = new Letter(board[currentColPos][currentRowPos], currentRowPos+1, currentColPos+1);
            letters.add(letter);

            moveOne();

            numberOfLettersRetrieved++;
        }

        Letter[] result = new Letter[letters.size()];
        letters.toArray(result);

        return result;
    }

    @Override
    public boolean inRange(Range range, Coordinate coordinate, int squareSize) {
        Coordinate startPos = range.getStartPos();
        Coordinate endPos = range.getEndPos();

        currentColPos = startPos.getColPos();
        currentRowPos = startPos.getRowPos();

        boolean foundMatchingCoordinate = false;
        boolean foundEndOfRange = false;

        while(!foundMatchingCoordinate && !foundEndOfRange) {
            Coordinate coordinateUnderTest = new Coordinate(currentColPos, currentRowPos);
            if(coordinate.equals(coordinateUnderTest)) {
                foundMatchingCoordinate = true;
            }

            if (coordinateUnderTest.equals(endPos) ||
                    coordinateUnderTest.equals(startPos)) {
                foundEndOfRange = true;
            }

            moveOne();
        }

        return foundMatchingCoordinate;
    }

    private void moveOne() {
        if (currentColPos == 0) {
            currentColPos = squareSize-1;
        } else {
            currentColPos--;
        }
    }
}
