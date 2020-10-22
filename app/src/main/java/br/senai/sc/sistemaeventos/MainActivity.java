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

import br.senai.sc.sistemaeventos.modelo.Evento;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE_NOVOEVENTO = 1;
    private final int RESULT_CODE_NOVOEVENTO = 10;
    private final int REQUEST_CODE_EDITAREVENTO = 2;
    private final int RESULT_CODE_EVENTOEDITADO = 11;

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

        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this, android.R.layout.simple_list_item_1, eventos);

        listViewEventos.setAdapter(adapterEventos);

        definirOnClickListenerItem();
        definirLongClickListenerListView();

    }

    private void definirLongClickListenerListView() {
        listViewEventos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Evento eventoLongClick = adapterEventos.getItem(position);
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapterEventos.remove(eventoLongClick);
                                adapterEventos.notifyDataSetChanged();
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
               startActivityForResult(intent, REQUEST_CODE_EDITAREVENTO);
            }
        });
    }

    public void onClickNovoEvento(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroEventos.class);
        startActivityForResult(intent, REQUEST_CODE_NOVOEVENTO);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_NOVOEVENTO && resultCode == RESULT_CODE_NOVOEVENTO) {
            Evento evento = (Evento) data.getExtras().getSerializable("novoEvento");
            this.adapterEventos.add(evento);
            evento.setId(++id);
        } else if (requestCode == REQUEST_CODE_EDITAREVENTO && resultCode == RESULT_CODE_EVENTOEDITADO) {
            Evento eventoEditado = (Evento) data.getExtras().getSerializable("eventoEditado");
            for(int i = 0; i < adapterEventos.getCount(); i++) {
                Evento evento = adapterEventos.getItem(i);
                if(evento.getId() == eventoEditado.getId()) {
                    adapterEventos.remove(evento);
                    adapterEventos.insert(eventoEditado, i);
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}