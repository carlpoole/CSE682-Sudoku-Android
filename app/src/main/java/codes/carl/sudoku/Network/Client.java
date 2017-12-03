package codes.carl.sudoku.Network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import codes.carl.sudoku.Model.Puzzle;
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

    private static final String TAG = "NETWORK";
    private static final String BASE_URL = "https://guarded-atoll-98665.herokuapp.com/";

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

    private void initialize() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webService = retrofit.create(API.class);
    }

    public void test() {
        Call<JsonObject> test = webService.getTest();

        test.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }

    public void getPuzzles(String userID) {

        Call<List<Puzzle>> call = webService.getPuzzles(userID);

        call.enqueue(new Callback<List<Puzzle>>() {
            @Override
            public void onResponse(Call<List<Puzzle>> call, Response<List<Puzzle>> response) {
                Log.d(TAG, response.toString());
            }

            @Override
            public void onFailure(Call<List<Puzzle>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }


}
