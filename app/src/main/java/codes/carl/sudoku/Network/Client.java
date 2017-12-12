package codes.carl.sudoku.Network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import codes.carl.sudoku.Events.PuzzleUploadedEvent;
import codes.carl.sudoku.Model.Puzzle;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Network client class
 * <p>
 * This class manages network connection logic with the Sudoku web service.
 *
 * @author Carl Poole
 */
public class Client {

    /**
     * Log identifier.
     */
    private static final String TAG = "NETWORK";

    /**
     * The base URL for the API connection.
     */
    private static final String BASE_URL = "https://sudoku-dev.herokuapp.com/";

    /**
     * The API interface describing the API routes.
     */
    private API webService;

    /**
     * Singleton instance of Client.
     */
    private static Client instance;

    /**
     * Gets the single instance of this class.
     *
     * @return A single instance of Client.
     */
    public static Client getInstance() {

        if (instance == null) {
            synchronized (Client.class) {
                if (instance == null)
                    instance = new Client();
                instance.initialize();
            }
        }

        return instance;
    }

    /**
     * Setup API connection
     */
    private void initialize() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        webService = retrofit.create(API.class);
    }

    /**
     * Test the API connection.
     */
    public void ping() {
        Call<JsonObject> test = webService.ping();

        test.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                JsonObject body = response.body();

                if (body != null)
                    Log.d(TAG, body.toString());
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }


    public void uploadPuzzle(String imagePath, String userID) {

        File file = new File(imagePath);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        RequestBody user = RequestBody.create(MediaType.parse("text/plain"), userID);

        Call<Puzzle> call = webService.postImage(body, user);
        call.enqueue(new Callback<Puzzle>() {
            @Override
            public void onResponse(@NonNull Call<Puzzle> call, @NonNull Response<Puzzle> response) {

                if (response.isSuccessful()) {
                    // Do awesome stuff
                    Gson gson = new GsonBuilder().create();
                    //int[][] stateArray = response.body().state;
                    EventBus.getDefault().post(new PuzzleUploadedEvent(response.body()));
                    Log.d(TAG, "Success");
                } else if (response.code() == 401) {
                    // Handle unauthorized
                    Log.e(TAG, "Unauthorized");
                } else {
                    // Handle other responses
                    Log.e(TAG, response.code() + ": " + response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Puzzle> call, @NonNull Throwable throwable) {
                Log.e(TAG, "Post fail");
                // Todo: Error request handling
            }
        });



    }

    /**
     * Get the list of puzzles for the provided user.
     *
     * @param userID The ID of the user.
     */
    public void getPuzzles(String userID) {

        Call<List<Puzzle>> call = webService.getPuzzles(userID);

        call.enqueue(new Callback<List<Puzzle>>() {
            @Override
            public void onResponse(@NonNull Call<List<Puzzle>> call, @NonNull Response<List<Puzzle>> response) {
                Log.d(TAG, response.toString());
            }

            @Override
            public void onFailure(@NonNull Call<List<Puzzle>> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }


}
