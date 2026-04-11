public class Extraccion {
    private Donante voluntario;
    private String fechaExtraccion;
    private int volumenExtraido;
    private boolean seSintioMal;
   
    public Extraccion (Donante voluntario, String fechaExtraccion, int volumenExtraido, boolean seSintioMal) {
        this.voluntario = voluntario;
        this.fechaExtraccion = fechaExtraccion;
        this.volumenExtraido = volumenExtraido;
        this.seSintioMal = seSintioMal;
    }   
    
    //Getters
    public Donante getVoluntario() { return voluntario; }
    public String getFechaExtraccion() { return fechaExtraccion; }
    public int getVolumenExtraido() { return volumenExtraido; }
    public boolean getSeSintioMal() { return seSintioMal; }
    
    //Setters
    public void setVoluntario(Donante voluntario) { this.voluntario = voluntario; }
    public void setFechaExtraccion(String fechaExtraccion) { this.fechaExtraccion = fechaExtraccion; }
    public void setVolumenExtraido(int volumenExtraido) { this.volumenExtraido = volumenExtraido; }
    public void setSeSintioMal(boolean seSintioMal) { this.seSintioMal = seSintioMal; }
}
