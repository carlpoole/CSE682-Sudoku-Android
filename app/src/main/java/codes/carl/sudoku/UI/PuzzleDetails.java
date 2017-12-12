package codes.carl.sudoku.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.io.Serializable;
import java.util.Arrays;

import codes.carl.sudoku.Model.Puzzle;
import codes.carl.sudoku.R;

public class PuzzleDetails extends BaseActivity {

    GridView sudokuGrid;
    ArrayAdapter<Integer> adapter;
    Button solutionButton;
    Button hintButton;
    Puzzle puzzle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_details);
        sudokuGrid = findViewById(R.id.sudoku_grid);
        solutionButton = findViewById(R.id.solve_button);
        hintButton = findViewById(R.id.hint_button);

        puzzle = Parcels.unwrap(getIntent().getParcelableExtra("puzzle"));

        int[][] puzzleState = puzzle.state;

        Integer[] array = new Integer[81];
        int k=0;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                array[k++]= puzzleState[i][j];
            }
        }

        adapter = new ArrayAdapter<Integer>(this, R.layout.sudoku_grid_item, array);
        sudokuGrid.setAdapter(adapter);
        solutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[][] puzzleState = puzzle.solution;

                Integer[] array = new Integer[81];
                int k=0;
                for(int i = 0; i < 9; i++){
                    for(int j = 0; j < 9; j++){
                        array[k++]= puzzleState[i][j];
                    }
                }

                adapter = new ArrayAdapter<Integer>(PuzzleDetails.this, R.layout.sudoku_grid_item, array);
                sudokuGrid.setAdapter(adapter);

            }
        });
    }
}
