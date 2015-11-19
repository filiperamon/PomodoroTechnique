package filipe.ramon.com.pomodorothechnique.Activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import filipe.ramon.com.pomodorothechnique.Activity.Activitys.ListTasksActivity;
import filipe.ramon.com.pomodorothechnique.Activity.Business.GerenciadorPromodorosBusiness;
import filipe.ramon.com.pomodorothechnique.Activity.Entity.Pomodoro;
import filipe.ramon.com.pomodorothechnique.Activity.Util.MyCountDownTimer;
import filipe.ramon.com.pomodorothechnique.R;

/**
 * Created by ufc155.barbosa on 09/11/2015.
 */
public class RecicleViewPomodorosAdapter
        extends RecyclerView.Adapter<RecicleViewPomodorosAdapter.PomodoroViewHolder>{

    List<Pomodoro> listPomodoros;
    private TextView chronometro;
    private ListTasksActivity context;
    private GerenciadorPromodorosBusiness gerenciador;
    private int position;

    public RecicleViewPomodorosAdapter(ListTasksActivity context, List<Pomodoro> pomodoros, TextView chronometro){
        this.context = context;
        this.listPomodoros = pomodoros;
        this.chronometro = chronometro;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

        context.registerForContextMenu(pomodoroViewHolder.cv);
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

                if (context.timer == null) {
                    if (quantidadePomodoros > 0)
                        gerenciador.ativaTimer(context, chronometro, 25, pomodoroViewHolder.btnIniciar, pomodoroViewHolder.pomodoroNum, id);
                    else
                        pomodoroViewHolder.pomodoroNum.setTextColor(Color.RED);
                }
            }
        });

        pomodoroViewHolder.btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gerenciador = new GerenciadorPromodorosBusiness(context);

                int id = Integer.valueOf(pomodoroViewHolder.pomodoroId.getText().toString());
                int quantidadePomodoros = gerenciador.getQuantidadePomodoros(String.valueOf(id));

                if (context.timer != null) {
                    pomodoroViewHolder.btnIniciar.setEnabled(true);
                    context.timer.cancel();
                    context.timer = null;
                    chronometro.setText(context.getString(R.string.vinte_cinco_minutos));

                    if (quantidadePomodoros > 0) {
                        gerenciador.updateQuantidadePomodoros(id, quantidadePomodoros);
                        quantidadePomodoros = gerenciador.getQuantidadePomodoros(String.valueOf(id));
                        pomodoroViewHolder.pomodoroNum.setText(String.valueOf(
                                String.valueOf(quantidadePomodoros)));
                    }
                }
            }
        });

        pomodoroViewHolder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(pomodoroViewHolder.getPosition());
                return false;
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PomodoroViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {

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

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, v.getId(), 0, R.string.editar);
            menu.add(0, v.getId(), 0, R.string.excluir);
        }
    }
}