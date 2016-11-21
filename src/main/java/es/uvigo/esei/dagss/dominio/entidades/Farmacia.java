/*
 Práctica Java EE 7, DAGSS 2013/14 (ESEI, U. de Vigo)
 */
package es.uvigo.esei.dagss.dominio.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue(value = "FARMACIA")
public class Farmacia extends Usuario implements Serializable {

    @Size(min = 0, max = 75)
    @Column(length = 75, nullable = false)
    String nombreFarmacia;

    @Size(min = 9, max = 9)
    @Column(length = 9, nullable = false)
    String nif;

    @Embedded
    Direccion direccion;


    public Farmacia() {
        super();
        this.tipoUsuario = TipoUsuario.FARMACIA;  // Es necesario hacerlo explícitamente?        
    }

    public Farmacia(String nombreFarmacia, String nif, Direccion direccion) {
        super();
        this.tipoUsuario = TipoUsuario.FARMACIA;  // Es necesario hacerlo explícitamente?        

        this.nombreFarmacia = nombreFarmacia;
        this.nif = nif;
        this.direccion = direccion;
    }

    public String getNombreFarmacia() {
        return nombreFarmacia;
    }

    public void setNombreFarmacia(String nombreFarmacia) {
        this.nombreFarmacia = nombreFarmacia;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    
}
