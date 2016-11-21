/*
 Proyecto Java EE, DAGSS-2013
 */
package es.uvigo.esei.dagss.dominio.daos;

import es.uvigo.esei.dagss.dominio.entidades.TipoUsuario;
import es.uvigo.esei.dagss.dominio.entidades.Usuario;
import java.util.Calendar;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.jasypt.util.password.BasicPasswordEncryptor;

@Stateless
@LocalBean
public class UsuarioDAO extends GenericoDAO<Usuario> {


    // Métodos adicionales para control de usuarios
    public boolean autenticarUsuario(Long idUsuario, String passwordPlano, String tipo) {
        Usuario usuario;
        boolean resultado = false;
        
        usuario = buscarPorId(idUsuario);
                
        if (usuario != null) {
            if (comprobarTipo(usuario, tipo)) {
                if (usuario.getPassword().equals("")) {
                    resultado = true;
                } else if (passwordPlano.equals("") && !usuario.getPassword().equals("")) {
                    resultado = false;
                } else {
                    if (comprobarPassword(passwordPlano, usuario.getPassword())) {
                        resultado = true;
                    }
                }
            }
        }

        return resultado;
    }

    public Usuario actualizarPassword(long idUsuario, String passwordPlano) {
        Usuario usuario = buscarPorId(idUsuario);

        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        String passwordEncriptado = passwordEncryptor.encryptPassword(passwordPlano);

        usuario.setPassword(passwordEncriptado);
        return actualizar(usuario);
    }

    public Usuario actualizarUltimoAcceso(long idUsuario) {
        Usuario usuario = buscarPorId(idUsuario);
        usuario.setUltimoAcceso(Calendar.getInstance().getTime());  // Tiempo actual
        return actualizar(usuario);
    }

    public boolean existeUsuario(long idUsuario) {
        return (buscarPorId(idUsuario) != null);
    }

    private boolean comprobarTipo(Usuario usuario, String tipo) {
        return usuario.getTipoUsuario().getEtiqueta().equalsIgnoreCase(tipo);
    }

    private boolean comprobarTipo(Usuario usuario, TipoUsuario tipo) {
        return usuario.getTipoUsuario().equals(tipo);
    }    
    
    private boolean comprobarPassword(String passwordPlano, String passwordEncriptado) {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

        return (passwordEncryptor.checkPassword(passwordPlano, passwordEncriptado));
    }
}
