package codes.carl.sudoku.Events;

import codes.carl.sudoku.Model.Puzzle;

/**
 * Puzzle Hint Event Object Class
 * <p>
 * Facilitates the puzzle-hint event with the Event Bus to facilitate the
 * handling of puzzle data after a hint is received from the API.
 *
 * @author Carl Poole
 * @see org.greenrobot.eventbus.EventBus
 */
public class PuzzleHintEvent {

    public Puzzle puzzle;

    public PuzzleHintEvent(Puzzle puzzle) {
        this.puzzle = puzzle;
    }
}
