package filipe.ramon.com.pomodorothechnique.Activity.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import filipe.ramon.com.pomodorothechnique.Activity.Business.GerenciadorPromodorosBusiness;
import filipe.ramon.com.pomodorothechnique.Activity.Entity.Pomodoro;
import filipe.ramon.com.pomodorothechnique.R;

public class NewPomodoroActivity extends AppCompatActivity {

    private EditText edTitulo;
    private EditText edDescricao;
    private EditText edPomodoros;
    private Button   btSalvar;
    private int telaDeEdicao = 0;
    private Pomodoro pomodoro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pomodoro);

        iniciaComponentes();

        if(this.getIntent().getExtras() != null) {
            telaDeEdicao = Integer.valueOf(this.getIntent().getStringExtra(getString(R.string.editar)));
            pomodoro     = (Pomodoro) this.getIntent().getSerializableExtra(getString(R.string.promodoros));
        }

        if(telaDeEdicao == 1) {
            btSalvar.setText(getString(R.string.salvarEdicao));
            preencheCampos(pomodoro);
        }

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pomodoro == null)
                    pomodoro = new Pomodoro();

                pomodoro.setTitulo(edTitulo.getEditableText().toString());
                pomodoro.setDescricao(edDescricao.getEditableText().toString());
                pomodoro.setPomodoro(Integer.valueOf(edPomodoros.getEditableText().toString()));
                pomodoro.setAtivo(1);
                pomodoro.setConcluido(0);

                GerenciadorPromodorosBusiness pBO = new GerenciadorPromodorosBusiness(NewPomodoroActivity.this);

                if(telaDeEdicao == 0)
                    pBO.insertPomodoro(pomodoro);
                else
                    pBO.updatePomodoros(pomodoro);

                finish();
            }
        });
    }

    private void preencheCampos(Pomodoro pomodoro){
        edTitulo.setText(pomodoro.getTitulo());
        edDescricao.setText(pomodoro.getDescricao());
        edPomodoros.setText(String.valueOf(pomodoro.getPomodoro()));
    }

    private void iniciaComponentes(){
        edTitulo    = (EditText) findViewById(R.id.editTextTitulo);
        edDescricao = (EditText) findViewById(R.id.editTextDescricao);
        edPomodoros = (EditText) findViewById(R.id.editTextPomodoros);
        btSalvar    = (Button)   findViewById(R.id.buttonSalvar);
    }
}
