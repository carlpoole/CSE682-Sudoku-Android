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

    public String error;
    public String id = UUID.randomUUID().toString();
    public Date createDate = new Date();
    public int[][] state;
    public int[][] solution;
    public int[] hintCoords;

    public Puzzle() {}

    public Puzzle(String id, int[][] state, int[][] solution) {
        this.id = id;
        this.state = state;
        this.solution = solution;
    }

    public static ArrayList<Integer> getPuzzleAsArrayList(int[][] puzzle){
        ArrayList<Integer> array = new ArrayList<>();

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                array.add(puzzle[i][j]);
            }
        }

        return array;
    }
}
