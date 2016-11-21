/*
 Proyecto Java EE, DAGSS-2015/16
 */
package es.uvigo.esei.dagss.controladores.administrador;

import es.uvigo.esei.dagss.dominio.daos.CentroSaludDAO;
import es.uvigo.esei.dagss.dominio.daos.MedicoDAO;
import es.uvigo.esei.dagss.dominio.daos.UsuarioDAO;
import es.uvigo.esei.dagss.dominio.entidades.CentroSalud;
import es.uvigo.esei.dagss.dominio.entidades.Medico;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author dagss
 */
@Named(value = "gestionMedicosControlador")
@SessionScoped
public class GestionMedicosControlador implements Serializable {

    @Inject
    MedicoDAO medicoDAO;

    @Inject
    CentroSaludDAO centroSaludDAO;

    @Inject
    UsuarioDAO usuarioDAO;

    List<Medico> medicos;
    Medico medicoActual;
    String password1;
    String password2;


    public GestionMedicosControlador() {
    }

    @PostConstruct
    public void inicializar() {
        medicos = medicoDAO.buscarTodos();
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }

    public Medico getMedicoActual() {
        return medicoActual;
    }

    public void setMedicoActual(Medico medicoActual) {
        this.medicoActual = medicoActual;
    }

    public List<CentroSalud> getCentrosSalud() {
        return centroSaludDAO.buscarTodos();
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

    public void doEliminar() {
        medicoDAO.eliminar(medicoActual);
        medicos = medicoDAO.buscarTodos(); // Actualizar lista de medicos
    }

    public void doNuevo() {
        medicoActual = new Medico(); // Medico vacio
        medicoActual.setFechaAlta(Calendar.getInstance().getTime());
        medicoActual.setUltimoAcceso(medicoActual.getFechaAlta());
    }

    public void doEditar(Medico medico) {
        medicoActual = medico;   // Otra alternativa: volver a refrescarlos desde el DAO
    }

    private boolean passwordsVacios() {
        if ((password1 == null) && (password2 == null)) {
            return true;
        } else {
            return (password1.isEmpty() && password2.isEmpty());
        }
    }

    private boolean passwordsValidos() {
        return (!passwordsVacios() && password1.equals(password2));
    }
    

    public void doGuardarNuevo() {
        if (passwordsValidos()) {
            // Crea  nuevo
            medicoActual = medicoDAO.crear(medicoActual);

            // Ajustar password 
            usuarioDAO.actualizarPassword(medicoActual.getId(), password1);

            // Actualiza lista 
            medicos = medicoDAO.buscarTodos();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Password incorrecto (usente o no coincidencia)", ""));
        }
    }

    public void doGuardarEditado() {
        if (passwordsVacios()) { // No modifica password
            // Actualiza 
            medicoActual = medicoDAO.actualizar(medicoActual);

            // Actualiza lista 
            medicos = medicoDAO.buscarTodos();        
        } else if (password1.equals(password2)) {
            // Actualiza
            medicoActual = medicoDAO.actualizar(medicoActual);

            // Actualiza lista a mostrar
            medicos = medicoDAO.buscarTodos();        

            // Ajustar su password 
            usuarioDAO.actualizarPassword(medicoActual.getId(), password1);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Passwords incorrectos (no coincidencia)", ""));
        }
    }

    public String doVolver() {
        return "../index?faces-redirect=true";
    }

}
