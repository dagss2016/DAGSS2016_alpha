/*
 Práctica Java EE 7, DAGSS 2015/16 (ESEI, U. de Vigo)
 */
package es.uvigo.esei.dagss.dominio.entidades;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  // Una única tabla compartida por todas las  subclases
@DiscriminatorColumn(name = "TIPO_USUARIO",
        discriminatorType = DiscriminatorType.STRING,
        length = 20)
public abstract class Usuario implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableGenerator(name = "USUARIO_GEN", table = "USUARIO_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", allocationSize = 1)           
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "USUARIO_GEN")
    Long id;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date fechaAlta;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date ultimoAcceso;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_USUARIO", length = 20)
    TipoUsuario tipoUsuario;

    @Column(length = 64)
    String password;


    public Usuario() {
        this.fechaAlta = Calendar.getInstance().getTime();
        this.ultimoAcceso = Calendar.getInstance().getTime();
    }

    public Usuario(Date fechaAlta, Date ultimoAcceso, TipoUsuario tipoUsuario) {
        this(fechaAlta, ultimoAcceso, tipoUsuario, null);
    }

    public Usuario(Date fechaAlta, Date ultimoAcceso, TipoUsuario tipoUsuario, String password) {
        this.fechaAlta = fechaAlta;
        this.ultimoAcceso = ultimoAcceso;
        this.tipoUsuario = tipoUsuario;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Date ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", tipoUsuario=" + tipoUsuario + '}';
    }

    
 
}
