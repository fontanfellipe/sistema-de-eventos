package br.senai.sc.sistemaeventos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import br.senai.sc.sistemaeventos.database.contract.EventContract;

public class DatabaseDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "db.evento";
    public static final int DATABASE_VERSION = 2;

    public DatabaseDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EventContract.criarTabela());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EventContract.removerTabela());
        db.execSQL(EventContract.criarTabela());
    }
}
