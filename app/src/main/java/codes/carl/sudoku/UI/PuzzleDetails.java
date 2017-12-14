package codes.carl.sudoku.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.parceler.Parcels;

import java.util.ArrayList;

import codes.carl.sudoku.Events.PuzzleHintEvent;
import codes.carl.sudoku.Model.Puzzle;
import codes.carl.sudoku.Network.Client;
import codes.carl.sudoku.R;

public class PuzzleDetails extends BaseActivity {

    GridView sudokuGrid;
    SudokuGridAdapter adapter;
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

        adapter = new SudokuGridAdapter(this, array);
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
                                adapter.setData(array);
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

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Client.getInstance().getPuzzleHint(puzzle);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPuzzleHintUpdate(PuzzleHintEvent event) {

        // Clear selection on previous hint if applicable
        if (puzzle.hintCoords != null)
            sudokuGrid.getChildAt((puzzle.hintCoords[0] * 9) + puzzle.hintCoords[1]).setBackground(null);


        // Carry solution and date from previous puzzle
        event.puzzle.solution = puzzle.solution;
        event.puzzle.createDate = puzzle.createDate;

        // Replace current puzzle with new puzzle object
        puzzle = event.puzzle;

        // Update puzzle values in the grid on the screen
        ArrayList<Integer> array = Puzzle.getPuzzleAsArrayList(puzzle.state);
        adapter.setData(array);

        // Highlight the cell of the hint we just got
        sudokuGrid.getChildAt((puzzle.hintCoords[0] * 9) + puzzle.hintCoords[1])
                .setBackgroundResource(R.drawable.grid_selected_rectangle);
        adapter.notifyDataSetChanged();
    }

    class SudokuGridAdapter extends BaseAdapter {

        ArrayList<Integer> data;
        LayoutInflater inflater;

        SudokuGridAdapter(Context context, ArrayList<Integer> data) {
            inflater = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setData(ArrayList<Integer> data) {
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.sudoku_grid_item, parent, false);
            }

            //viewHolder = new ViewHolder(convertView);
            Integer value = data.get(position);

            TextView sudokuValue = convertView.findViewById(R.id.grid_cell);
            sudokuValue.setText(String.valueOf(value));

            if (value == 0) {
                sudokuValue.setTextColor(getResources().getColor(android.R.color.transparent));
            }else{
                sudokuValue.setTextColor(getResources().getColor(android.R.color.black));
            }

            return convertView;
        }
    }

}
