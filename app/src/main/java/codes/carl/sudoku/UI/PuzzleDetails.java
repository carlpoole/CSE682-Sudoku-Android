package codes.carl.sudoku.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import codes.carl.sudoku.Model.Puzzle;
import codes.carl.sudoku.R;
import codes.carl.sudoku.Utils.StorageManager;

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

        ArrayList<Integer> array = Puzzle.getPuzzleAsArrayList(puzzle.state);

        adapter = new ArrayAdapter<>(this, R.layout.sudoku_grid_item, array);
        sudokuGrid.setAdapter(adapter);
        solutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                ArrayList<Integer> array = Puzzle.getPuzzleAsArrayList(puzzle.solution);
                                adapter.clear();
                                adapter.addAll(array);
                                adapter.notifyDataSetChanged();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(PuzzleDetails.this);
                builder.setMessage("Are you sure you want to see the solution?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener)
                        .show();

            }
        });
    }
}
