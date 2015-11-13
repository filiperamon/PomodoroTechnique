package filipe.ramon.com.pomodorothechnique.Activity.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import filipe.ramon.com.pomodorothechnique.Activity.Util.MyCountDownTimer;
import filipe.ramon.com.pomodorothechnique.R;

public class TimerActivity extends AppCompatActivity {

    private MyCountDownTimer timer;
    private TextView chronometro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        chronometro = (TextView) findViewById(R.id.chronometro);
        if (timer == null) {
            timer = new MyCountDownTimer(this, chronometro, 25 * 60 * 1000, 1000, null,0);
            timer.start();
        }
    }
}
