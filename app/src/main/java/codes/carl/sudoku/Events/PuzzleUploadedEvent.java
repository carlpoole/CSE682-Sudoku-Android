package codes.carl.sudoku.Events;

import codes.carl.sudoku.Model.Puzzle;

/**
 * Created by cma on 12/10/17.
 */

public class PuzzleUploadedEvent {

    public Puzzle puzzle;

    public PuzzleUploadedEvent(Puzzle puzzle) {
        this.puzzle = puzzle;
    }
}
