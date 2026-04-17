package entidades;

import java.time.LocalDate;

public class InfoDonacion extends Extraccion {
    private String idCampaña;

    public InfoDonacion(Donante voluntario, LocalDate fechaExtraccion, int volumenExtraido, boolean seSintioMal, String idCampaña) {
        super(voluntario, fechaExtraccion, volumenExtraido, seSintioMal);
        this.idCampaña = idCampaña;
    }

    //Getters
    public String getIdCampaña() { return idCampaña; }

    //Setters
    public void setIdCampaña(String idCampaña) { this.idCampaña = idCampaña; }
 
}