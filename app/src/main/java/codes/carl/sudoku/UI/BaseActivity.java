package codes.carl.sudoku.UI;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import codes.carl.sudoku.Events.PingAPIEvent;

/**
 * Base Activity Class.
 * <p>
 * Parent class to all classes in the application.
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
    public void pingAPI(PingAPIEvent event){

    }

}
