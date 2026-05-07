package entidades;


import entidades.Campaña;

public class CampañaEnfocada extends Campaña  {
    private String grupoObjetivo;
    private float porcentajeMeta; // Porcentaje de las donaciones que deben pertenecer al grupo objetivo
    
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
    
    //Metodos
    @Override
    public void validar() throws Exception {
        // Se validan las reglas de la clase padre
        super.validar();
        
        // Ahora se validan las reglas de la clase hija
        // Valida que el grupo objetivo no este en blanco
        if (this.grupoObjetivo == null || this.grupoObjetivo.trim().isEmpty()) {
            throw new Exception("El grupo objetivo no puede estar vacío.");
        }
        // Valida que el porcentaje de la meta este entre 1% y 100%
        if (this.porcentajeMeta <= 0 || this.porcentajeMeta > 100) {
            throw new Exception("El porcentaje de la meta debe estar entre 1% y 100%.");
        }
    }
}