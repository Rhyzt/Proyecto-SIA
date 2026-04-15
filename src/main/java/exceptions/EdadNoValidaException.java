package exceptions;

public class EdadNoValidaException extends Exception{
    public EdadNoValidaException(int edad) {
        super("La edad no es apta. El voluntario debe tener entre 18 y 65 años");
    }
}
