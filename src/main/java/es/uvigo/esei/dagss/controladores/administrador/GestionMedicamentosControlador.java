/*
 Proyecto Java EE, DAGSS-2015
 */
package es.uvigo.esei.dagss.controladores.administrador;

import es.uvigo.esei.dagss.dominio.daos.MedicamentoDAO;
import es.uvigo.esei.dagss.dominio.entidades.Medicamento;
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
@Named(value = "gestionMedicamentosControlador")
@SessionScoped
public class GestionMedicamentosControlador implements Serializable {

    @Inject
    MedicamentoDAO medicamentoDAO;

    List<Medicamento> medicamentos;
    Medicamento medicamentoActual;

    public GestionMedicamentosControlador() {
    }

    @PostConstruct
    public void inicializar() {
        medicamentos = medicamentoDAO.buscarTodos();
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setCentrosSalud(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public Medicamento getMedicamentoActual() {
        return medicamentoActual;
    }

    public void setMedicamentoActual(Medicamento medicamentoActual) {
        this.medicamentoActual = medicamentoActual;
    }

    public void doEliminar() {
        medicamentoDAO.eliminar(medicamentoActual);
        medicamentos = medicamentoDAO.buscarTodos(); // Actualizar lista 
    }

    public void doNuevo() {
        medicamentoActual = new Medicamento(); // Medicamento
    }

    public void doEditar(Medicamento medicamento) {
        medicamentoActual = medicamento;   // Otra alternativa: volver a refrescarlos desde el DAO
    }

    public void doGuardarNuevo() {
        // Crea  nuevo
        medicamentoActual = medicamentoDAO.crear(medicamentoActual);
        // Actualiza lista a mostrar
        medicamentos = medicamentoDAO.buscarTodos();

    }

    public void doGuardarEditado() {
        // Actualiza 
        medicamentoActual = medicamentoDAO.actualizar(medicamentoActual);
        // Actualiza lista  a mostrar
        medicamentos = medicamentoDAO.buscarTodos();
    }


    public String doVolver() {
        return "../index?faces-redirect=true";
    }

}
