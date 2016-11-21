/*
 Práctica Java EE 7, DAGSS 2015/16 (ESEI, U. de Vigo)
 */

package es.uvigo.esei.dagss.dominio.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/* No se mapea a BD como entidad/tabla, seran atributos */
@Embeddable
public class Direccion implements Serializable {
    @Size(min = 0, max = 75)
    @Column(length = 75, nullable = false)
    String domicilio;

    @Size(min = 0, max = 30)
    @Column(length = 30, nullable = false)
    String localidad;

    @Size(min = 5, max = 5)
    @Column(length = 5, nullable = false)
    String codigoPostal;

    @Size(min = 0, max = 30)
    @Column(length = 30, nullable = false)
    String provincia;

    public Direccion() {
    }

    public Direccion(String domicilio, String localidad, String codigoPostal, String provincia) {
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.codigoPostal = codigoPostal;
        this.provincia = provincia;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    
    
}
