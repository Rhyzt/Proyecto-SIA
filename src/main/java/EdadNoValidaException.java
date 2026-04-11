/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andre
 */
public class EdadNoValidaException extends Exception{
    public EdadNoValidaException(int edad) {
        super("La edad no es apta. El voluntario debe tener entre 18 y 65 años");
    }
}
