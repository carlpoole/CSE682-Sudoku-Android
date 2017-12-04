package codes.carl.sudoku.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import codes.carl.sudoku.Network.Client;
import codes.carl.sudoku.R;
import codes.carl.sudoku.SudokuApplication;


/**
 * Puzzle List Activity
 * <p>
 * List the sudoku puzzles added by the user.
 *
 * @author Carl Poole
 */
public class PuzzleList extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newPuzzleIntent = new Intent(PuzzleList.this, NewPuzzle.class);
                startActivity(newPuzzleIntent);

            }
        });

        Client client = Client.getInstance();
        client.test();
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
}
