import java.time.LocalDate;

public class Extraccion {
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
}
