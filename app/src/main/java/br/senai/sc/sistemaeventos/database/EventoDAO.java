package br.senai.sc.sistemaeventos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.senai.sc.sistemaeventos.database.entity.EventEntity;
import br.senai.sc.sistemaeventos.modelo.Evento;

public class EventoDAO {

    private final String SQL_LISTAR_TODOS = "SELECT * FROM " + EventEntity.TABLE_NAME;
    private final String SQL_ORDER_BY_NAME_ASC = "SELECT * FROM " + EventEntity.TABLE_NAME + " ORDER BY " + EventEntity.COLUMN_NAME_NOME + " ASC";
    private final String SQL_ORDER_BY_NAME_DESC = "SELECT * FROM " + EventEntity.TABLE_NAME + " ORDER BY " + EventEntity.COLUMN_NAME_NOME + " DESC";


    private DBGateway dbGateway;
    private DatabaseDBHelper databaseDBHelper;

    public EventoDAO(Context context){
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Evento evento) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventEntity.COLUMN_NAME_NOME, evento.getNome());
        contentValues.put(EventEntity.COLUMN_NAME_LOCAL, evento.getLocal());
        contentValues.put(EventEntity.COLUMN_NAME_DATA, evento.getData());

        if(evento.getId() > 0){
            return dbGateway.getDatabase().update(EventEntity.TABLE_NAME,
                    contentValues,
                    EventEntity._ID + "=?",
                    new String[]{String.valueOf(evento.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(EventEntity.TABLE_NAME, null, contentValues) > 0;
    }

    public List<Evento> listar() {
        List<Evento> eventos = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_NOME));
            String local = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_LOCAL));
            String data = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_DATA));

            eventos.add(new Evento(id, nome, local, data));
        }
        cursor.close();
        return eventos;
    }

    public List<Evento> ordernarAsc() {
        List<Evento> eventosOrdenados = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_ORDER_BY_NAME_ASC, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_NOME));
            String local = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_LOCAL));
            String data = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_DATA));

            eventosOrdenados.add(new Evento(id, nome, local, data));
        }
        cursor.close();
        return eventosOrdenados;
    }


    public List<Evento> ordernarDesc() {
        List<Evento> eventosOrdenados = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_ORDER_BY_NAME_DESC, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_NOME));
            String local = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_LOCAL));
            String data = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_DATA));

            eventosOrdenados.add(new Evento(id, nome, local, data));
        }
        cursor.close();
        return eventosOrdenados;
    }








    public List<Evento> procurarNomeDAO(String queryNome) {
        List<Evento> eventosSearchNome = new ArrayList<>();
        final String SQL_SEARCH_NOME = "SELECT * FROM " + EventEntity.TABLE_NAME + " WHERE " + EventEntity.COLUMN_NAME_NOME + " LIKE " + "'%" + queryNome + "%'";


        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_SEARCH_NOME, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_NOME));
            String local = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_LOCAL));
            String data = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_DATA));

            eventosSearchNome.add(new Evento(id, nome, local, data));
        }
        cursor.close();
        return eventosSearchNome;
    }



    public List<Evento> procurarLocalDAO(String queryLocal) {
        List<Evento> eventosSearchLocal = new ArrayList<>();

        final String SQL_SEARCH_LOCAL = "SELECT * FROM " + EventEntity.TABLE_NAME + " WHERE " + EventEntity.COLUMN_NAME_LOCAL + " LIKE " + "'%" + queryLocal + "%'";

        //Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS, null);
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_SEARCH_LOCAL, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_NOME));
            String local = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_LOCAL));
            String data = cursor.getString(cursor.getColumnIndex(EventEntity.COLUMN_NAME_DATA));

            eventosSearchLocal.add(new Evento(id, nome, local, data));
        }
        cursor.close();
        return eventosSearchLocal;
    }

    public int deleteItem(Evento evento){
        return dbGateway.getDatabase().delete(EventEntity.TABLE_NAME, EventEntity._ID + "=?", new String[]{String.valueOf(evento.getId())});
    }






}
