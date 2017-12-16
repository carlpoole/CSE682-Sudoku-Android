package codes.carl.sudoku.Network;

import com.google.gson.JsonObject;

import codes.carl.sudoku.Model.Puzzle;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @Headers("Content-Type: application/json")
    @POST("/puzzles/hints")
    Call<Puzzle> getPuzzleHint(@Body String jsonBody);

}
