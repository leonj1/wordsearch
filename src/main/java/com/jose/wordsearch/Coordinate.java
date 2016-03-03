package com.jose.wordsearch;

/**
 * Created by jose on 3/2/16.
 */
public class Coordinate {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (colPos != that.colPos) return false;
        return rowPos == that.rowPos;

    }

    @Override
    public int hashCode() {
        int result = colPos;
        result = 31 * result + rowPos;
        return result;
    }
}