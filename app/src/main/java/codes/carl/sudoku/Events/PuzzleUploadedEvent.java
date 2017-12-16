package codes.carl.sudoku.Events;

import codes.carl.sudoku.Model.Puzzle;

/**
 * Puzzle Uploaded Event Object Class
 * <p>
 * Facilitates the puzzle-uploaded event with the Event Bus after the puzzle has
 * been uploaded to the API and a puzzle has been received.
 *
 * @author Carl Poole
 * @see org.greenrobot.eventbus.EventBus
 */
public class PuzzleUploadedEvent {

    public Puzzle puzzle;

    public PuzzleUploadedEvent(Puzzle puzzle) {
        this.puzzle = puzzle;
    }
}
