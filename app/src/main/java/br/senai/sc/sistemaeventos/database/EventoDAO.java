package br.senai.sc.sistemaeventos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.senai.sc.sistemaeventos.database.entity.EventEntity;
import br.senai.sc.sistemaeventos.modelo.Evento;

public class EventoDAO {

    private final String SQL_LISTAR_TODOS = "SELECT * FROM " + EventEntity.TABLE_NAME;
    private DBGateway dbGateway;

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

    public int deleteItem(Evento evento){
        return dbGateway.getDatabase().delete(EventEntity.TABLE_NAME, EventEntity._ID + "=?", new String[]{String.valueOf(evento.getId())});
    }
}
