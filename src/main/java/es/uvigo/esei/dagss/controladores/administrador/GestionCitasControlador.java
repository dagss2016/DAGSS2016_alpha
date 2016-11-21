/*
 Proyecto Java EE, DAGSS-2015
 */
package es.uvigo.esei.dagss.controladores.administrador;

import es.uvigo.esei.dagss.dominio.daos.CitaDAO;
import es.uvigo.esei.dagss.dominio.daos.MedicoDAO;
import es.uvigo.esei.dagss.dominio.daos.PacienteDAO;
import es.uvigo.esei.dagss.dominio.entidades.Cita;
import es.uvigo.esei.dagss.dominio.entidades.EstadoCita;
import es.uvigo.esei.dagss.dominio.entidades.Medico;
import es.uvigo.esei.dagss.dominio.entidades.Paciente;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author dagss
 */
@Named(value = "gestionCitasControlador")
@SessionScoped
public class GestionCitasControlador implements Serializable {

    static final public Integer DURACION_CITA_POR_DEFECTO = 15; // Citas de 15 minutos

    @Inject
    CitaDAO citaDAO;

    @Inject
    MedicoDAO medicoDAO;
    
    @Inject
    PacienteDAO pacienteDAO;

    List<Cita> citas;
    Cita citaActual;

    public GestionCitasControlador() {
    }

    @PostConstruct
    public void inicializar() {
        citas = citaDAO.buscarTodos();
    }

    /*
       Usado en la lista desplegable con los estados de una cita
    */
    public EstadoCita[]  getEstadosCitas() {
        return EstadoCita.values();
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public Cita getCitaActual() {
        return citaActual;
    }

    public void setCitaActual(Cita citaActual) {
        this.citaActual = citaActual;
    }

    public List<Paciente> getListadoPacientes() {
        return pacienteDAO.buscarTodos();
    }

    public List<Medico> getListadoMedicos() {
        return medicoDAO.buscarTodos();
    }

    public void doEliminar() {
        citaDAO.eliminar(citaActual);
        citas = citaDAO.buscarTodos(); // Actualizar lista de centros
    }

    public void doNuevo() {
        citaActual = new Cita(); // Cita valia   
        citaActual.setDuracion(DURACION_CITA_POR_DEFECTO);
        citaActual.setEstado(EstadoCita.PLANIFICADA);
    }

    public void doEditar(Cita cita) {
        citaActual = cita;   // Otra alternativa: volver a refrescarlos desde el DAO
    }

    public void doGuardarNuevo() {
        // Crea un nuevo centro de salud
        citaActual = citaDAO.crear(citaActual);
        // Actualiza lista de centros de salud a mostrar
        citas = citaDAO.buscarTodos();

    }

    public void doGuardarEditado() {
        // Actualiza un centro de salud
        citaActual = citaDAO.actualizar(citaActual);
        // Actualiza lista de centros de salud a mostrar
        citas = citaDAO.buscarTodos();
    }

    public String doVolver() {
        return "../index?faces-redirect=true";
    }

}
