package codes.carl.sudoku.Events;

import android.net.Uri;

/**
 * Puzzle Captured Event Object Class
 * <p>
 * Facilitates the puzzle-captured event with the Event Bus after the user
 * takes a picture of the puzzle they are working with.
 *
 * @author Carl Poole
 * @see org.greenrobot.eventbus.EventBus
 */
public class PuzzleCapturedEvent {

    public String imagePath;

    public PuzzleCapturedEvent(String imagePath) {
        this.imagePath = imagePath;
    }
}
