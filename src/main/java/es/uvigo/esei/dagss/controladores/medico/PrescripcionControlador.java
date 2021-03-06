/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.controladores.medico;

import es.uvigo.esei.dagss.dominio.daos.CitaDAO;
import es.uvigo.esei.dagss.dominio.daos.MedicamentoDAO;
import es.uvigo.esei.dagss.dominio.daos.PrescripcionDAO;
import es.uvigo.esei.dagss.dominio.daos.RecetaDAO;
import es.uvigo.esei.dagss.dominio.entidades.Cita;
import es.uvigo.esei.dagss.dominio.entidades.EstadoCita;
import es.uvigo.esei.dagss.dominio.entidades.Medicamento;
import es.uvigo.esei.dagss.dominio.entidades.Prescripcion;
import es.uvigo.esei.dagss.dominio.entidades.Receta;
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
    private Prescripcion prescripcionAModificar;
    
    @EJB
    private CitaDAO citaDAO;
    
    @EJB
    private ServicioPrescripcion servicioPrescripcion;
    
    @EJB
    private MedicamentoDAO medicamentoDAO;

    @Inject
    private MedicoControlador medicoControlador;

    @Inject
    private PrescripcionDAO prescripcionDAO;
    
    @Inject RecetaDAO recetaDAO;

    public void borrar(Prescripcion p){
        //Borrar recetas primero
        borrarRecetasDePrescripcion(p);
        //Luego borrar la prescripcion
        prescripcionDAO.eliminar(p);
    }
    
    public void borrarRecetasDePrescripcion(Prescripcion p){
        Long id = p.getId();
        List<Receta> recetas = recetaDAO.getRecetasPrescripcion(id);
        for(Receta r: recetas){
            recetaDAO.eliminar(r);
        }
    }

    public Prescripcion getPrescripcionAModificar() {
        return prescripcionAModificar;
    }
    
    public String modificarPrescripcion(Prescripcion p){
        medicamento = p.getMedicamento();
        indicaciones = p.getIndicaciones();
        dosisDiaria = p.getDosis();
        fechaInicio = p.getFechaInicio();
        fechaFin = p.getFechaFin();
        prescripcionAModificar = p;
        return "modificarPrescripcion";
    }
    
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
        if(indicaciones.equals("")){
            this.indicaciones = "N/A";
        }else{
            this.indicaciones = indicaciones;
        }
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
    
    public String modificar(){
        //Long id = prescripcionAModificar.getId();
        //prescripcionDAO.actualizar(id, dosisDiaria, fechaFin, fechaInicio, indicaciones, medicamento);
        Prescripcion toUpdate = prescripcionDAO.buscarPorId(prescripcionAModificar.getId());
        toUpdate.setDosis(dosisDiaria);
        toUpdate.setFechaFin(fechaFin);
        toUpdate.setFechaInicio(fechaInicio);
        toUpdate.setIndicaciones(indicaciones);
        toUpdate.setMedicamento(medicamento);
        prescripcionDAO.actualizar(toUpdate);
        //BORRAR RECETAS Y HACERLAS NUEVAS 
        borrarRecetasDePrescripcion(toUpdate);
        servicioPrescripcion.crearPlanDeRecetas(toUpdate);
        return "index"; 
    }
    

    public String finalizar(){
        Cita citaActual = medicoControlador.getCitaActual();
        citaActual.setEstado(EstadoCita.COMPLETADA);
        citaDAO.actualizar(citaActual);
        return "citasDeHoy";
    }

    public List<Prescripcion> getPrescripcionesPaciente(){
        return this.prescripcionDAO.buscarPorPaciente(
                this.medicoControlador.getPacienteActual()
        );
    }



}
