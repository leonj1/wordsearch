package com.jose.wordsearch;

/**
 * Created by jose on 3/2/16.
 */
class Range {
    Coordinate startPos;
    Coordinate endPos;

    public Range(Coordinate start, Coordinate end) {
        this.startPos = start;
        this.endPos = end;
    }

    public Coordinate getStartPos() {
        return startPos;
    }

    public Coordinate getEndPos() {
        return endPos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range range = (Range) o;

        if (startPos != null ? !startPos.equals(range.startPos) : range.startPos != null) return false;
        return !(endPos != null ? !endPos.equals(range.endPos) : range.endPos != null);

    }

    @Override
    public int hashCode() {
        int result = startPos != null ? startPos.hashCode() : 0;
        result = 31 * result + (endPos != null ? endPos.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Range{" +
                "startPos=" + startPos +
                ", endPos=" + endPos +
                '}';
    }
}
