package entidades;

public abstract class Persona {
    private String nombre;
    private String rut;
    private int edad;
    private String sexo;
    
    public Persona(String nombre, String rut, int edad, String sexo) {
        this.nombre = nombre;
        this.rut = rut;
        this.edad = edad;
        this.sexo = sexo;
    }
    
    //Getters
    public String getNombre() { return nombre; }
    public String getRut() { return rut; }
    public int getEdad() { return edad; }
    public String getSexo() { return sexo; }
    
    //Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRut(String rut) { this.rut = rut; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    
    //Metodos
    public void obtenerResumen() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Rut: " + rut);
        System.out.println("edad: " + edad);
        System.out.println("Sexo: " + sexo);
    }
}