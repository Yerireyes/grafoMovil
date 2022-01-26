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
public class DiGrafo extends Grafo {

    public DiGrafo() {
        super();
    }

    public DiGrafo(int nroDeVertices) throws ExcepcionNumVerticesInvalido {
        super(nroDeVertices);
    }

    @Override
    public int cantidadDeAristas() {
        int aristas = 0;
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            List<Integer> aristasDelVertice = this.listaDeAdyacencias.get(i);
            for (int j = 0; j < aristasDelVertice.size(); j++) {
                aristas++;
            }
        }
        return aristas;
    }

    @Override
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> adyacentesDelVerticeOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        adyacentesDelVerticeOrigen.add(posVerticeDestino);
        Collections.sort(adyacentesDelVerticeOrigen);
    }

    @Override
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacentesDelVerticeOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        int posicionAristaAEliminar = adyacentesDelVerticeOrigen.indexOf(posVerticeDestino);
        adyacentesDelVerticeOrigen.remove(posicionAristaAEliminar);
    }

    @Override
    public int gradoDeVertice(int posDeVertice) {
        throw new UnsupportedOperationException("metodo no soportado en grafos dirigidos");
    }

    public int gradoDeEntrada(int posDeVertice) {
        int aristas = 0;
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            List<Integer> aristasDelVertice = this.listaDeAdyacencias.get(i);
            for (int j = 0; j < aristasDelVertice.size(); j++) {
                if (aristasDelVertice.get(j) == posDeVertice) {
                    aristas++;
                }
            }
        }
        return aristas;
    }

    public int gradoDeSalida(int posDeVertice) {
        return super.gradoDeVertice(posDeVertice);
    }

    public boolean hayCicloDiGrafo() {
        List<Integer> recorrido = new LinkedList<>();
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            recorrido.add(0);
        }
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            if (recorrido.get(i) != 1) {
                simularDFS(recorrido, i);
            }
        }
        return !todoElRecorridoMarcado(recorrido);
    }

    private void simularDFS(List<Integer> recorrido, int posDeVerticeEnTurno) {
        recorrido.set(posDeVerticeEnTurno, 1);
        Iterable<Integer> adyacentesDeVerticeEnTurno = this.adyacentesDeVertice(posDeVerticeEnTurno);
        for (Integer posDeVerticeAdy : adyacentesDeVerticeEnTurno) {
            if (recorrido.get(posDeVerticeAdy) != 1) {
                simularDFS(recorrido, posDeVerticeAdy);
            } else {
                return;
            }
        }
        recorrido.set(posDeVerticeEnTurno, 2);

    }

    private boolean todoElRecorridoMarcado(List<Integer> recorrido) {
        for (int i = 0; i < recorrido.size(); i++) {
            if (recorrido.get(i) != 2) {
                return false;
            }
        }
        return true;
    }
    
    public int cantidadDeIslasDiGrafo(){
        DFS dfs = new DFS(this,0);
        return dfs.cantidadDeIslas();
    }
    
    public boolean esDebilmenteConexo(){
        DFS dfs = new DFS(this, 0);
        return dfs.esDebilmenteConexo();
    }
    
    public boolean esFuertementeConexo(){
        DFS dfs = new DFS(this, 0);
        if(!dfs.controlDeMarcados.estanTodosMarcados()){
            return false;
        }
            for (int i = 1; i < this.cantidadDeVertices(); i++) {
                dfs.controlDeMarcados.desmarcarTodos();
                dfs.procesarBFS(i);
                if (!dfs.controlDeMarcados.estanTodosMarcados()) {
                    return false;
                }
            }
        return true;
    }
}
