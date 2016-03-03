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

    /**
     * Determine if the provided coordinate is within this range
     * @param coordinate    the coordinate we are interested in checking
     * @param squareSize    after thought - we need to know the max size of the puzzle to handle wrapping; FIXME!
     * @return              a boolean if within this range or not
     */
    public boolean isWithinRange(Coordinate coordinate, int squareSize) {
        boolean isWithinRange = false;

        // TODO: Out of time - Range should probably also include a variable indicating direction (e.g. TopLeftBottomRight)
        // that's the only way we're going to effectively determine which search strategy to implement since having the start + end
        // positions is not sufficient.

        return false;   // dummy value
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
