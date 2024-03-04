package com.example.gerenciadordearquivos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    MaterialButton btnArmazenamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnArmazenamento = findViewById(R.id.btn_armazenamento);

        btnArmazenamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Permissão Estiver OK então ele lista o nosso gerenciador de tarefas
                if(checarPermissao()){

                    // Caso a permissão tenha sido dada, ir para a proxima tela
                    Intent intent = new Intent(MainActivity.this, ArquivosActivity.class);


                    // Aqui passamo o caminho do armazenamento externo para acessa-lo na lista -
                    // Com isso obermos o diretorio do caminho de armazenamento externo.
                    String path = Environment.getExternalStorageDirectory().getPath();
                    intent.putExtra("path",path);

                    // Inicia a atividade
                    startActivity(intent);
                }else{
                    // Caso contrario, ele solicita a permissão
                    solicitarPermissao();
                }
            }
        });
    }

    /* Booleano para Checar se a permissão já foi concedida para não precisar ficar retornando a mensagem de permissão toda vez
     que utilizar o app
    */
    private boolean checarPermissao(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // Verifica se a permissão foi dada, caso sim retornar verdadeiro e não solicitar, caso falso solicitar permissão
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else
            return false;
    }

    // Metodo utilizado para acessar o armazenamento externo e solicitar a permissao do usuario
    private void solicitarPermissao(){

        // Não quermos permitir que o usuario negue a permissão e acesse o app, caso ele negue, informamos uma mensagem para
        // ele autorizar caso queira utilizar o app
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this,"A permissão de armazenamento é necessária, permita nas configurações",Toast.LENGTH_SHORT).show();
        }else
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE},222);
    }
}