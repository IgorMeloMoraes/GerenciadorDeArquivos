package com.example.gerenciadordearquivos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class ArquivosActivity extends AppCompatActivity {

    TextView txtArquivosNaoEncontrados;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arquivos);

        recyclerView = findViewById(R.id.recycler_view);
        txtArquivosNaoEncontrados = findViewById(R.id.txt_sem_arquivos);


        // Criamos uma nova string para retornar o caminho que passomos na Main
        String path = getIntent().getStringExtra("path");

        // Aqui pegamos todos os arquivos importando da path que o usuario deu permissão de leitura e escrita
        // Este é o caminho que temos todos os armazenamentos e obtemos a lista de arquivos e pastas
        // Lista tambem todos os arquivos e pastas de .raiz
        File root = new File(path);
        File[] arquivosPastas = root.listFiles();

        // Se os arquivos e pastas forem nulos ou vazios não termos retorno de aqruivos e mostraremos na tela o layout que
        // criamos e deixamos invisivel
        if(arquivosPastas==null || arquivosPastas.length ==0){
            txtArquivosNaoEncontrados.setVisibility(View.VISIBLE);
            return;
        }

        // Se tiver algum arquivo retorna a lista completa e esconde os texto
        txtArquivosNaoEncontrados.setVisibility(View.INVISIBLE);

        // Chama a Lista e configura o adpter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterArquivos(getApplicationContext(),arquivosPastas));
    }
}