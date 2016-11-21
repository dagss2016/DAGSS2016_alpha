/*
 Práctica Java EE 7, DAGSS 2015/16 (ESEI, U. de Vigo)
 */
package es.uvigo.esei.dagss.dominio.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ADMINISTRADOR")
public class Administrador extends Usuario implements Serializable {

    @Column(length = 25)
    String login;

    @Column(length = 75)
    String nombre;


    public Administrador() {
        super();
        this.tipoUsuario = TipoUsuario.ADMINISTRADOR;  // Es necesario hacerlo explícitamente?
    }

    public Administrador(String login, String nombre) {
        super();
        this.tipoUsuario = TipoUsuario.ADMINISTRADOR;  // Es necesario hacerlo explícitamente?

        this.login = login;
        this.nombre = nombre;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.login);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Administrador other = (Administrador) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        return true;
    }


}
