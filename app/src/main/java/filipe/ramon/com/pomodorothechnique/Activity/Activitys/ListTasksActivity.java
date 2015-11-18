package filipe.ramon.com.pomodorothechnique.Activity.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import filipe.ramon.com.pomodorothechnique.Activity.Adapter.RecicleViewPomodorosAdapter;
import filipe.ramon.com.pomodorothechnique.Activity.Business.GerenciadorPromodorosBusiness;
import filipe.ramon.com.pomodorothechnique.Activity.Entity.Pomodoro;
import filipe.ramon.com.pomodorothechnique.Activity.Util.MyCountDownTimer;
import filipe.ramon.com.pomodorothechnique.R;

public class ListTasksActivity extends AppCompatActivity {

    private MyCountDownTimer timer;
    private RecyclerView rvList;
    private List<Pomodoro> listPomodoros;
    private TextView chronometro;
    private Button btAdicionarTarefa;
    private GerenciadorPromodorosBusiness pBO;
    private RecicleViewPomodorosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);

        inicializaComponentes();

        rvList = (RecyclerView) findViewById(R.id.rvListPomodoros);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvList.setLayoutManager(llm);

        registerForContextMenu(rvList);

        btAdicionarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iNewTask = new Intent(ListTasksActivity.this, NewPomodoroActivity.class);
                startActivity(iNewTask);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        initializeData();
        atualizarLista();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelaTimer();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;

        try {
            position = adapter.getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        if (listPomodoros != null) {
            //if (listPomodoros.get(position).getAtivo() == 0) {

                if (item.getTitle().equals(getString(R.string.editar))) {
                    Bundle bundle = new Bundle();
                    Intent iEditTask = new Intent(ListTasksActivity.this, NewPomodoroActivity.class);
                    bundle.putString(getString(R.string.editar), "1");
                    bundle.putSerializable(getString(R.string.promodoros), listPomodoros.get(position));
                    iEditTask.putExtras(bundle);
                    startActivity(iEditTask);

                    if (listPomodoros.get(position).getAtivo() == 0) {
                        pBO = new GerenciadorPromodorosBusiness(this);
                        pBO.deletaPomodoro(listPomodoros.get(position).getId());
                        initializeData();
                        atualizarLista();
                        cancelaTimer();
                    }

               // } else {
               //     Toast.makeText(this, "Pomodo ativado não pode sofrer alterações.", Toast.LENGTH_SHORT).show();
               //}
            }
        }

        return true;
    }

    public void cancelaTimer(){
        if (timer != null) {
            timer.cancel();
            chronometro.setText(getString(R.string.vinte_cinco_minutos));
        }
    }

    public void inicializaComponentes() {
        rvList = (RecyclerView) findViewById(R.id.rvListPomodoros);
        chronometro = (TextView) findViewById(R.id.chronometer);
        btAdicionarTarefa = (Button) findViewById(R.id.buttonNewTask);
    }

    private void initializeData() {
        pBO = new GerenciadorPromodorosBusiness(this);
        listPomodoros = pBO.getTodosPomodoros();
    }

    public void atualizarLista() {
        if (listPomodoros != null) {
            adapter = new RecicleViewPomodorosAdapter(this, listPomodoros, timer, chronometro);
            rvList.setAdapter(adapter);
        }
    }

}
