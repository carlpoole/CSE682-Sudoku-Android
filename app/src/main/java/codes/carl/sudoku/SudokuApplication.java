package codes.carl.sudoku;

import android.app.Application;

/**
 * Sudoku Application Singleton Class
 * <p>
 * This class contains application level logic.
 *
 * @author Carl Poole
 */
public class SudokuApplication extends Application {

    /**
     * Singleton instance of SudokuApplication.
     */
    private static SudokuApplication instance;

    /**
     * Gets the single instance of this class.
     *
     * @return A single instance of SudokuApplication.
     */
    public static SudokuApplication getInstance() {

        if (instance == null) {
            synchronized (SudokuApplication.class) {
                if (instance == null)
                    instance = new SudokuApplication();
            }
        }

        return instance;
    }

    private SudokuApplication() {

    }


}
