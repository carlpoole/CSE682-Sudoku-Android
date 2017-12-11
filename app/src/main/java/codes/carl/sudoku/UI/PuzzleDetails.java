package codes.carl.sudoku.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Arrays;

import codes.carl.sudoku.R;

public class PuzzleDetails extends BaseActivity {

    GridView sudokuGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_details);
        sudokuGrid = findViewById(R.id.sudoku_grid);

        Intent intent = getIntent();
        int[][] puzzleState = (int[][]) intent.getSerializableExtra("puzzle_state");

        Integer[] array = new Integer[81];
        int k=0;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                array[k++]= puzzleState[i][j];
            }
        }

        sudokuGrid.setAdapter(new ArrayAdapter<Integer>(this, R.layout.sudoku_grid_item, array));
    }
}
