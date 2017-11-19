package codes.carl.sudoku;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.provider.Settings;

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

    /**
     * Gets a unique identity for the device running the application.
     *
     * @param context
     * @return
     */
    public String getIdentity(Context context){

        @SuppressLint("HardwareIds")
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        return androidId;
    }


}
