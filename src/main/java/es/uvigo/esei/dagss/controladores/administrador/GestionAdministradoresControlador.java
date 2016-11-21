/*
 Proyecto Java EE, DAGSS-2015
 */
package es.uvigo.esei.dagss.controladores.administrador;

import es.uvigo.esei.dagss.dominio.daos.AdministradorDAO;
import es.uvigo.esei.dagss.dominio.entidades.Administrador;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author dagss
 */
@Named(value = "gestionAdministradoresControlador")
@ConversationScoped
public class GestionAdministradoresControlador implements Serializable {

    private List<Administrador> administradores;
    private Administrador administradorEnEdicion;
    private String password1;
    private String password2;
    private boolean esNuevo;

    @Inject
    Conversation conversation;

    @Inject
    private AdministradorDAO administradorDAO;

    /**
     * Creates a new instance of GestionAdministradoresControlador
     */
    public GestionAdministradoresControlador() {
    }

    @PostConstruct
    private void inicializarGestionAdministradores() {
        if (conversation.isTransient()) {
            conversation.begin();
        }

        administradores = administradorDAO.buscarTodos();
        esNuevo = false;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<Administrador> administradores) {
        this.administradores = administradores;
    }

    public Administrador getAdministradorEnEdicion() {
        return administradorEnEdicion;
    }

    public void setAdministradorEnEdicion(Administrador administradorEnEdicion) {
        this.administradorEnEdicion = administradorEnEdicion;
        this.esNuevo = false;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String doEditar(Administrador administradorSeleccionado) {
        if (administradorSeleccionado != null) {
            administradorEnEdicion = administradorSeleccionado;
            return "editarAdministrador";
        } else {
            return null;
        }
    }

    public String doEliminar(Administrador administradorSeleccionado) {
        // llamar a DAO
        administradorDAO.eliminar(administradorSeleccionado);
        administradorEnEdicion = null;
        return "listaAdministradores";
    }

    public String doNuevo() {
        administradorEnEdicion = new Administrador();
        administradorEnEdicion.setFechaAlta(Calendar.getInstance().getTime());
        administradorEnEdicion.setUltimoAcceso(administradorEnEdicion.getFechaAlta());
        this.esNuevo = false;
        return "editarAdministrador";
    }

    public String doGuardar() {
        // llamar a DAO
        if ((password1 != null) && (password2 != null) && (password1.equals(password2))) {
            if (this.esNuevo) {
                administradorEnEdicion = administradorDAO.crear(administradorEnEdicion);
            } else {
                administradorEnEdicion = administradorDAO.actualizar(administradorEnEdicion);
            }
        }
        return "listaAdministradores";
    }

    public String doCancelar() {
        if ((administradores != null) && (!administradores.isEmpty())) {
            administradorEnEdicion = administradores.get(0);
        }
        return "listaAdministradores";
    }

    public String doVolver() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        return "../index";
    }
}
