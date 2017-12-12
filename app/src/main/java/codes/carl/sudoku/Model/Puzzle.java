package codes.carl.sudoku.Model;

import org.parceler.Parcel;

/**
 * Puzzle Model Class
 * <p>
 * Represents the puzzle details for a single sudoku puzzle.
 *
 * @author Carl Poole
 */
@Parcel
public class Puzzle {

    public String id;
    public String createDate;
    public int[][] state;
    public int[][] solution;

    public Puzzle() {}

    public Puzzle(String id, String createDate, int[][] state, int[][] solution) {
        this.id = id;
        this.createDate = createDate;
        this.state = state;
        this.solution = solution;
    }
}
