package com.jose.wordsearch.strategy;

import com.jose.wordsearch.Coordinate;
import com.jose.wordsearch.Letter;
import com.jose.wordsearch.Range;

/**
 * Created by jose on 3/2/16.
 */
public interface Strategy {
    Letter[] execute();
    boolean inRange(Range range, Coordinate coordinate, int squareSize);
}
