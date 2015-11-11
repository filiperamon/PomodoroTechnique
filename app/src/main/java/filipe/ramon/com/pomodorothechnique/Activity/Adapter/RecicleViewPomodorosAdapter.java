package filipe.ramon.com.pomodorothechnique.Activity.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import filipe.ramon.com.pomodorothechnique.Activity.Business.GerenciadorPromodorosBusiness;
import filipe.ramon.com.pomodorothechnique.Activity.Entity.Pomodoro;
import filipe.ramon.com.pomodorothechnique.Activity.Util.MyCountDownTimer;
import filipe.ramon.com.pomodorothechnique.R;

/**
 * Created by ufc155.barbosa on 09/11/2015.
 */
public class RecicleViewPomodorosAdapter extends RecyclerView.Adapter<RecicleViewPomodorosAdapter.pomodoroViewHolder>{

    List<Pomodoro> listPomodoros;
    private MyCountDownTimer timer;
    private TextView chronometro;
    private Context context;
    private GerenciadorPromodorosBusiness gerenciador;

    public RecicleViewPomodorosAdapter(Context context, List<Pomodoro> pomodoros, MyCountDownTimer timer, TextView chronometro){
        this.context = context;
        this.listPomodoros = pomodoros;
        this.timer = timer;
        this.chronometro = chronometro;
    }

    @Override
    public int getItemCount() {
        return listPomodoros.size();
    }

    @Override
    public pomodoroViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_card_view, viewGroup, false);
        pomodoroViewHolder pvh = new pomodoroViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final pomodoroViewHolder pomodoroViewHolder, int i) {

        pomodoroViewHolder.pomodoroTitulo.setText(listPomodoros.get(i).getTitulo());
        pomodoroViewHolder.pomodoroDescricao.setText(listPomodoros.get(i).getDescricao());
        pomodoroViewHolder.pomodoroNum.setText(String.valueOf(listPomodoros.get(i).getPomodoro()));
        pomodoroViewHolder.pomodoroId.setText(String.valueOf(listPomodoros.get(i).getId()));

        pomodoroViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gerenciador = new GerenciadorPromodorosBusiness(context);

                int id = Integer.valueOf(pomodoroViewHolder.pomodoroId.getText().toString());
                int quantidadePomodoros = gerenciador.getQuantidadePomodoros(String.valueOf(id));

                if (timer == null) {
                    if (quantidadePomodoros > 0) {
                        timer = new MyCountDownTimer(context, chronometro, 25 * 60 * 1000, 1000, pomodoroViewHolder.pomodoroNum, id);
                        timer.start();
                    } else {
                        pomodoroViewHolder.pomodoroNum.setTextColor(Color.RED);
                    }
                }
            }
        });

        pomodoroViewHolder.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlert(v);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void openAlert(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(context.getString(R.string.alert_confirmacao));
        alertDialogBuilder.setMessage(context.getString(R.string.alerta_descricao_confirmacao));
        alertDialogBuilder.setPositiveButton(context.getString(R.string.sim), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


            }
        });

        alertDialogBuilder.setNegativeButton(context.getString(R.string.nao), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();

    }


    public static class pomodoroViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView pomodoroTitulo;
        TextView pomodoroDescricao;
        TextView pomodoroNum;
        TextView pomodoroId;
        ImageButton btnClose;

        pomodoroViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            pomodoroTitulo = (TextView)itemView.findViewById(R.id.pomodoroTitulo);
            pomodoroDescricao = (TextView)itemView.findViewById(R.id.pomodoroDescricao);
            pomodoroNum = (TextView)itemView.findViewById(R.id.Pomodoros);
            pomodoroId = (TextView)itemView.findViewById(R.id.textViewIdPomodoro);
            btnClose = (ImageButton)itemView.findViewById(R.id.imageClose);
        }
    }
}