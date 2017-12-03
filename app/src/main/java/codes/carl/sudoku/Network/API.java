package codes.carl.sudoku.Network;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import codes.carl.sudoku.Model.Puzzle;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Network Interface.
 * <p>
 * This interface models the API connections the Client class makes.
 *
 * @author Carl Poole
 * @see Client
 */
public interface API {

    @GET("/puzzles")
    Call<JsonObject> getTest();

    @GET("/puzzles/{user}")
    Call<List<Puzzle>> getPuzzles(@Path("user") String userID);


}
