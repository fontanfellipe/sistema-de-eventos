package br.senai.sc.sistemaeventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import br.senai.sc.sistemaeventos.database.EventoDAO;
import br.senai.sc.sistemaeventos.modelo.Evento;

public class CadastroEventos extends AppCompatActivity {

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_eventos);
        setTitle("Adicionar Evento");

        carregarDadosEvento();
    }

    private void carregarDadosEvento() {
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null && intent.getExtras().get("editarEvento") != null) {
            Evento evento = (Evento) intent.getExtras().get("editarEvento");
            EditText editTextNome = findViewById(R.id.editText_nome);
            EditText editTextData = findViewById(R.id.editText_data);
            EditText editTextLocal = findViewById(R.id.editText_local);

            editTextNome.setText(evento.getNome());
            editTextData.setText(String.valueOf(evento.getData()));
            editTextLocal.setText(evento.getLocal());
            //edicao = true;
            id = evento.getId();
        }
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar(View v) {
        EditText editTextNome = findViewById(R.id.editText_nome);
        EditText editTextLocal = findViewById(R.id.editText_local);
        EditText editTextData = findViewById(R.id.editText_data);

        String nome = editTextNome.getText().toString();
        String local = editTextLocal.getText().toString();
        String data = editTextData.getText().toString() ;

        Evento evento = new Evento(id, nome, local, data);
        EventoDAO eventoDAO = new EventoDAO(getBaseContext());
        boolean salvou = eventoDAO.salvar(evento);

        if(nome.length() == 0) {
            editTextNome.setError("Insira o nome do evento");
        } else if (local.length() == 0) {
            editTextLocal.setError("Insira um local para o evento");
        } else if (data.length() == 0) {
            editTextData.setError("Insira uma data para o evento");
        } else {
            if(salvou){

                finish();
            } else {
                Toast.makeText(CadastroEventos.this, "Erro ao salvar", Toast.LENGTH_LONG).show();
            }

        }
    }
}
