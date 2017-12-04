package codes.carl.sudoku.UI;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import codes.carl.sudoku.Events.PuzzleCapturedEvent;
import codes.carl.sudoku.ImageProcesser;

/**
 * Created by cma on 12/3/17.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void processImage(PuzzleCapturedEvent event){
        ImageProcesser.process(this, event.imagePath);
    }

}
