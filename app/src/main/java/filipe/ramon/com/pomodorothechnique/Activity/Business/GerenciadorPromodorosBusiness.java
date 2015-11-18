package filipe.ramon.com.pomodorothechnique.Activity.Business;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import filipe.ramon.com.pomodorothechnique.Activity.Dao.PomodoroDao;
import filipe.ramon.com.pomodorothechnique.Activity.Entity.Pomodoro;
import filipe.ramon.com.pomodorothechnique.Activity.Util.MyCountDownTimer;
import filipe.ramon.com.pomodorothechnique.R;

/**
 * Created by ufc155.barbosa on 10/11/2015.
 */
public class GerenciadorPromodorosBusiness {

    private MyCountDownTimer timer;
    private TextView chronometro;
    private Context context;
    private PomodoroDao pomodoroDao;

    public GerenciadorPromodorosBusiness(Context context) {
        this.context = context;
        pomodoroDao = new PomodoroDao(context);
    }


    //CRUD
    public void insertPomodoro(Pomodoro pomodoro) {
        pomodoroDao.setPomodoro(pomodoro);
    }

    public List<Pomodoro> getTodosPomodoros() {
        return pomodoroDao.getTodosPomodoros();
    }

    public void deletaPomodoro(int id) {
        pomodoroDao.deletePomodoro(id);
    }

    public void updatePomodoros(Pomodoro pomodoro) {
        pomodoroDao.updatePomodoros(pomodoro);
    }
    //CRUD

    public void updateQuantidadePomodoros(int id, int numPomodoros) {
        pomodoroDao.updateQuantidadePomodoros(id, numPomodoros - 1);
    }

    public int getQuantidadePomodoros(String id) {
        return pomodoroDao.getQuantidadePomodoros(id);
    }

    public void ativaTimer(Context context,MyCountDownTimer timer, TextView chronometro, int mimutos, Button inicio, TextView quantidadePomodoros, int idPomodoro) {
        timer = new MyCountDownTimer(context, chronometro, (mimutos * 60 * 1000), 1000, inicio, quantidadePomodoros, idPomodoro);
        timer.start();
        inicio.setEnabled(false);
    }

    public void notificacao(){
        Notification.Builder mBuilder = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(context.getString(R.string.pausa))
                .setContentText(context.getString(R.string.intervalo_de_cinco_minutos))
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

}
