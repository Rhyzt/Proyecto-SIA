package entidades;

import java.time.LocalDate;

public class InfoDonacion {
    private String idCampaña;
    private int volumen;
    private boolean seSintioMal;

    public InfoDonacion(String idCampaña, int volumen, boolean seSintioMal) {
        this.idCampaña = idCampaña;
        this.volumen = volumen;
        this.seSintioMal = seSintioMal;
    }

    //Getters
    public String getNombreCampaña() { return idCampaña; }
    public int getVolumen() { return volumen; }
    public boolean getSeSintioMal() { return seSintioMal; }

    //Setters
    public void setNombreCampaña(String idCampaña) { this.idCampaña = idCampaña; }
    public void setVolumen(int volumen) { this.volumen = volumen; }
    public void setSeSintioMal(boolean seSintioMal) { this.seSintioMal = seSintioMal; }
}