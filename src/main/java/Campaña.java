import java.util.ArrayList;
import java.util.List;

public class Campaña {
    private int idCampaña;
    private String nombreCampaña;
    private String ubicacion;
    private String fechaCampaña;
    private int metaDonaciones;
    
    public Campaña(int idCampaña, String nombreCampaña, String ubicacion, String fechaCampaña, int metaDonaciones) {
        this.idCampaña = idCampaña;
        this.nombreCampaña = nombreCampaña;
        this.ubicacion = ubicacion;
        this.fechaCampaña = fechaCampaña;
        this.metaDonaciones = metaDonaciones;
    }
    
    //Getters
    public int getIdCampaña() { return idCampaña; }
    public String getNombreCampaña() { return nombreCampaña; }
    public String getUbicacion() { return ubicacion; }
    public String getFechaCampaña() { return fechaCampaña; }
    public int getMetaDonaciones() { return metaDonaciones; }
    
    //Setters
    public void setIdCampaña(int idCampaña){ this.idCampaña = idCampaña; }
    public void setNombreCampaña(String nombreCampaña){ this.nombreCampaña = nombreCampaña; }
    public void setUbicacion(String ubicacion){ this.ubicacion = ubicacion; }
    public void setFechaCampaña(String fechaCampaña){ this.fechaCampaña = fechaCampaña; }
    public void setMetaDonaciones(int metaDonaciones){ this.metaDonaciones = metaDonaciones; }
}