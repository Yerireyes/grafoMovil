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
public class ExcepcionNumVerticesInvalido extends Exception {

    /**
     * Creates a new instance of <code>ExcepcionNumVerticesInvalido</code>
     * without detail message.
     */
    public ExcepcionNumVerticesInvalido() {
        super("cantiad de vertices invalido");
    }

    /**
     * Constructs an instance of <code>ExcepcionNumVerticesInvalido</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionNumVerticesInvalido(String msg) {
        super(msg);
    }
}
