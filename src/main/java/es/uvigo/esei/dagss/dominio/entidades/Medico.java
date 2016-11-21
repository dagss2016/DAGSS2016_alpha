/*
 Práctica Java EE 7, DAGSS 2015/16 (ESEI, U. de Vigo)
 */
package es.uvigo.esei.dagss.dominio.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue(value = "MEDICO")
public class Medico extends Usuario implements Serializable {

    @Size(min = 0, max = 10)
    @Column(length = 10, nullable = false)
    String numeroColegiado;

    @Size(min = 9, max = 9)
    @Pattern(regexp = "[0-9]{8}[A-Z]")
    @Column(length = 9, nullable = false)
    String dni;

    @Size(min = 0, max = 30)
    @Column(length = 30, nullable = false)
    String nombre;

    @Size(min = 0, max = 50)
    @Column(length = 50, nullable = false)
    String apellidos;

    @ManyToOne
    CentroSalud centroSalud;

    @Size(min = 9, max = 9)
    @Column(length = 9)
    String telefono;

    @Size(min = 0, max = 25)
    @Pattern(regexp = ".+@.+")
    @Column(length = 25)
    String email;

    
    public Medico() {
        super();
        this.tipoUsuario = TipoUsuario.MEDICO;  // Es necesario hacerlo explícitamente?        
    }

    public Medico(String numeroColegiado, String dni, String nombre, String apellidos, CentroSalud centroSalud, String telefono, String email) {
        super();        
        this.tipoUsuario = TipoUsuario.MEDICO;  // Es necesario hacerlo explícitamente?        

        this.numeroColegiado = numeroColegiado;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.centroSalud = centroSalud;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNumeroColegiado() {
        return numeroColegiado;
    }

    public void setNumeroColegiado(String numeroColegiado) {
        this.numeroColegiado = numeroColegiado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public CentroSalud getCentroSalud() {
        return centroSalud;
    }

    public void setCentroSalud(CentroSalud centroSalud) {
        this.centroSalud = centroSalud;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
