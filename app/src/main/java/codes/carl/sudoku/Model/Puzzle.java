package codes.carl.sudoku.Model;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Puzzle Model Class
 * <p>
 * Represents the puzzle details for a single sudoku puzzle.
 *
 * @author Carl Poole
 */
@Parcel
public class Puzzle {

    /**
     * Error message if an error is returned from the API.
     */
    public String error;

    /**
     * A unique ID for the puzzle.
     */
    public String id = UUID.randomUUID().toString();

    /**
     * The date and time the puzzle was created.
     */
    public Date createDate = new Date();

    /**
     * A snapshot state of the puzzle contents.
     */
    public int[][] state;

    /**
     * The solved contents of the puzzle.
     */
    public int[][] solution;

    /**
     * Coordinates of the location of a hint square if provided by the API.
     */
    public int[] hintCoords;

    /**
     * Coordinates of the location of an error square if provided by the API.
     */
    public int[] errorCoords;

    /**
     * Constructs an empty Puzzle object.
     */
    public Puzzle() {}

    /**
     * Constructs a new Puzzle with minimal information.
     *
     * @param id a unique ID.
     * @param state the state of the puzzle.
     * @param solution the puzzle solution.
     */
    public Puzzle(String id, int[][] state, int[][] solution) {
        this.id = id;
        this.state = state;
        this.solution = solution;
    }

    /**
     * Converts the multidimensional array format of the puzzle contents to
     * a flattened ArrayList of Integers from 0 - 80.
     *
     * @param puzzle the puzzle state to convert.
     * @return the flattened ArrayList of puzzle values.
     */
    public static ArrayList<Integer> getPuzzleAsArrayList(int[][] puzzle) {
        ArrayList<Integer> array = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                array.add(puzzle[i][j]);
            }
        }

        return array;
    }
}
