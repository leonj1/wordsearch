package com.jose.wordsearch;

/**
 * Created by jose on 3/2/16.
 */
public class Letter {
    char letter;
    int colPos;
    int rowPos;

    public Letter(char letter, int colPos, int rowPos) {
        this.letter = letter;
        this.colPos = colPos;
        this.rowPos = rowPos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Letter letter = (Letter) o;

        if (colPos != letter.colPos) return false;
        return rowPos == letter.rowPos;

    }

    @Override
    public int hashCode() {
        int result = colPos;
        result = 31 * result + rowPos;
        return result;
    }

    @Override
    public String toString() {
        return Character.toString(this.letter);
    }
}
