package entidades;

import java.time.LocalDate;

public class Extraccion implements procesamiento.Validadores {
    private Donante voluntario;
    private LocalDate fechaExtraccion;
    private int volumenExtraido;
    private boolean seSintioMal;
   
    public Extraccion (Donante voluntario, LocalDate fechaExtraccion, int volumenExtraido, boolean seSintioMal) {
        this.voluntario = voluntario;
        this.fechaExtraccion = fechaExtraccion;
        this.volumenExtraido = volumenExtraido;
        this.seSintioMal = seSintioMal;
    }   
    
    //Getters
    public Donante getVoluntario() { return voluntario; }
    public LocalDate getFechaExtraccion() { return fechaExtraccion; }
    public int getVolumenExtraido() { return volumenExtraido; }
    public boolean getSeSintioMal() { return seSintioMal; }
    
    //Setters
    public void setVoluntario(Donante voluntario) { this.voluntario = voluntario; }
    public void setFechaExtraccion(LocalDate fechaExtraccion) { this.fechaExtraccion = fechaExtraccion; }
    public void setVolumenExtraido(int volumenExtraido) { this.volumenExtraido = volumenExtraido; }
    public void setSeSintioMal(boolean seSintioMal) { this.seSintioMal = seSintioMal; }
    
    //Metodos
    @Override
    public void validar() throws Exception {
        // Una extraccion segura (no mayor a 600ml para evitar riesgos a la salud)
        if (this.volumenExtraido <= 0 || this.volumenExtraido > 600) {
            throw new Exception("Volumen inválido. Una extracción normal es entre 400ml y 500ml.");
        }
        // Que el donante de la extraccion exista
        if (this.voluntario == null) {
            throw new Exception("La extracción debe estar asociada a un donante válido.");
        }
    }
}
