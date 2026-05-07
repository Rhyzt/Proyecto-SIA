package entidades;

public abstract class Persona implements procesamiento.Validadores {
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
    
    @Override
    public void validar() throws Exception {
        //Valida que el rut sea valido
        if (!procesamiento.Validadores.esRutValido(this.rut)) {
            throw new Exception(procesamiento.Validadores.obtenerMensajeErrorRUT(this.rut));
        }
        // Valida que el nombre no tenga caracteres invalidos
        if (!procesamiento.Validadores.esNombreValido(this.nombre)) {
            throw new Exception(procesamiento.Validadores.obtenerMensajeErrorNombre());
        }
    }
}