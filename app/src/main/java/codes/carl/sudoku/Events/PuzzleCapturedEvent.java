package codes.carl.sudoku.Events;

import android.net.Uri;

/**
 * Created by cma on 12/3/17.
 */

public class PuzzleCapturedEvent {

    public String imagePath;

    public PuzzleCapturedEvent(String imagePath) {
        this.imagePath = imagePath;
    }
}
