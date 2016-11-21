/*
 Práctica Java EE 7, DAGSS 2015/16 (ESEI, U. de Vigo)
 */
package es.uvigo.esei.dagss.dominio.entidades;

public enum EstadoCita {

    PLANIFICADA ("PLANIFICADA"), 
    ANULADA     ("ANULADA"), 
    COMPLETADA  ("COMPLETADA"), 
    AUSENTE     ("AUSENTE");

    private final String etiqueta;

    private EstadoCita(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
