/*
 Proyecto Java EE, DAGSS-2015/16
 */
package es.uvigo.esei.dagss.controladores.administrador;

import es.uvigo.esei.dagss.dominio.daos.MedicoDAO;
import es.uvigo.esei.dagss.dominio.daos.PacienteDAO;
import es.uvigo.esei.dagss.dominio.daos.UsuarioDAO;
import es.uvigo.esei.dagss.dominio.entidades.Direccion;
import es.uvigo.esei.dagss.dominio.entidades.Medico;
import es.uvigo.esei.dagss.dominio.entidades.Paciente;
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
@Named(value = "gestionPacientesControlador")
@SessionScoped
public class GestionPacientesControlador implements Serializable {

    @Inject
    PacienteDAO pacienteDAO;

    @Inject
    MedicoDAO medicoDAO;

    @Inject
    UsuarioDAO usuarioDAO;

    List<Paciente> pacientes;
    Paciente pacienteActual;
    String password1;
    String password2;

    public GestionPacientesControlador() {
    }

    @PostConstruct
    public void inicializar() {
        pacientes = pacienteDAO.buscarTodos();
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public Paciente getPacienteActual() {
        return pacienteActual;
    }

    public void setPacienteActual(Paciente pacienteActual) {
        this.pacienteActual = pacienteActual;
    }

    public List<Medico> getMedicos() {
        return medicoDAO.buscarTodos();
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
        pacienteDAO.eliminar(pacienteActual);
        pacientes = pacienteDAO.buscarTodos(); // Actualizar lista 
    }

    public void doNuevo() {
        pacienteActual = new Paciente(); // Paciente vacio
        pacienteActual.setDireccion(new Direccion());
        pacienteActual.setFechaAlta(Calendar.getInstance().getTime());
        pacienteActual.setUltimoAcceso(pacienteActual.getFechaAlta());
    }

    public void doEditar(Paciente paciente) {
        pacienteActual = paciente;   // Otra alternativa: volver a refrescarlos desde el DAO
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
            pacienteActual = pacienteDAO.crear(pacienteActual);
            // Ajustar su password 
            usuarioDAO.actualizarPassword(pacienteActual.getId(), password1);

            // Actualiza lista  a mostrar
            pacientes = pacienteDAO.buscarTodos();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Password incorrecto (usente o no coincidencia)", ""));
        }
    }

    public void doGuardarEditado() {
        if (passwordsVacios()) { // No modifica password
            // Actualiza 
            pacienteActual = pacienteDAO.actualizar(pacienteActual);

            // Actualiza lista  a mostrar
            pacientes = pacienteDAO.buscarTodos();        
        } else if (password1.equals(password2)) {
            // Actualiza 
            pacienteActual = pacienteDAO.actualizar(pacienteActual);

            // Actualiza lista a mostrar
            pacientes = pacienteDAO.buscarTodos();        

            // Ajustar su password 
            usuarioDAO.actualizarPassword(pacienteActual.getId(), password1);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Passwords incorrectos (no coincidencia)", ""));
        }
    }

    public String doVolver() {
        return "../index?faces-redirect=true";
    }

}
