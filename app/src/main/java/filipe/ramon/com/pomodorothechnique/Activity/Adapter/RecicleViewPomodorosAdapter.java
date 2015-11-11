package filipe.ramon.com.pomodorothechnique.Activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class RecicleViewPomodorosAdapter extends RecyclerView.Adapter<RecicleViewPomodorosAdapter.PomodoroViewHolder>{

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
    public PomodoroViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_card_view, viewGroup, false);
        PomodoroViewHolder pvh = new PomodoroViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PomodoroViewHolder pomodoroViewHolder, int i) {

        pomodoroViewHolder.pomodoroTitulo.setText(listPomodoros.get(i).getTitulo());
        pomodoroViewHolder.pomodoroDescricao.setText(listPomodoros.get(i).getDescricao());
        pomodoroViewHolder.pomodoroNum.setText(String.valueOf(listPomodoros.get(i).getPomodoro()));
        pomodoroViewHolder.pomodoroId.setText(String.valueOf(listPomodoros.get(i).getId()));

        pomodoroViewHolder.btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gerenciador = new GerenciadorPromodorosBusiness(context);

                int id = Integer.valueOf(pomodoroViewHolder.pomodoroId.getText().toString());
                int quantidadePomodoros = gerenciador.getQuantidadePomodoros(String.valueOf(id));

                if (timer == null) {
                    if (quantidadePomodoros > 0) {
                        timer = new MyCountDownTimer(context, chronometro, 1 * 60 * 1000, 1000, pomodoroViewHolder.btnIniciar, pomodoroViewHolder.pomodoroNum, id);
                        timer.start();
                        pomodoroViewHolder.btnIniciar.setEnabled(false);
                    } else {
                        pomodoroViewHolder.pomodoroNum.setTextColor(Color.RED);
                    }
                }
            }
        });

        pomodoroViewHolder.btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gerenciador = new GerenciadorPromodorosBusiness(context);

                int id = Integer.valueOf( pomodoroViewHolder.pomodoroId.getText().toString() );
                int quantidadePomodoros = gerenciador.getQuantidadePomodoros(String.valueOf(id));

                if(timer != null){
                    pomodoroViewHolder.btnIniciar.setEnabled(true);
                    timer.cancel();
                    timer = null;
                    chronometro.setText("25:00");

                    if(quantidadePomodoros > 0) {
                        gerenciador.updateQuantidadePomodoros(id, quantidadePomodoros);
                        quantidadePomodoros = gerenciador.getQuantidadePomodoros(String.valueOf(id));
                        pomodoroViewHolder.pomodoroNum.setText(String.valueOf(String.valueOf(quantidadePomodoros)));
                    }
                }
            }
        });


        pomodoroViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(v.getContext(), DetailPetAnimalActivity.class);
                Bundle b = new Bundle();
                b.putSerializable( v.getContext().getString( R.string.pet ),p);
                intent.putExtras(b);
                v.getContext().startActivity(intent);*/
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PomodoroViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView pomodoroTitulo;
        TextView pomodoroDescricao;
        TextView pomodoroNum;
        TextView pomodoroId;
        Button btnIniciar;
        Button btnConcluir;

        PomodoroViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            pomodoroTitulo = (TextView)itemView.findViewById(R.id.pomodoroTitulo);
            pomodoroDescricao = (TextView)itemView.findViewById(R.id.pomodoroDescricao);
            pomodoroNum = (TextView)itemView.findViewById(R.id.Pomodoros);
            pomodoroId = (TextView)itemView.findViewById(R.id.textViewIdPomodoro);
            btnIniciar = (Button)itemView.findViewById(R.id.buttonIniciar);
            btnConcluir = (Button)itemView.findViewById(R.id.buttonConcluir);
        }
    }
}