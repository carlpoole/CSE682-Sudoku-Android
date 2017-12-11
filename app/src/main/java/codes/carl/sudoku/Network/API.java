package codes.carl.sudoku.Network;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import codes.carl.sudoku.Model.Puzzle;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("/ping")
    Call<JsonObject> ping();

    @Multipart
    @POST("/puzzles")
    Call<Puzzle> postImage(@Part MultipartBody.Part image, @Part("userId") RequestBody name);

    @GET("/users/{userID}/puzzles")
    Call<List<Puzzle>> getPuzzles(@Path("userID") String userID);

    @GET("/puzzles/{puzzleID}")
    Call<Puzzle> getPuzzleDetails(@Path("puzzleID") String puzzleID);

    @GET("/puzzles/{puzzleID}/hint")
    Call<Puzzle> getPuzzleHint(@Path("puzzleID") String puzzleID);

    @GET("/puzzles/{puzzleID}/solution")
    Call<Puzzle> getPuzzleSolution(@Path("puzzleID") String puzzleID);

}
