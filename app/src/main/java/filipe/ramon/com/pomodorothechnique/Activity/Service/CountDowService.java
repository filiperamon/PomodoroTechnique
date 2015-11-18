package filipe.ramon.com.pomodorothechnique.Activity.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by ufc155.barbosa on 18/11/2015.
 */
public class CountDowService extends Service {

    public CountDowService(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
