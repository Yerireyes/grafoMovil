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
import android.widget.Toast;

import com.example.grafoproyecto.grafos.ExcepcionAristaYaExiste;
import com.example.grafoproyecto.redsocial.RedSocial;
import com.example.grafoproyecto.redsocial.RedSocialSingleton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    RedSocial redSocial;
    Button botonCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (RedSocialSingleton.getRedSocial() == null){
            try {
                redSocial = new RedSocial();
            } catch (ExcepcionAristaYaExiste excepcionAristaYaExiste) {
                excepcionAristaYaExiste.printStackTrace();
            }
            RedSocialSingleton.setRedSocial(redSocial);
        }else{
            redSocial = RedSocialSingleton.getRedSocial();
        }
        listView = (ListView) findViewById(R.id.listview);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < redSocial.cantidadDePersonas(); i++) {
            arrayList.add(redSocial.getPersona(i).getNombre() + " " + redSocial.getPersona(i).getApellido());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new ListViewListener());
        botonCrear = findViewById(R.id.botonCrear);
        botonCrear.setOnClickListener(new botonCrearListener());
    }


    private class ListViewListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(MainActivity.this, PersonActivity.class);
            intent.putExtra("id", i);
            startActivity(intent);
        }
    }

    private class botonCrearListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, CreateUserActivity.class);
            startActivity(intent);
        }
    }
}