package com.jose.wordsearch.strategy;

import com.jose.wordsearch.Letter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 3/2/16.
 */
public class GetWordDiagonalBottomRightTopLeft implements Strategy {
    char[][] board;
    int squareSize;
    int colPos;
    int rowPos;
    int numberOfLetters;

    public GetWordDiagonalBottomRightTopLeft(char[][] board, int colPos, int rowPos, int numberOfLetters){
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

        int currentColPos = this.colPos;
        int currentRowPos = this.rowPos;

        while (numberOfLettersRetrieved != this.numberOfLetters) {
            Letter letter = new Letter(board[currentColPos][currentRowPos], currentRowPos+1, currentColPos+1);
            letters.add(letter);

            if (currentRowPos == 0) {
                currentRowPos = squareSize-1;
            } else {
                currentRowPos--;
            }

            if (currentColPos == 0) {
                currentColPos = squareSize-1;
            } else {
                currentColPos--;
            }

            numberOfLettersRetrieved++;
        }

        Letter[] result = new Letter[letters.size()];
        letters.toArray(result);

        return result;
    }
}
