package filipe.ramon.com.pomodorothechnique.Activity.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import filipe.ramon.com.pomodorothechnique.Activity.Entity.Pomodoro;

/**
 * Created by ufc155.barbosa on 10/11/2015.
 */
public class PomodoroDao extends SQLiteOpenHelper {

    private static int VERSION = 1;
    private static String TABELA = "Pomodoro";
    private static String[] COLS = {"id","titulo","descricao","numero_pomodoros", "ativo","concluido"};

    //private SQLiteDatabase db;

    public PomodoroDao(Context context){
        super(context, TABELA, null, VERSION);
        Log.i("DAO", "Construtor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DAO", "CRIOU O BANCO");
        String sql = "CREATE TABLE " + TABELA +
                " ( id INTEGER PRIMARY KEY, "+
                " titulo TEXT, " +
                " descricao TEXT, " +
                " numero_pomodoros INTEGER, " +
                " ativo INTEGER, " +
                " concluido INTEGER " +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PomodoroDao.TABELA);
        this.onCreate(db);
    }

    public void setPomodoro(Pomodoro pomodoro){
        ContentValues values = new ContentValues();
        values.put("titulo",pomodoro.getTitulo());
        values.put("descricao",pomodoro.getDescricao());
        values.put("numero_pomodoros",pomodoro.getPomodoro());
        values.put("ativo",1);
        values.put("concluido", 0);
        getWritableDatabase().insertOrThrow(TABELA, null, values);
    }

    public List<Pomodoro> getTodosPomodoros(){
        List<Pomodoro> pomodoros = new ArrayList<Pomodoro>();

        Cursor c = getWritableDatabase().query(TABELA,COLS, null,null,null,null,null);

        while (c.moveToNext()){
            Pomodoro p = new Pomodoro();
            p.setId(c.getInt(0));
            p.setTitulo(c.getString(1));
            p.setDescricao(c.getString(2));
            p.setPomodoro(c.getInt(3));
            p.setAtivo(c.getInt(4));

            pomodoros.add(p);
        }

        c.close();

        return pomodoros;
    }

    public int getQuantidadePomodoros(String id){
        String selectQuery = "SELECT numero_pomodoros FROM "+TABELA+" WHERE id = " + id;

        SQLiteDatabase banco = this.getWritableDatabase();
        Cursor cursor = banco.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        String nomeString = cursor.getString(cursor.getColumnIndex("numero_pomodoros"));
        StringBuilder conversor = new StringBuilder();
        conversor.append(nomeString);

        return Integer.valueOf(conversor.toString());
    }

    public void updateQuantidadePomodoros(int id, int numPomodoros){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("numero_pomodoros", numPomodoros);

        db.update(TABELA, newValues, "id = ? ", new String[]{String.valueOf(id)} );
    }
}
