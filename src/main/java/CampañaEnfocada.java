public class CampañaEnfocada extends Campaña {
    private String grupoObjetivo;
    private float bonoPrioridad;
    
    public CampañaEnfocada(int idCampaña, String nombreCampaña, String ubicacion, String fechaCampaña, int metaDonaciones, String grupoObjetivo, float bonoPrioridad) {
        super(idCampaña, nombreCampaña, ubicacion, fechaCampaña, metaDonaciones);
        this.grupoObjetivo = grupoObjetivo;
        this.bonoPrioridad = bonoPrioridad;
    }
    
    //Getters
    public String getGrupoObjetivo() { return grupoObjetivo; }
    public float getBonoPrioridad() { return bonoPrioridad; }
    
    //Setters
    public void setGrupoObjetivo(String grupoObjetivo) { this.grupoObjetivo = grupoObjetivo; }
    public void setBonoPrioridad(float bonoPrioridad) { this.bonoPrioridad = bonoPrioridad; }
    
    
}
