package entidades;


import entidades.Campaña;

public class CampañaEnfocada extends Campaña {
    private String grupoObjetivo;
    private float porcentajeMeta;
    
    public CampañaEnfocada(String nombreCampaña, String ubicacion, String fechaCampaña, int metaDonaciones, String grupoObjetivo, float porcentajeMeta) {
        super(nombreCampaña, ubicacion, fechaCampaña, metaDonaciones);
        this.grupoObjetivo = grupoObjetivo;
        this.porcentajeMeta = porcentajeMeta;
    }
    
    //Getters
    public String getGrupoObjetivo() { return grupoObjetivo; }
    public float getPorcentajeMeta() { return porcentajeMeta; }
    
    //Setters
    public void setGrupoObjetivo(String grupoObjetivo) { this.grupoObjetivo = grupoObjetivo; }
    public void setPorcentajeMeta(float porcentajeMeta) { this.porcentajeMeta = porcentajeMeta; }
    
    
}
