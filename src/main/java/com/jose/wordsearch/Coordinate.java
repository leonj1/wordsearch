package com.jose.wordsearch;

/**
 * Created by jose on 3/2/16.
 */
class Coordinate {
    int colPos;
    int rowPos;

    public Coordinate(int colPos, int rowPos) {
        this.colPos = colPos;
        this.rowPos = rowPos;
    }

    public int getColPos() {
        return colPos;
    }

    public int getRowPos() {
        return rowPos;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", this.colPos, this.rowPos);
    }
}