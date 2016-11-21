/*
 Proyecto Java EE, DAGSS-2015
 */
package es.uvigo.esei.dagss.controladores.administrador;

import es.uvigo.esei.dagss.dominio.daos.CentroSaludDAO;
import es.uvigo.esei.dagss.dominio.entidades.CentroSalud;
import es.uvigo.esei.dagss.dominio.entidades.Direccion;
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
@Named(value = "gestionCentrosSaludControlador")
@SessionScoped
public class GestionCentrosSaludControlador implements Serializable {

    @Inject
    CentroSaludDAO centroSaludDAO;

    List<CentroSalud> centrosSalud;
    CentroSalud centroSaludActual;

    public GestionCentrosSaludControlador() {
    }

    @PostConstruct
    public void inicializar() {
        centrosSalud = centroSaludDAO.buscarTodos();
    }

    public List<CentroSalud> getCentrosSalud() {
        return centrosSalud;
    }

    public void setCentrosSalud(List<CentroSalud> centrosSalud) {
        this.centrosSalud = centrosSalud;
    }

    public CentroSalud getCentroSaludActual() {
        return centroSaludActual;
    }

    public void setCentroSaludActual(CentroSalud centroSaludActual) {
        this.centroSaludActual = centroSaludActual;
    }

    public void doEliminar() {
        centroSaludDAO.eliminar(centroSaludActual);
        centrosSalud = centroSaludDAO.buscarTodos(); // Actualizar lista de centros
    }

    public void doNuevo() {
        centroSaludActual = new CentroSalud(); // Centro de salud vacio
        centroSaludActual.setDireccion(new Direccion()); // Con dirección vacía
    }

    public void doEditar(CentroSalud centroSalud) {
        centroSaludActual = centroSalud;   // Otra alternativa: volver a refrescarlos desde el DAO
    }

    public void doGuardarNuevo() {
        // Crea un nuevo centro de salud
        centroSaludActual = centroSaludDAO.crear(centroSaludActual);
        // Actualiza lista de centros de salud a mostrar
        centrosSalud = centroSaludDAO.buscarTodos();

    }

    public void doGuardarEditado() {
        // Actualiza un centro de salud
        centroSaludActual = centroSaludDAO.actualizar(centroSaludActual);
        // Actualiza lista de centros de salud a mostrar
        centrosSalud = centroSaludDAO.buscarTodos();
    }


    public String doVolver() {
        return "../index?faces-redirect=true";
    }

}
