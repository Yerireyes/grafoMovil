package com.example.grafoproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grafoproyecto.grafos.ExcepcionAristaNoExiste;
import com.example.grafoproyecto.grafos.ExcepcionAristaYaExiste;
import com.example.grafoproyecto.redsocial.Persona;
import com.example.grafoproyecto.redsocial.RedSocial;
import com.example.grafoproyecto.redsocial.RedSocialSingleton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PersonActivity extends AppCompatActivity {
    RedSocial redSocial;
    Persona persona;
    TextView textView, datosTextView;
    ListView listView, listViewBuscarAmigos;
    List<Integer> listaDeAmigos;
    List<Integer> listaDeNoAmigos;
    Button botonEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        redSocial = RedSocialSingleton.getRedSocial();
        int id = getIntent().getIntExtra("id", 0);
        this.persona = redSocial.getPersona(id);
        this.cargarLista();
        datosTextView = findViewById(R.id.datosTextView);
        datosTextView.setText(
                this.persona.getId() + "\n" +
                this.persona.getNombre() + "\n" +
                this.persona.getApellido() + "\n" +
                this.persona.getEdad() + "\n" +
                this.persona.getCorreo() + "\n" +
                this.persona.getTelefono()
        );
        listView.setOnItemClickListener(new PersonActivity.ListViewListener());
        listView.setOnItemLongClickListener(new PersonActivity.ListViewLongListener());
        listViewBuscarAmigos.setOnItemClickListener(new PersonActivity.ListViewBuscarAmigosListener());
        listViewBuscarAmigos.setOnItemLongClickListener(new PersonActivity.ListViewBuscarAmigosLongListener());
        botonEliminar = findViewById(R.id.botonEliminar);
        botonEliminar.setOnClickListener(new PersonActivity.botonEliminarListener());
    }

    public void cargarLista(){
        this.listaDeAmigos = new LinkedList<>();
        this.listaDeNoAmigos = new LinkedList<>();
        Iterable<Integer> listaDeAdyacencias = redSocial.getListaDeAmigos(persona.getId());
        listView = (ListView) findViewById(R.id.listview);
        ArrayList<String> arrayList = new ArrayList<>();
        for (Integer adyacenciaEnTurno : listaDeAdyacencias) {
            arrayList.add(redSocial.getPersona(adyacenciaEnTurno).getNombre() + " " + redSocial.getPersona(adyacenciaEnTurno).getApellido());
            this.listaDeAmigos.add(adyacenciaEnTurno);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        listViewBuscarAmigos = (ListView) findViewById(R.id.listViewBuscarAmigos);
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (int i = 0; i < redSocial.cantidadDePersonas(); i++){
            boolean existeUnoIgual = false;
            for (Integer adyacenciaEnTurno : listaDeAdyacencias) {
                if (!existeUnoIgual && adyacenciaEnTurno == i){
                    existeUnoIgual = true;
                }
            }
            if (!existeUnoIgual && i != persona.getId()){
                arrayList2.add(redSocial.getPersona(i).getNombre() + " " + redSocial.getPersona(i).getApellido());
                this.listaDeNoAmigos.add(i);
            }
        }
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList2);
        listViewBuscarAmigos.setAdapter(arrayAdapter2);
    }
    private class ListViewListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
            intent.putExtra("id", listaDeAmigos.get(i));
            startActivity(intent);
        }
    }

    private class botonEliminarListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            redSocial.eliminarPersona(persona.getId());
            Intent intent = new Intent(PersonActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private class ListViewLongListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            try {
                redSocial.eliminarAmigo(persona.getId(), listaDeAmigos.get(i));
            } catch (ExcepcionAristaNoExiste excepcionAristaNoExiste) {
                excepcionAristaNoExiste.printStackTrace();
            }
            cargarLista();
            return true;
        }
    }

    private class ListViewBuscarAmigosListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
            intent.putExtra("id", listaDeNoAmigos.get(i));
            startActivity(intent);
        }
    }

    private class ListViewBuscarAmigosLongListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            try {
                redSocial.añadirAmigo(persona.getId(), listaDeNoAmigos.get(i));
            } catch (ExcepcionAristaYaExiste excepcionAristaYaExiste) {
                excepcionAristaYaExiste.printStackTrace();
            }
            cargarLista();
            return true;
        }
    }
}