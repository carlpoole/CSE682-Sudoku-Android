package codes.carl.sudoku;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;

import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by cma on 12/3/17.
 */

public class ImageProcesser {

    /**
     * Log identifier.
     */
    private static final String TAG = "IMGPROC";

    public static void process(Context context, String path){
        //crop();
        enhance(context, path);
    }

    private static void crop(){

    }

    private static void enhance(Context context, String path){
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubfilter(10));
        myFilter.addSubFilter(new ContrastSubfilter(1.5f));

        FileOutputStream out = null;

        try {

            Bitmap inputImage = BitmapFactory.decodeFile(path);
            inputImage = inputImage.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap outputImage = myFilter.processFilter(inputImage);
            out = new FileOutputStream(path);
            outputImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
            Log.d(TAG, "Image enhancement completed");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Image enhancement failed");
        }

    }

}
