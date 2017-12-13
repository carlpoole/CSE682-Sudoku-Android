package codes.carl.sudoku.Events;

import codes.carl.sudoku.Model.Puzzle;

/**
 * Created by carl on 12/13/2017.
 */

public class PuzzleHintEvent {

    public Puzzle puzzle;

    public PuzzleHintEvent(Puzzle puzzle) {
        this.puzzle = puzzle;
    }
}
