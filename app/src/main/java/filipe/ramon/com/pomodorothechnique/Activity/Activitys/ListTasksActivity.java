package filipe.ramon.com.pomodorothechnique.Activity.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import filipe.ramon.com.pomodorothechnique.Activity.Adapter.RecicleViewPomodorosAdapter;
import filipe.ramon.com.pomodorothechnique.Activity.Entity.Pomodoro;
import filipe.ramon.com.pomodorothechnique.Activity.Dao.PomodoroDao;
import filipe.ramon.com.pomodorothechnique.Activity.Util.MyCountDownTimer;
import filipe.ramon.com.pomodorothechnique.R;

public class ListTasksActivity extends AppCompatActivity {

    private MyCountDownTimer timer;
    private RecyclerView rvList;
    private List<Pomodoro> listPomodoros;
    private TextView chronometro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);

        inicializaComponentes();

        rvList = (RecyclerView) findViewById(R.id.rvListPomodoros);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvList.setLayoutManager(llm);

    }

    @Override
    protected void onResume() {
        super.onResume();

        initializeData();
        atualizarLista();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        if(timer != null){
            timer.cancel();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent iNewTask = new Intent(ListTasksActivity.this, NewPomodoroActivity.class);
            startActivity(iNewTask);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void inicializaComponentes(){
        rvList = (RecyclerView) findViewById(R.id.rvListPomodoros);
        chronometro = (TextView) findViewById(R.id.chronometer);
    }

    private void initializeData(){
        PomodoroDao pDao = new PomodoroDao(this);
        listPomodoros = pDao.getTodosPomodoros();
        pDao.close();
    }

    public void atualizarLista(){
        if(listPomodoros != null) {
            RecicleViewPomodorosAdapter adapter = new RecicleViewPomodorosAdapter(this, listPomodoros, timer, chronometro);
            rvList.setAdapter(adapter);
        }
    }

}
