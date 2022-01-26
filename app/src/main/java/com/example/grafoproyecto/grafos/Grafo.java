/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.grafoproyecto.grafos;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Yeri
 */
public class Grafo {
    protected List<List<Integer>> listaDeAdyacencias;

    public Grafo() {
        this.listaDeAdyacencias = new LinkedList<>();
    }

    public Grafo(int nroDeVertices) throws ExcepcionNumVerticesInvalido {
        if (nroDeVertices <= 0) {
            throw new ExcepcionNumVerticesInvalido();
        }

        this.listaDeAdyacencias = new LinkedList<>();
        for (int i = 0; i < nroDeVertices; i++) {
            this.insertarVertice();
        }
    }

    public void insertarVertice() {
        List<Integer> adyacenteVerticeAInsertar = new LinkedList<>();
        this.listaDeAdyacencias.add(adyacenteVerticeAInsertar);
    }

    public int cantidadDeVertices() {
        return listaDeAdyacencias.size();
    }

    public int gradoDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        return adyacentesDelVertice.size();
    }

    public void validarVertice(int posDeVertice) {
        if (posDeVertice < 0 || posDeVertice >= this.cantidadDeVertices()) {
            throw new IllegalArgumentException("no existe vertice en la posicion" + posDeVertice + " en su grafo");
        }
    }

    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> adyacentesDelVerticeOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        adyacentesDelVerticeOrigen.add(posVerticeDestino);
        Collections.sort(adyacentesDelVerticeOrigen);
        if (posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacentesDelVerticeDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            adyacentesDelVerticeDestino.add(posVerticeOrigen);
            Collections.sort(adyacentesDelVerticeDestino);
        }
    }

    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino) {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<Integer> adyacentesDelVerticeOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        return adyacentesDelVerticeOrigen.contains(posVerticeDestino);
    }
    
    public Iterable<Integer> adyacentesDeVertice(int posDeVertice){
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        Iterable<Integer> iterableDeAdyacentesDeVertice = adyacentesDelVertice;
        return iterableDeAdyacentesDeVertice;
    }
    
    public int cantidadDeAristas(){
        int aristas = 0;
        int lazos = 0;
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            List<Integer> aristasDelVertice = this.listaDeAdyacencias.get(i);
            for (int j = 0; j < aristasDelVertice.size(); j++) {
                if (aristasDelVertice.get(j) == i) {
                    lazos++;
                }else{
                    aristas++;
                }
            }
        }
        return (aristas/2) + lazos;
    }
    
    public void eliminarVertice(int posVerticeAEliminar){
        validarVertice(posVerticeAEliminar);
        this.listaDeAdyacencias.remove(posVerticeAEliminar);
        for (List<Integer> adyacentesDeUnVertice : this.listaDeAdyacencias) {
            int posicionDeVerticeAEliminarEnAdy = adyacentesDeUnVertice.indexOf(posVerticeAEliminar);
            if (posicionDeVerticeAEliminarEnAdy >= 0) {
                adyacentesDeUnVertice.remove(posicionDeVerticeAEliminarEnAdy);
            }
            
            for (int i = 0; i < adyacentesDeUnVertice.size(); i++) {
                int posicionDeAdyacente = adyacentesDeUnVertice.get(i);
                if (posicionDeAdyacente > posVerticeAEliminar) {
                    adyacentesDeUnVertice.set(i, posicionDeAdyacente - 1);
                }
            }
        }
    }
    
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacentesDelVerticeOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        int posicionAristaAEliminar = adyacentesDelVerticeOrigen.indexOf(posVerticeDestino);
        adyacentesDelVerticeOrigen.remove(posicionAristaAEliminar);
        if (posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacentesDelVerticeDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            posicionAristaAEliminar = adyacentesDelVerticeDestino.indexOf(posVerticeOrigen);
            adyacentesDelVerticeDestino.remove(posicionAristaAEliminar);
        }
    }
    
    
    public boolean hayCiclo() throws ExcepcionNumVerticesInvalido, ExcepcionAristaYaExiste{
        DFS dfs = new DFS(this);
        return dfs.hayCiclo();
    }
    
    public void eliminarAdyacencias(){
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            this.listaDeAdyacencias.get(i).clear();
        }
    }
    
    public int cantidadDeIslas(){
        int islas = 0;
        DFS dfs  = new DFS(this, 0);
        islas++;
        for (int i = 1; i < this.cantidadDeVertices(); i++) {
            if (!dfs.controlDeMarcados.estaVerticeMarcado(i)) {
                dfs.procesarDFS(i);
                islas++;
            }
        }
        return islas;
    }
}
