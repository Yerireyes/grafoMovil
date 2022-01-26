/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.grafoproyecto.grafos;

/**
 *
 * @author Yeri
 */
public class ExcepcionAristaYaExiste extends Exception {

    /**
     * Creates a new instance of <code>ExcepcionAristaYaExiste</code> without
     * detail message.
     */
    public ExcepcionAristaYaExiste() {
        super("arista ya existe en su grafo");
    }

    /**
     * Constructs an instance of <code>ExcepcionAristaYaExiste</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionAristaYaExiste(String msg) {
        super(msg);
    }
}
