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

    private static int VERSION = 2;
    private static String TABELA = "Pomodoro";
    private static String[] COLS = {"id","titulo","descricao","numero_pomodoros","timer", "interacao","ativo","concluido"};

    public PomodoroDao(Context context){
        super(context, TABELA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA +
                " ( id INTEGER PRIMARY KEY, "+
                " titulo TEXT, " +
                " descricao TEXT, " +
                " numero_pomodoros INTEGER, " +
                " timer INTEGER," +
                " interacao INTEGER," +
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
        values.put("timer",25);
        values.put("interacao",0);
        values.put("ativo",0);
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
            p.setTempo(c.getInt(5));
            p.setInteracao(c.getInt(6));
            p.setAtivo(c.getInt(7));
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

        db.update(TABELA, newValues, "id = ? ", new String[]{String.valueOf(id)});
    }

    public void updatePomodoros(Pomodoro pomodoro){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("titulo", pomodoro.getTitulo());
        newValues.put("descricao", pomodoro.getDescricao());
        newValues.put("numero_pomodoros", pomodoro.getPomodoro());

        db.update(TABELA, newValues, "id = ? ", new String[]{String.valueOf(pomodoro.getId())});
    }

    public int deletePomodoro(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "id=?";
        return db.delete(TABELA, where, new String[]{String.valueOf(id)});
    }

    public void ativaDesativaPomodoto(int id, int ativo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("ativo", ativo);

        db.update(TABELA, newValues, "id = ? ", new String[]{String.valueOf(id)});
    }

    public void alteraTimerPomodoto(int id, int timer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("timer", timer);

        db.update(TABELA, newValues, "id = ? ", new String[]{String.valueOf(id)});
    }

    public int getTimerPomodoros(String id){
        String selectQuery = "SELECT timer FROM "+TABELA+" WHERE id = " + id;

        SQLiteDatabase banco = this.getWritableDatabase();
        Cursor cursor = banco.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        String nomeString = cursor.getString(cursor.getColumnIndex("timer"));
        StringBuilder conversor = new StringBuilder();
        conversor.append(nomeString);

        return Integer.valueOf(conversor.toString());
    }

    public void incrementaInteracaoPomodoto(int id, int interacao){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("interacao", interacao);

        db.update(TABELA, newValues, "id = ? ", new String[]{String.valueOf(id)});
    }

    public int getInteracaoPomodoros(String id){
        String selectQuery = "SELECT interacao FROM "+TABELA+" WHERE id = " + id;

        SQLiteDatabase banco = this.getWritableDatabase();
        Cursor cursor = banco.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        String nomeString = cursor.getString(cursor.getColumnIndex("interacao"));
        StringBuilder conversor = new StringBuilder();
        conversor.append(nomeString);

        return Integer.valueOf(conversor.toString());
    }
}
