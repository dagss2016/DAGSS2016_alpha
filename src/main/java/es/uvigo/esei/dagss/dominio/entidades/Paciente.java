package es.uvigo.esei.dagss.dominio.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue(value = "PACIENTE")
public class Paciente extends Usuario implements Serializable {

    @Size(min = 10, max = 10)
    @Column(length = 10, nullable = false)
    String numeroTarjetaSanitaria;

    @Size(min = 13, max = 13)
    @Column(length = 13, nullable = false)
    String numeroSeguridadSocial;

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

    @Embedded
    Direccion direccion;

    @Size(min = 9, max = 9)
    @Column(length = 9)
    String telefono;

    @Size(min = 0, max = 25)
    @Pattern(regexp = ".+@.+")
    @Column(length = 25)
    String email;

    @ManyToOne
    Medico medico;

    

    public Paciente() {
        super();
        this.tipoUsuario = TipoUsuario.PACIENTE;  // Es necesario hacerlo explícitamente?        
    }

    public Paciente(String numeroTarjetaSanitaria, String numeroSeguridadSocial, String dni, String nombre, String apellidos, Direccion direccion, String telefono, String email, Medico medico) {
        super();
        this.tipoUsuario = TipoUsuario.PACIENTE;  // Es necesario hacerlo explícitamente?        

        this.numeroTarjetaSanitaria = numeroTarjetaSanitaria;
        this.numeroSeguridadSocial = numeroSeguridadSocial;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.medico = medico;
    }

    public String getNumeroTarjetaSanitaria() {
        return numeroTarjetaSanitaria;
    }

    public void setNumeroTarjetaSanitaria(String numeroTarjetaSanitaria) {
        this.numeroTarjetaSanitaria = numeroTarjetaSanitaria;
    }

    public String getNumeroSeguridadSocial() {
        return numeroSeguridadSocial;
    }

    public void setNumeroSeguridadSocial(String numeroSeguridadSocial) {
        this.numeroSeguridadSocial = numeroSeguridadSocial;
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

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
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

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

}
