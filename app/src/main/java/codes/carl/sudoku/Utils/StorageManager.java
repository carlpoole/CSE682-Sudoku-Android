package codes.carl.sudoku.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import codes.carl.sudoku.Model.Puzzle;
import codes.carl.sudoku.SudokuApplication;

/**
 * Created by carl on 12/11/2017.
 */

public class StorageManager {

    /**
     * Log identifier.
     */
    private static final String TAG = "STORAGE";

    private static final String STORAGE_NAME = "sudoku_storage";

    private static final String PUZZLE_LIST_KEY = "puzzle_list";

    /**
     * Singleton instance of StorageManager.
     */
    private static StorageManager instance;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    /**
     * Gets the single instance of this class.
     *
     * @return A single instance of StorageManager.
     */
    public static StorageManager getInstance(Context context) {

        if (instance == null) {
            synchronized (StorageManager.class) {
                if (instance == null) {
                    instance = new StorageManager();
                    instance.preferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
                }
            }
        }

        return instance;
    }

    public void savePuzzle(Puzzle puzzle) {
        Gson gson = new Gson();
        editor = preferences.edit();

        String puzzleListJson = preferences.getString(PUZZLE_LIST_KEY, "");

        if(puzzleListJson.isEmpty()){
            JsonArray puzzleArray = new JsonArray();
            String puzzleJson = gson.toJson(puzzle);
            JsonObject puzzleJsonObject = gson.fromJson(puzzleJson, JsonObject.class);
            puzzleArray.add(puzzleJsonObject);
            editor.putString(PUZZLE_LIST_KEY, puzzleArray.toString());
            Log.d(TAG,"Saved new puzzle list to storage.");
        }else{
            ArrayList<Puzzle> puzzleArray = gson.fromJson(puzzleListJson, new TypeToken<ArrayList<Puzzle>>(){}.getType());
            puzzleArray.add(puzzle);
            String jsonArray = gson.toJson(puzzleArray);
            editor.putString(PUZZLE_LIST_KEY, jsonArray);
            Log.d(TAG,"Added new puzzle to storage list.");
        }

        editor.apply();
    }

    public ArrayList<Puzzle> getPuzzles() {
        Gson gson = new Gson();
        String puzzleListJson = preferences.getString(PUZZLE_LIST_KEY, "");
        return gson.fromJson(puzzleListJson, new TypeToken<ArrayList<Puzzle>>(){}.getType());
    }


}
