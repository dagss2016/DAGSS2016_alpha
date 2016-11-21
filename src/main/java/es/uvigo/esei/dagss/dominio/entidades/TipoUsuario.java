/*
 Práctica Java EE 7, DAGSS 2013/14 (ESEI, U. de Vigo)
 */

package es.uvigo.esei.dagss.dominio.entidades;


public enum TipoUsuario {
    ADMINISTRADOR ("ADMINISTRADOR"), 
    PACIENTE      ("PACIENTE"), 
    MEDICO        ("MEDICO"), 
    FARMACIA      ("FARMACIA");    
    
    private final String etiqueta;

    private TipoUsuario(String etiqueta) {
        this.etiqueta = etiqueta;
    }
   
    public String getEtiqueta() {
        return etiqueta;
    }    
}
