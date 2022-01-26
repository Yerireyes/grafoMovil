package com.example.grafoproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.grafoproyecto.redsocial.RedSocial;
import com.example.grafoproyecto.redsocial.RedSocialSingleton;

public class CreateUserActivity extends AppCompatActivity {
    Button submit;
    RedSocial redSocial;
    EditText nombre, apellido, edad, correo, telefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        redSocial = RedSocialSingleton.getRedSocial();
        this.submit = findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new CreateUserActivity.submitListener());
        nombre = findViewById(R.id.editTextNombre);
        apellido = findViewById(R.id.editTextApellido);
        edad = findViewById(R.id.editTextEdad);
        correo = findViewById(R.id.editTextCorreo);
        telefono = findViewById(R.id.editTextTelefono);
    }

    private class submitListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            redSocial.crearPersona(nombre.getText().toString(), apellido.getText().toString(),
                    Integer.parseInt(edad.getText().toString()), correo.getText().toString(),
                    telefono.getText().toString());
            Intent intent = new Intent(CreateUserActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}