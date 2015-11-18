package filipe.ramon.com.pomodorothechnique.Activity.Util;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import filipe.ramon.com.pomodorothechnique.Activity.Business.GerenciadorPromodorosBusiness;
import filipe.ramon.com.pomodorothechnique.R;

/**
 * Created by ufc155.barbosa on 09/11/2015.
 */
public class MyCountDownTimer extends CountDownTimer {
    private Context context;
    private TextView tv;
    private TextView tvBtnInicio;
    private TextView tvQuantidadePomodoros;
    private long timeInFuture;
    private int idPomodoro;
    protected GerenciadorPromodorosBusiness gerenciador;


    public MyCountDownTimer(Context context, TextView tv, long timeInFuture, long interval, TextView tvBtnInicio, TextView tvQuantidadePomodoros, int idPomodoro){
        super(timeInFuture, interval);
        this.context = context;
        this.tv = tv;
        this.tvBtnInicio = tvBtnInicio;
        this.idPomodoro = idPomodoro;
        this.tvQuantidadePomodoros = tvQuantidadePomodoros;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        timeInFuture = millisUntilFinished;
        tv.setText(getCorretcTimer(true, millisUntilFinished)+":"+getCorretcTimer(false, millisUntilFinished));
    }

    @Override
    public void onFinish() {
        timeInFuture -= 1000;
        gerenciador = new GerenciadorPromodorosBusiness(context);
        tv.setText(getCorretcTimer(true, timeInFuture) + ":" + getCorretcTimer(false, timeInFuture));
        tvBtnInicio.setEnabled(true);

        //NOTIFICATION
        gerenciador.notificacao();

        //Atualiza quantidade de pomodoros
        int quantidadePomodoros = Integer.valueOf(tvQuantidadePomodoros.getText().toString());
        gerenciador.updateQuantidadePomodoros(idPomodoro, quantidadePomodoros);
        tvQuantidadePomodoros.setText(String.valueOf(String.valueOf(gerenciador.getQuantidadePomodoros(String.valueOf(idPomodoro)))));

        if(gerenciador.getQuantidadePomodoros(String.valueOf(idPomodoro)) > 0) {

            new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.alert))
                    .setMessage(context.getString(R.string.alert_descricao_pausa))
                    .setCancelable(false)
                    .setNegativeButton("Não",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                gerenciador.ativaTimer(context, MyCountDownTimer.this, tv, 5,
                                        (Button) tvBtnInicio, tvQuantidadePomodoros, idPomodoro);
                            }
                    }).create().show();

        }
    }

    private String getCorretcTimer(boolean isMinute, long millisUntilFinished){
        String aux;
        int constCalendar = isMinute ? Calendar.MINUTE : Calendar.SECOND;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millisUntilFinished);

        aux = c.get(constCalendar) < 10 ? "0"+c.get(constCalendar) : ""+c.get(constCalendar);
        return(aux);
    }
}
