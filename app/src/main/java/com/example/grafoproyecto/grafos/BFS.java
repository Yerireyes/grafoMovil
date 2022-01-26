/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.grafoproyecto.grafos;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Yeri
 */
public class BFS {
    public RecorridoUtils controlDeMarcados;
    public Grafo grafo;
    private List<Integer> recorrido;
    
    public BFS(Grafo unGrafo, int posVerticeDePartida){
        this.grafo = unGrafo;
        grafo.validarVertice(posVerticeDePartida);
        recorrido = new LinkedList<>();
        controlDeMarcados = new RecorridoUtils(grafo.cantidadDeVertices());
        ejecutarBFS(posVerticeDePartida) ;
    }
    
    private void ejecutarBFS(int posVerticeDePartida){
        Queue<Integer> cola = new LinkedList<>();
        cola.offer(posVerticeDePartida);
        controlDeMarcados.marcarVertice(posVerticeDePartida);
        do {
            int posVerticeEnTurno = cola.poll();
            recorrido.add(posVerticeEnTurno);
            Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice(posVerticeEnTurno);
            for(Integer posDeVerticeAdy : adyacentesDeVerticeEnTurno){
                if (!controlDeMarcados.estaVerticeMarcado(posDeVerticeAdy)) {
                    cola.offer(posDeVerticeAdy);
                    controlDeMarcados.marcarVertice(posDeVerticeAdy);
                }
            }
        } while (!cola.isEmpty());
        
    }
    
    public boolean hayCaminosATodos(){
        return controlDeMarcados.estanTodosMarcados();
    }
    
    public Iterable<Integer> obtenerRecorrido(){
        return this.recorrido;
    }
    
    public boolean hayCaminoVertice(int posDeVerticeDestino){
        grafo.validarVertice(posDeVerticeDestino);
        return controlDeMarcados.estaVerticeMarcado(posDeVerticeDestino);
    }
}
