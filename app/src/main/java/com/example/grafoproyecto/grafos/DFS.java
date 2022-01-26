/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.grafoproyecto.grafos;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Yeri
 */
public class DFS {

    public RecorridoUtils controlDeMarcados;
    public Grafo grafo;
    public DiGrafo diGrafo;
    private List<Integer> recorrido;

    public DFS(Grafo unGrafo, int posVerticeDePartida) {
        this.grafo = unGrafo;
        grafo.validarVertice(posVerticeDePartida);
        recorrido = new LinkedList<>();
        controlDeMarcados = new RecorridoUtils(grafo.cantidadDeVertices());
        procesarDFS(posVerticeDePartida);
    }

    public DFS(Grafo unGrafo) {
        this.grafo = unGrafo;
        controlDeMarcados = new RecorridoUtils(grafo.cantidadDeVertices());
    }

    public boolean hayCiclo() throws ExcepcionNumVerticesInvalido, ExcepcionAristaYaExiste {
        Grafo newGrafo = new Grafo(this.grafo.cantidadDeVertices());
        for (int i = 0; i < grafo.cantidadDeVertices(); i++) {
            if (!controlDeMarcados.estaVerticeMarcado(i)) {
                hayCicloGrafoAuxiliar(newGrafo, i);
            }
        }
        return !cantidadDeAdyacenciasIguales(newGrafo);
    }

    public void hayCicloGrafoAuxiliar(Grafo newGrafo, int posDeVerticeEnTurno) throws ExcepcionAristaYaExiste {
        controlDeMarcados.marcarVertice(posDeVerticeEnTurno);
        Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice(posDeVerticeEnTurno);
        for (Integer posDeVerticeAdy : adyacentesDeVerticeEnTurno) {
            if (!controlDeMarcados.estaVerticeMarcado(posDeVerticeAdy)) {
                newGrafo.insertarArista(posDeVerticeEnTurno, posDeVerticeAdy);
                hayCicloGrafoAuxiliar(newGrafo, posDeVerticeAdy);
            }
        }
    }

    public DFS(DiGrafo unGrafo, int posVerticeDePartida) {
        this.diGrafo = unGrafo;
        diGrafo.validarVertice(posVerticeDePartida);
        recorrido = new LinkedList<>();
        controlDeMarcados = new RecorridoUtils(diGrafo.cantidadDeVertices());
        procesarBFS(posVerticeDePartida);
    }

    public void procesarBFS(int posDeVerticeEnTurno) {
        controlDeMarcados.marcarVertice(posDeVerticeEnTurno);
        recorrido.add(posDeVerticeEnTurno);
        Iterable<Integer> adyacentesDeVerticeEnTurno = diGrafo.adyacentesDeVertice(posDeVerticeEnTurno);
        for (Integer posDeVerticeAdy : adyacentesDeVerticeEnTurno) {
            if (!controlDeMarcados.estaVerticeMarcado(posDeVerticeAdy)) {
                procesarBFS(posDeVerticeAdy);
            }
        }

    }

    public void procesarDFS(int posDeVerticeEnTurno) {
        controlDeMarcados.marcarVertice(posDeVerticeEnTurno);
        recorrido.add(posDeVerticeEnTurno);
        Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice(posDeVerticeEnTurno);
        for (Integer posDeVerticeAdy : adyacentesDeVerticeEnTurno) {
            if (!controlDeMarcados.estaVerticeMarcado(posDeVerticeAdy)) {
                procesarDFS(posDeVerticeAdy);
            }
        }

    }
    public boolean hayCaminosATodos() {
        return controlDeMarcados.estanTodosMarcados();
    }

    public Iterable<Integer> obtenerRecorrido() {
        return this.recorrido;
    }

    public boolean hayCaminoVertice(int posDeVerticeDestino) {
        diGrafo.validarVertice(posDeVerticeDestino);
        return controlDeMarcados.estaVerticeMarcado(posDeVerticeDestino);
    }

    public boolean esDebilmenteConexo() {
        while (this.buscarVerticeSinMarcar() != -1) {
            procesarBFS(this.buscarVerticeSinMarcar());
        }
        return this.hayCaminosATodos();
    }

    public int buscarVerticeSinMarcar() {
        for (int i = 0; i < diGrafo.cantidadDeVertices(); i++) {
            if (!controlDeMarcados.estaVerticeMarcado(i)) {
                List<Integer> listaDeAdyacencia = diGrafo.listaDeAdyacencias.get(i);
                for (int j = 0; j < listaDeAdyacencia.size(); j++) {
                    if (this.hayCaminoVertice(listaDeAdyacencia.get(j))) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    private boolean cantidadDeAdyacenciasIguales(Grafo newGrafo) {
        for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
            if (this.grafo.listaDeAdyacencias.get(i).size() != newGrafo.listaDeAdyacencias.get(i).size()) {
                return false;
            }
        }
        return true;
    }

    public int cantidadDeIslas() {
        int islas = 0;
        while (this.buscarVerticeSinMarcar() != -1) {
            procesarBFS(this.buscarVerticeSinMarcar());
        }
        islas++;
        for (int i = 1; i < diGrafo.cantidadDeVertices(); i++) {
            if (!controlDeMarcados.estaVerticeMarcado(i)) {
                procesarBFS(i);
                while (this.buscarVerticeSinMarcar() != -1) {
                    procesarBFS(this.buscarVerticeSinMarcar());
                }
                islas++;
            }
        }
        return islas;
    }
    
}
