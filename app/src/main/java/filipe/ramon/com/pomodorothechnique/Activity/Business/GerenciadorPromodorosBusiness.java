package filipe.ramon.com.pomodorothechnique.Activity.Business;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import filipe.ramon.com.pomodorothechnique.Activity.Activitys.ListTasksActivity;
import filipe.ramon.com.pomodorothechnique.Activity.Dao.PomodoroDao;
import filipe.ramon.com.pomodorothechnique.Activity.Entity.Pomodoro;
import filipe.ramon.com.pomodorothechnique.Activity.Util.MyCountDownTimer;
import filipe.ramon.com.pomodorothechnique.R;

/**
 * Created by ufc155.barbosa on 10/11/2015.
 */
public class GerenciadorPromodorosBusiness {

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
        pomodoroDao.updateQuantidadePomodoros(id, numPomodoros);
    }

    public int getQuantidadePomodoros(String id) {
        return pomodoroDao.getQuantidadePomodoros(id);
    }

    public void ativarDesativarPomodoro(int id, int ativo) {
        pomodoroDao.ativaDesativaPomodoto(id, ativo);
    }

    public void ativaTimer(ListTasksActivity context , TextView chronometro, int mimutos, Button inicio, TextView quantidadePomodoros, int idPomodoro) {
        ativarDesativarPomodoro(idPomodoro, 1);
        context.timer = new MyCountDownTimer(context, chronometro, (mimutos * 60 * 1000), 1000, inicio, quantidadePomodoros, idPomodoro);
        context.timer.start();
        inicio.setEnabled(false);

        //IncrementaInteração
        incrementaInteracaoPomodoro(idPomodoro, getAtualinteracaoPomodoro(idPomodoro) + 1);
    }

    public int getAtualinteracaoPomodoro(int id){
        return pomodoroDao.getInteracaoPomodoros(String.valueOf(id));
    }

    public void incrementaInteracaoPomodoro(int id, int interacao){
        pomodoroDao.incrementaInteracaoPomodoto(id, interacao);
    }

    public int getAtualTimerPomodoro(int id){
        return pomodoroDao.getTimerPomodoros(String.valueOf(id));
    }

    public void updateTimerPomodoro(int id, int timer){
        pomodoroDao.alteraTimerPomodoto(id,timer);
    }

    public void notificacao(int timer){
        Notification.Builder mBuilder = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(context.getString(R.string.pausa))
                .setContentText("Pare por " +timer+ " minutos e volte a atividade.")
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

    public int atualizaQuantidadePomodoro(int idPomodoro, int quantidadePomodoros){
        updateQuantidadePomodoros(idPomodoro, quantidadePomodoros);
        return getQuantidadePomodoros(String.valueOf(idPomodoro));
    }
}
