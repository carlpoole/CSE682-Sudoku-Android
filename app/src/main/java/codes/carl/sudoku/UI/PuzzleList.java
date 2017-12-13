package codes.carl.sudoku.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import codes.carl.sudoku.Model.Puzzle;
import codes.carl.sudoku.Network.Client;
import codes.carl.sudoku.R;
import codes.carl.sudoku.SudokuApplication;
import codes.carl.sudoku.Utils.StorageManager;


/**
 * Puzzle List Activity
 * <p>
 * List the sudoku puzzles added by the user.
 *
 * @author Carl Poole
 */
public class PuzzleList extends BaseActivity {

    RecyclerView puzzleList;
    PuzzleListAdapter adapter;
    ArrayList<Puzzle> puzzles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newPuzzleIntent = new Intent(PuzzleList.this, NewPuzzle.class);
                startActivity(newPuzzleIntent);

            }
        });

        Client client = Client.getInstance();

        puzzleList = findViewById(R.id.puzzle_list);
        puzzleList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PuzzleListAdapter(this);

        adapter.setClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(PuzzleList.this, PuzzleDetails.class);
                intent.putExtra("puzzle", Parcels.wrap(puzzles.get(position)));

                startActivity(intent);
            }
        });
        puzzleList.setAdapter(adapter);

        client.ping();
    }

    @Override
    protected void onResume() {
        super.onResume();

        puzzles = StorageManager.getInstance(this).getPuzzles();
        Collections.sort(puzzles, new Comparator<Puzzle>() {
            @Override
            public int compare(Puzzle puzzle1, Puzzle puzzle2) {

                if(puzzle1.createDate.before(puzzle2.createDate))
                    return 1;

                if(puzzle1.createDate.after(puzzle2.createDate))
                    return -1;


                return 0;
            }
        });

        Log.d("LIST", "Loaded Puzzles.");
        adapter.data = puzzles;
        adapter.notifyDataSetChanged();
    }

    class PuzzleListAdapter extends RecyclerView.Adapter<PuzzleListAdapter.ViewHolder>{

        private ArrayList<Puzzle> data;
        private LayoutInflater inflater;
        private ItemClickListener clickListener;

        public PuzzleListAdapter(Context context){
            this.inflater = LayoutInflater.from(context);
        }

        public PuzzleListAdapter(Context context, ArrayList<Puzzle> data){
            this.inflater = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.puzzle_list_row, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Puzzle puzzle = data.get(position);
            SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format), Locale.US);
            String dateAsString = dateFormat.format(puzzle.createDate);
            holder.puzzleDate.setText(String.format("%s %s", getString(R.string.puzzle_from), dateAsString));
        }

        @Override
        public int getItemCount() {
            return data != null ? data.size() : 0;
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public TextView puzzleDate;

            public ViewHolder(View itemView) {
                super(itemView);
                puzzleDate = itemView.findViewById(R.id.puzzle_date);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_puzzle_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            SudokuApplication.getInstance().displayAboutDialog(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
