/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.controladores.medico;

import es.uvigo.esei.dagss.dominio.daos.CitaDAO;
import es.uvigo.esei.dagss.dominio.daos.MedicamentoDAO;
import es.uvigo.esei.dagss.dominio.entidades.Cita;
import es.uvigo.esei.dagss.dominio.entidades.EstadoCita;
import es.uvigo.esei.dagss.dominio.entidades.Medicamento;
import es.uvigo.esei.dagss.dominio.entidades.Prescripcion;
import es.uvigo.esei.dagss.servicios.ServicioPrescripcion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author root
 */

@Named(value = "prescripcionControlador")
@SessionScoped
public class PrescripcionControlador implements Serializable {
    private String nombreMedicamentoActual;
    private List<String> lNombreMedicamentos;
    private List<Medicamento> lMedicamentos;
    private Medicamento medicamento;
    private String indicaciones;
    private int dosisDiaria;
    private Date fechaInicio;
    private Date fechaFin;
    
    @EJB
    private CitaDAO citaDAO;
    
    @EJB
    private ServicioPrescripcion servicioPrescripcion;
    
    @EJB
    private MedicamentoDAO medicamentoDAO;

    @Inject
    private MedicoControlador medicoControlador;
    
    public Date getFechaFin() {
        return fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    
    public void setNombreMedicamentoActual(String nombreMedicamentoActual) {
        this.nombreMedicamentoActual = nombreMedicamentoActual;
        int index = lNombreMedicamentos.lastIndexOf(this.nombreMedicamentoActual);
        this.medicamento = lMedicamentos.get(index);
    }

    public String getNombreMedicamentoActual() {
        return nombreMedicamentoActual;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public int getDosisDiaria() {
        return dosisDiaria;
    }

    public void setDosisDiaria(int dosisDiaria) {
        this.dosisDiaria = dosisDiaria;
    }
    
    //Selecciona los nombres de los medicamentos coincidentes con Query
    public List<String> completarMedicamentos(String query){
        lMedicamentos = medicamentoDAO.buscarMedicamento(query);
        lNombreMedicamentos = new ArrayList<>();
        for(Medicamento it : lMedicamentos ){
            lNombreMedicamentos.add(it.getNombre());
        }
        return lNombreMedicamentos;
    }
    
    public String crear(){
        Cita citaActual = medicoControlador.getCitaActual();
        Prescripcion p = new Prescripcion(citaActual.getPaciente(), medicamento,medicoControlador.getMedicoActual(),dosisDiaria,indicaciones,fechaInicio,fechaFin);
        servicioPrescripcion.crearPrescripción(p);
        //Me cuestiono el orden en el que esto debería escribirse
        citaActual.setEstado(EstadoCita.COMPLETADA);
        citaDAO.actualizar(citaActual);
        return "citasDeHoy";
    }



}
