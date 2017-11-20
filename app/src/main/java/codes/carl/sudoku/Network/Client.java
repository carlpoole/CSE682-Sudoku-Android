package codes.carl.sudoku.Network;

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

    API webService;

    public void initialize(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webService = retrofit.create(API.class);
    }


}
