/*
 Proyecto Java EE, DAGSS-2013
 */
package es.uvigo.esei.dagss.controladores.medico;

import es.uvigo.esei.dagss.controladores.autenticacion.AutenticacionControlador;
import es.uvigo.esei.dagss.dominio.daos.CitaDAO;
import es.uvigo.esei.dagss.dominio.daos.MedicamentoDAO;
import es.uvigo.esei.dagss.dominio.daos.MedicoDAO;
import es.uvigo.esei.dagss.dominio.daos.PacienteDAO;
import es.uvigo.esei.dagss.dominio.entidades.*;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.POST;

/**
 *
 * @author ribadas
 */

@Named(value = "medicoControlador")
@SessionScoped
public class MedicoControlador implements Serializable {
    
    
    private Medico medicoActual;
    private Cita citaActual;
    private String dni;
    private String numeroColegiado;
    private String password;
    private List<Cita> listaDeCitas;

    @Inject
    private AutenticacionControlador autenticacionControlador;

    @EJB
    private MedicoDAO medicoDAO;
    

    @EJB
    private CitaDAO citaDAO;

    @Inject
    private PacienteDAO pacienteDAO;

    private List<Paciente> pacientes;
    private Paciente pacienteActual;


    public List<Cita> cargarCitasHoy(){
        return citaDAO.getCitasDeHoy(medicoActual);
    }
    
    /**
     * Creates a new instance of AdministradorControlador
     */
    public MedicoControlador() {
    }

    public List<Cita> getListaDeCitas() {
        return listaDeCitas;
    }

    public Cita getCitaActual() {
        return citaActual;
    }


 

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public String doAtenderCita(Cita c){
        citaActual = c;
        return "atenderCita";
    }

    public String doVerPreinscripcionesPaciente(Paciente p){
        this.pacienteActual = p;
        return "preinscripcionesPaciente";
    }
    
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNumeroColegiado() {
        return numeroColegiado;
    }

    public void setNumeroColegiado(String numeroColegiado) {
        this.numeroColegiado = numeroColegiado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Medico getMedicoActual() {
        return medicoActual;
    }

    public Paciente getPacienteActual() {
        return pacienteActual;
    }

    private boolean parametrosAccesoInvalidos() {
        return (((dni == null) && (numeroColegiado == null)) || (password == null));
    }
    
    public void deleteCita(Cita c){
        c.setEstado(EstadoCita.ANULADA);
        citaDAO.actualizar(c);
    }

    private Medico recuperarDatosMedico() {
        Medico medico = null;
        if (dni != null) {
            medico = medicoDAO.buscarPorDNI(dni);
        }
        if ((medico == null) && (numeroColegiado != null)) {
            medico = medicoDAO.buscarPorNumeroColegiado(numeroColegiado);
        }
        return medico;
    }
    

    public String doLogin() {
        String destino = null;
        if (parametrosAccesoInvalidos()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha indicado un DNI ó un número de colegiado o una contraseña", ""));
        } else {
            Medico medico = recuperarDatosMedico();
            if (medico == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No existe ningún médico con los datos indicados", ""));
            } else {
                if (autenticacionControlador.autenticarUsuario(medico.getId(), password,
                        TipoUsuario.MEDICO.getEtiqueta().toLowerCase())) {
                    medicoActual = medico;
                    this.pacientes = pacienteDAO.buscarPorMedico(this.medicoActual);
                    destino = "privado/index";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Credenciales de acceso incorrectas", ""));
                }
            }
        }
        return destino;
    }
    
    
    //Acciones
    public String doShowCita() {
        return "detallesCita";
    }
}
