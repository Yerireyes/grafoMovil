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
public class RecorridoUtils {
    private List<Boolean> marcados;
    
    public RecorridoUtils(int nroVertices){
        marcados = new LinkedList<>();
        for (int i = 0; i < nroVertices; i++) {
            marcados.add(Boolean.FALSE);
        }
    }
    
    public void desmarcarTodos(){
        for (int i = 0; i < marcados.size(); i++) {
            marcados.set(i, Boolean.FALSE);
        }
    }
    
    public boolean estaVerticeMarcado(int posDeVertice){
        return this.marcados.get(posDeVertice);
    }
    
    public boolean estanTodosMarcados(){
        for (Boolean marcado : marcados) {
            if (!marcado) {
                return false;
            }
        }
        return true;
    }
    
    public void marcarVertice(int posDeVertice){
        this.marcados.set(posDeVertice, Boolean.TRUE);
    }
}
