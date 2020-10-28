package br.senai.sc.sistemaeventos.database.contract;

import br.senai.sc.sistemaeventos.database.entity.EventEntity;

public final class EventContract {

    private EventContract() {}

    public static final String criarTabela() {
        return "CREATE TABLE " + EventEntity.TABLE_NAME + " (" +
                EventEntity._ID + " INTEGER PRIMARY KEY," +
                EventEntity.COLUMN_NAME_NOME + " TEXT," +
                EventEntity.COLUMN_NAME_LOCAL+ " TEXT," +
                EventEntity.COLUMN_NAME_DATA + " TEXT)";
    }

    public static final String removerTabela() {
        return "DROP TABLE IF EXISTS " + EventEntity.TABLE_NAME;
    }
}
