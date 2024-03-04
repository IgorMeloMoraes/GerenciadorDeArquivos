package com.example.gerenciadordearquivos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class AdapterArquivos extends RecyclerView.Adapter<AdapterArquivos.MyViewHolder> {

    Context context;
    File[] pastasArquivos;

    public AdapterArquivos(Context context, File[] pastasArquivos) {
        this.context = context;
        this.pastasArquivos = pastasArquivos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_arquivos,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // Aqui ele pega a posiçaõ do arquivo para visualização e pegamos tambem o nome do arquivo para mostrar
        File arquivoSelecionado = pastasArquivos[position];
        holder.textView.setText(arquivoSelecionado.getName());

        // Verificamos se ele é um diretorio ou não
        if(arquivoSelecionado.isDirectory()){
            // Se for um diretorio definimos a imagem da pasta que criamo no vetor
            holder.imageView.setImageResource(R.drawable.ic_pasta);
        }else{
            // Caso contrario definimos o icone como arquivo
            holder.imageView.setImageResource(R.drawable.ic_arquivo);
        }

        // Ação para acessar as pastas abrindo elas
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ao clicar em um arquivo, ele deve verificar se é um diretorio ou se é um arquivo
                if(arquivoSelecionado.isDirectory()){
                    // Se for um diretorio (pasta) devemos seguir para dentro da pasta
                    // Aqui vamos para a pasta de arquivos e passamos o caminho absolto
                    // Agora estamos passando o caminho do arquivo selecionado, no caso a pasta selecionada, para que ele abra
                    // o caminho na lista de atividades
                    Intent intent = new Intent(context, ArquivosActivity.class);
                    String path = arquivoSelecionado.getAbsolutePath();
                    intent.putExtra("path",path);

                    // Aqui passamos a flg para informar que deve ser aberta uma nova activity ao clicar na pasta e não a mesma
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    // Se for um arquivo, ele deve abrir o arquivo
                    try {
                        // Dessa forma ele vai gerar uma nova açõa de visualização e abrir o arquivo
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);

                        // Nesse caso passamos apenas o arquivo de imagem para ser aberto, abrindo um try catch para testes,
                        // caso nao seja imagem o sistema retornará uma mensagem dizendo que o arquivo nao pode ser aberto
                        String type = "image/*";

                        // Aaui passamos o tipo de arquivo que sera aberto e os dados desse arquivo. Criamos a variavel type
                        // para passamos o tipo de arquivo que sera aberto
                        intent.setDataAndType(Uri.parse(arquivoSelecionado.getAbsolutePath()), type);

                        // Aponta que é uma nova activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        // Caso o arquivo seja de outro tipo informamos que não é possivel abri-lo
                    }catch (Exception e){
                        Toast.makeText(context.getApplicationContext(),"Não é possível abrir o arquivo",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Aqui definimos um Menu PopPup para podermos mover, Deletar ou Renomear a pastas ou arquivo
        // Aqui chamamos um CLickLongo para abrir o lek de opções popup
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // Aqui definimos o menu propriamente dito e add os menus que queremos
                PopupMenu popupMenu = new PopupMenu(context,view);
                popupMenu.getMenu().add("DELETAR");
                popupMenu.getMenu().add("MOVER");
                popupMenu.getMenu().add("RENOMEAR");

                // Aqui definimos a ação para caso o usuario selecione algumas das opções do popupo
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        // Se o click for menu de nome igual a deletar, então ele chama um boolano para deletar o arquivo
                        // selecionado
                        if(item.getTitle().equals("DELETAR")){
                            boolean deleted = arquivoSelecionado.delete();
                            // Se for deletado com sucesso mostrará uma imagem e ocultaremos a visualização
                            if(deleted){
                                Toast.makeText(context.getApplicationContext(),"DELETADO ",Toast.LENGTH_SHORT).show();
                                view.setVisibility(View.GONE);
                            }
                        }

                        // Se o click for menu de nome igual a Mover, então ele chama um boolano para deletar o arquivo
                        // selecionado
                        if(item.getTitle().equals("MOVER")){
                            Toast.makeText(context.getApplicationContext(),"MOVIDO ",Toast.LENGTH_SHORT).show();

                        }

                        // Se o click for menu de nome igual a Renomar, então ele chama um boolano para deletar o arquivo
                        // selecionado
                        if(item.getTitle().equals("RENOMEAR")){
                            Toast.makeText(context.getApplicationContext(),"RENOMEADO ",Toast.LENGTH_SHORT).show();

                        }
                        return true;
                    }
                });

                popupMenu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return pastasArquivos.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_nome_arquivo);
            imageView = itemView.findViewById(R.id.img_icone);
        }
    }
}
