package codes.carl.sudoku.Events;

/**
 * Created by cma on 12/10/17.
 */

public class PuzzleUploadedEvent {

    public int[][] puzzleState;

    public PuzzleUploadedEvent(int[][] puzzleState) {
        this.puzzleState = puzzleState;
    }
}
