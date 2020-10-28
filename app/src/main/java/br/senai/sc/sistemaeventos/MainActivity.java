package br.senai.sc.sistemaeventos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import br.senai.sc.sistemaeventos.database.EventoDAO;
import br.senai.sc.sistemaeventos.modelo.Evento;

public class MainActivity extends AppCompatActivity {

    private ListView listViewEventos;
    private ArrayAdapter<Evento> adapterEventos;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Cadastro de eventos");

        listViewEventos = findViewById(R.id.listView_eventos);
        ArrayList<Evento> eventos = new ArrayList<Evento>();

        definirOnClickListenerItem();
        definirLongClickListenerListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventoDAO eventoDAO = new EventoDAO(getBaseContext());
        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this, android.R.layout.simple_list_item_1, eventoDAO.listar());
        listViewEventos.setAdapter(adapterEventos);
    }

    private void definirLongClickListenerListView() {
        listViewEventos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Evento eventoLongClick = adapterEventos.getItem(position);
                final EventoDAO eventoDAO = new EventoDAO(getBaseContext());

                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapterEventos.remove(eventoLongClick);
                                adapterEventos.notifyDataSetChanged();
                                eventoDAO.deleteItem(eventoLongClick);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }

    private void definirOnClickListenerItem() {
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Evento eventoSelecionado = adapterEventos.getItem(position);
               Intent intent = new Intent(MainActivity.this, CadastroEventos.class);
               intent.putExtra("editarEvento", eventoSelecionado);
               startActivity(intent);
            }
        });
    }

    public void onClickNovoEvento(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroEventos.class);
        startActivity(intent);
    }
}