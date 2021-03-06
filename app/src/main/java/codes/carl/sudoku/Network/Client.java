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
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import codes.carl.sudoku.Events.PuzzleFailEvent;
import codes.carl.sudoku.Events.PuzzleHintEvent;
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
import retrofit2.converter.scalars.ScalarsConverterFactory;

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

        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
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

        Log.d(TAG, "Uploading puzzle...");
        Call<Puzzle> call = webService.postImage(body, user);
        call.enqueue(new Callback<Puzzle>() {
            @Override
            public void onResponse(@NonNull Call<Puzzle> call, @NonNull Response<Puzzle> response) {
                Gson gson = new GsonBuilder().create();

                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new PuzzleUploadedEvent(response.body()));
                    Log.d(TAG, "Success");
                } else if (response.code() == 500) {
                    try {
                        Puzzle puzzleError = gson.fromJson(response.errorBody().string(), Puzzle.class);
                        Log.e(TAG, puzzleError.error);
                        EventBus.getDefault().post(new PuzzleUploadedEvent(puzzleError));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                EventBus.getDefault().post(new PuzzleFailEvent());
            }
        });

    }

    public void getPuzzleHint(Puzzle currentPuzzle){
        Gson gson = new GsonBuilder().create();

        String requestBody = "{\"state\":"+gson.toJson(currentPuzzle.state)+"}";
        Call<Puzzle> call = webService.getPuzzleHint(requestBody);
        call.enqueue(new Callback<Puzzle>() {
            @Override
            public void onResponse(Call<Puzzle> call, Response<Puzzle> response) {
                if(response.isSuccessful()){
                    EventBus.getDefault().post(new PuzzleHintEvent(response.body()));
                }else{
                    Log.e(TAG, "Request problem - server!");
                }
            }

            @Override
            public void onFailure(Call<Puzzle> call, Throwable t) {
                Log.e(TAG, "Request problem - client!");
            }
        });

    }

}
