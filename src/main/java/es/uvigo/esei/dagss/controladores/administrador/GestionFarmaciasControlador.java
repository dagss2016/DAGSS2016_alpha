/*
 Proyecto Java EE, DAGSS-2015
 */
package es.uvigo.esei.dagss.controladores.administrador;

import es.uvigo.esei.dagss.dominio.daos.FarmaciaDAO;
import es.uvigo.esei.dagss.dominio.daos.UsuarioDAO;
import es.uvigo.esei.dagss.dominio.entidades.Direccion;
import es.uvigo.esei.dagss.dominio.entidades.Farmacia;
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
@Named(value = "gestionFarmaciasControlador")
@SessionScoped
public class GestionFarmaciasControlador implements Serializable {

    @Inject
    FarmaciaDAO farmaciaDAO;

    @Inject
    UsuarioDAO usuarioDAO;

    List<Farmacia> farmacias;
    Farmacia farmaciaActual;

    String password1;
    String password2;

    public GestionFarmaciasControlador() {
    }

    @PostConstruct
    public void inicializar() {
        farmacias = farmaciaDAO.buscarTodos();
    }

    public List<Farmacia> getFarmacias() {
        return farmacias;
    }

    public void setFarmacias(List<Farmacia> farmacias) {
        this.farmacias = farmacias;
    }

    public Farmacia getFarmaciaActual() {
        return farmaciaActual;
    }

    public void setFarmaciaActual(Farmacia farmaciaActual) {
        this.farmaciaActual = farmaciaActual;
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
        farmaciaDAO.eliminar(farmaciaActual);
        farmacias = farmaciaDAO.buscarTodos(); // Actualizar lista 
    }

    public void doNuevo() {
        farmaciaActual = new Farmacia(); // Farmacia vacia
        farmaciaActual.setDireccion(new Direccion()); // Con dirección vacía
        farmaciaActual.setFechaAlta(Calendar.getInstance().getTime());
        farmaciaActual.setUltimoAcceso(farmaciaActual.getFechaAlta());
    }

    public void doEditar(Farmacia farmacia) {
        farmaciaActual = farmacia;   // Otra alternativa: volver a refrescarlos desde el DAO
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
            farmaciaActual = farmaciaDAO.crear(farmaciaActual);

            // Ajustar password 
            usuarioDAO.actualizarPassword(farmaciaActual.getId(), password1);

            // Actualiza lista 
            farmacias = farmaciaDAO.buscarTodos();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Password incorrecto (usente o no coincidencia)", ""));
        }
    }

    public void doGuardarEditado() {
        if (passwordsVacios()) { // No modifica password
            // Actualiza 
            farmaciaActual = farmaciaDAO.actualizar(farmaciaActual);

            // Actualiza lista 
            farmacias = farmaciaDAO.buscarTodos();        
        } else if (password1.equals(password2)) {
            // Actualiza
            farmaciaActual = farmaciaDAO.actualizar(farmaciaActual);

            // Actualiza lista a mostrar
            farmacias = farmaciaDAO.buscarTodos();        

            // Ajustar su password 
            usuarioDAO.actualizarPassword(farmaciaActual.getId(), password1);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Passwords incorrectos (no coincidencia)", ""));
        }
    }

    public String doVolver() {
        return "../index?faces-redirect=true";
    }

}
