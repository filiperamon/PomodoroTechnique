package filipe.ramon.com.pomodorothechnique.Activity.Business;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import filipe.ramon.com.pomodorothechnique.Activity.Dao.PomodoroDao;
import filipe.ramon.com.pomodorothechnique.Activity.Entity.Pomodoro;
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


    //CRUD
    public void insertPomodoro(Pomodoro pomodoro){
        pomodoroDao.setPomodoro(pomodoro);
    }

    public List<Pomodoro> getTodosPomodoros(){
        return  pomodoroDao.getTodosPomodoros();
    }

    public void deletaPomodoro(int id) {
        pomodoroDao.deletePomodoro(id);
    }

    public void updatePomodoros(Pomodoro pomodoro){
        pomodoroDao.updatePomodoros(pomodoro);
    }
    //CRUD

    public void updateQuantidadePomodoros(int id, int numPomodoros){
        pomodoroDao.updateQuantidadePomodoros(id, numPomodoros-1);
    }

    public int getQuantidadePomodoros(String id){
        return pomodoroDao.getQuantidadePomodoros(id);
    }
}
