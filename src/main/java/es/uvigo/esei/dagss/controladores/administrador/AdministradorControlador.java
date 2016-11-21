/*
 Proyecto Java EE, DAGSS-2015
 */
package es.uvigo.esei.dagss.controladores.administrador;

import es.uvigo.esei.dagss.controladores.autenticacion.AutenticacionControlador;
import es.uvigo.esei.dagss.dominio.daos.AdministradorDAO;
import es.uvigo.esei.dagss.dominio.entidades.Administrador;
import es.uvigo.esei.dagss.dominio.entidades.TipoUsuario;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ribadas
 */

@Named(value = "administradorControlador")
@SessionScoped
public class AdministradorControlador implements Serializable {

    private List<Administrador> administradores;
    private Administrador administradorActual;
    private String login;
    private String password;

    @Inject
    private AutenticacionControlador autenticacionControlador;

    @Inject
    private AdministradorDAO administradorDAO;

    /**
     * Creates a new instance of AdministradorControlador
     */
    public AdministradorControlador() {
    }

    @PostConstruct
    public void inicializar() {
        administradores = administradorDAO.buscarTodos();
        administradorActual = null;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<Administrador> administradores) {
        this.administradores = administradores;
    }

    public Administrador getAdminsitradorActual() {
        return administradorActual;
    }

    public void setAdminsitradorActual(Administrador adminsitradorActual) {
        this.administradorActual = adminsitradorActual;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Administrador getAdministradorActual() {
        return administradorActual;
    }

    public void setAdministradorActual(Administrador administradorActual) {
        this.administradorActual = administradorActual;
    }


    private boolean parametrosAccesoInvalidos() {
        return ((login == null) || (password == null));
    }
        
    public String doLogin() {
        String destino = null;
        
        if (parametrosAccesoInvalidos()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha indicado un nombre de usuario o una contraseña", ""));
        }
        else {
            Administrador administrador = administradorDAO.buscarPorLogin(login);
            if (administrador == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No existe el usuario " + login, ""));
            } else {
                if (autenticacionControlador.autenticarUsuario(administrador.getId(), password,
                                                               TipoUsuario.ADMINISTRADOR.getEtiqueta().toLowerCase())) {
                    administradorActual = administrador;
                    destino = "privado/index";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Credenciales de acceso incorrectas", ""));
                }

            }
        }
        return destino;
    }
    
    public String doGestionAdministradores() {
    
        return "administradores/listaAdministradores";
    }
    
    public String doGestionCentrosSalud() {
    
        return "centrossalud/listaCentrosSalud";
    }
    
    public String doGestionTiposAgenda() {
    
        return "centrossalud/listaTiposAgenda";
    }
    
    public String doGestionMedicos() {
    
        return "medicos/listaMedicos";
    }

    public String doGestionPacientes() {
    
        return "pacientes/listaPacientes";
    }    

    public String doGestionFarmacias() {
    
        return "farmacias/listaFarmacias";
    }    
    
    public String doGestionMedicamentos() {
    
        return "medicamentos/listaMedicamentos";
    }    
    
    
}
