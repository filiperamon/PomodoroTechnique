package filipe.ramon.com.pomodorothechnique.Activity.Util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.CountDownTimer;
import android.provider.Settings;
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
        tv.setText(getCorretcTimer(true, timeInFuture) + ":" + getCorretcTimer(false, timeInFuture));
        tvBtnInicio.setEnabled(true);

        /*NOTIFICATION*/

        Notification.Builder mBuilder = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(context.getString(R.string.alertaIntervaloTitulo))
                .setContentText(context.getString(R.string.intervalo_de_cinco_minutos))
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

        /*NOTIFICATION*/

        /*Atualiza quantidade de pomodoros*/

        GerenciadorPromodorosBusiness gerenciador = new GerenciadorPromodorosBusiness(context);
        int quantidadePomodoros = Integer.valueOf(tvQuantidadePomodoros.getText().toString());

        gerenciador.updateQuantidadePomodoros(idPomodoro, quantidadePomodoros);
        tvQuantidadePomodoros.setText(String.valueOf(String.valueOf(gerenciador.getQuantidadePomodoros(String.valueOf(idPomodoro)))));

        /*Atualiza quantidade de pomodoros*/
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
