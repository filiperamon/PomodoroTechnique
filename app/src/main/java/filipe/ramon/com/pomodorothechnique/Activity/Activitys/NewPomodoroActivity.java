package filipe.ramon.com.pomodorothechnique.Activity.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import filipe.ramon.com.pomodorothechnique.Activity.Dao.PomodoroDao;
import filipe.ramon.com.pomodorothechnique.Activity.Entity.Pomodoro;
import filipe.ramon.com.pomodorothechnique.R;

public class NewPomodoroActivity extends AppCompatActivity {

    private EditText edTitulo;
    private EditText edDescricao;
    private EditText edPomodoros;
    private Button   btSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pomodoro);

        iniciaComponentes();

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pomodoro p = new Pomodoro();
                p.setTitulo(edTitulo.getEditableText().toString());
                p.setDescricao(edDescricao.getEditableText().toString());
                p.setPomodoro(Integer.valueOf(edPomodoros.getEditableText().toString()));
                p.setAtivo(1);
                p.setConcluido(0);

                PomodoroDao pDao = new PomodoroDao(NewPomodoroActivity.this);
                pDao.setPomodoro(p);
                pDao.close();

                finish();
            }
        });

    }

    private void iniciaComponentes(){
        edTitulo    = (EditText) findViewById(R.id.editTextTitulo);
        edDescricao = (EditText) findViewById(R.id.editTextDescricao);
        edPomodoros = (EditText) findViewById(R.id.editTextPomodoros);
        btSalvar    = (Button)   findViewById(R.id.buttonSalvar);
    }
}
