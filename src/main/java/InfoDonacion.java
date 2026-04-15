import java.time.LocalDate;

public class InfoDonacion {
    private String nombreCampaña;
    private LocalDate fechaCampaña;
    private int volumen;
    private boolean seSintioMal;

    public InfoDonacion(String nombreCampaña, LocalDate fechaCampaña, int volumen, boolean seSintioMal) {
        this.nombreCampaña = nombreCampaña;
        this.fechaCampaña = fechaCampaña;
        this.volumen = volumen;
        this.seSintioMal = seSintioMal;
    }

    //Getters
    public String getNombreCampaña() { return nombreCampaña; }
    public LocalDate getFechaCampaña() { return fechaCampaña; }
    public int getVolumen() { return volumen; }
    public boolean getSeSintioMal() { return seSintioMal; }

    //Setters
    public void setNombreCampaña(String nombreCampaña) { this.nombreCampaña = nombreCampaña; }
    public void setFechaCampaña(LocalDate fechaCampaña) { this.fechaCampaña = fechaCampaña; }
    public void setVolumen(int volumen) { this.volumen = volumen; }
    public void setSeSintioMal(boolean seSintioMal) { this.seSintioMal = seSintioMal; }
}