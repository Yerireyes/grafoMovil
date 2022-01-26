package com.example.grafoproyecto.redsocial;

import androidx.core.app.ActivityCompat;

import com.example.grafoproyecto.grafos.ExcepcionAristaNoExiste;
import com.example.grafoproyecto.grafos.ExcepcionAristaYaExiste;
import com.example.grafoproyecto.grafos.Grafo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class RedSocial {
    Grafo grafo;
    List<Persona> personas;

    public RedSocial() throws ExcepcionAristaYaExiste {
        grafo = new Grafo();
        personas = new LinkedList<>();
        this.cargarPersonas();
        this.añadirAmigo(0,1);
        this.añadirAmigo(0,2);
        this.añadirAmigo(0,3);
        this.añadirAmigo(0,4);
        this.añadirAmigo(1,7);
        this.añadirAmigo(1,9);
        this.añadirAmigo(1,2);
        this.añadirAmigo(2,6);
        this.añadirAmigo(3,6);
        this.añadirAmigo(4,5);
        this.añadirAmigo(4,9);
        this.añadirAmigo(5,7);
        this.añadirAmigo(5,8);
        this.añadirAmigo(5,9);
        this.añadirAmigo(7,8);
    }

    private void cargarPersonas() {
        this.crearPersona("Erik","Reyes",20,"le_soleto@hotmail.com","78028007");
        this.crearPersona("Cecilia","Justiniano",20,"ceci@gmail.com","12345678");
        this.crearPersona("Catherine","Gomez",21,"kiracata@hotmail.com","12345678");
        this.crearPersona("Mauricio","Sauza",22,"sauza@gmail.com","12345678");
        this.crearPersona("Ibai","Llanos",28,"ibai@gmail.com","12345678");
        this.crearPersona("David","Canovas",24,"grefg@gmail.com","12345678");
        this.crearPersona("Renato","Alvarez",24,"natolml@gmail.com","12345678");
        this.crearPersona("Raul","Buhajerok",21,"spreen@hotmail.com","12345678");
        this.crearPersona("Tomas","Arbillaga",23,"robleis@hotmail.com","12345678");
        this.crearPersona("Guillermo","Diaz",28,"willy@gmail.com","12345678");
    }

    public void añadirAmigo(int idActual, int id) throws ExcepcionAristaYaExiste {
        grafo.insertarArista(idActual, id);
    }

    public void eliminarAmigo(int idActual, int id) throws ExcepcionAristaNoExiste {
        grafo.eliminarArista(idActual, id);
    }

    public void crearPersona(String nombre, String apellido, int edad, String correo, String telefono){
        Persona persona = new Persona(this.personas.size(), nombre, apellido, edad, correo, telefono);
        personas.add(persona);
        grafo.insertarVertice();
    }

    public void eliminarPersona(int id){
        grafo.eliminarVertice(id);
        personas.remove(id);
        for (int i = id; i < personas.size(); i++){
            Persona personaActual = personas.get(i);
            personaActual.setId(personaActual.getId() - 1);
        }
    }

    public Persona getPersona(int id){
        for (int i = 0; i < this.personas.size(); i++){
            Persona persona = this.personas.get(i);
            if (persona.getId() == id){
                return persona;
            }
        }
        return null;
    }

    public int cantidadDePersonas(){
        return this.personas.size();
    }

    public int cantidadDeAmigos(int id){
        return this.grafo.gradoDeVertice(id);
    }

    public Iterable<Integer> getListaDeAmigos(int id) {
        return this.grafo.adyacentesDeVertice(id);
    }
}
