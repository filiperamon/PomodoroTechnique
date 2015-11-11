package filipe.ramon.com.pomodorothechnique.Activity.Business;

import android.content.Context;
import android.widget.TextView;

import filipe.ramon.com.pomodorothechnique.Activity.Dao.PomodoroDao;
import filipe.ramon.com.pomodorothechnique.Activity.Util.MyCountDownTimer;

/**
 * Created by ufc155.barbosa on 10/11/2015.
 */
public class GerenciadorPromodorosBusiness {

    private MyCountDownTimer timer;
    private TextView chronometro;
    private Context context;
    private PomodoroDao pomodoroDao;

    public GerenciadorPromodorosBusiness(Context context){
        this.context = context;
        pomodoroDao = new PomodoroDao(context);
    }

    public void iniciaContator(){

    }

    public void cancelaContador(){

    }

    public int getQuantidadePomodoros(String id){
        return pomodoroDao.getQuantidadePomodoros(id);
    }

    public void updateQuantidadePomodoros(int id, int numPomodoros){
        pomodoroDao.updateQuantidadePomodoros(id, numPomodoros-1);
    }
}
