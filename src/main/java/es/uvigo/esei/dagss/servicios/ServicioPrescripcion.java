/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.servicios;

import es.uvigo.esei.dagss.dominio.daos.PrescripcionDAO;
import es.uvigo.esei.dagss.dominio.daos.RecetaDAO;
import es.uvigo.esei.dagss.dominio.entidades.EstadoReceta;
import es.uvigo.esei.dagss.dominio.entidades.Prescripcion;
import es.uvigo.esei.dagss.dominio.entidades.Receta;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author root
 */
@Stateless
public class ServicioPrescripcion {
    
    @EJB
    private PrescripcionDAO prescripcionDAO;
    
    @EJB
    private RecetaDAO recetaDAO;
    
    
    public void crearPrescripción(Prescripcion p){
        prescripcionDAO.crear(p);
        crearPlanDeRecetas(p);
    }

    public void crearPlanDeRecetas(Prescripcion p){
        int nDias = getDiferenciaDeDias(p.getFechaInicio(), p.getFechaFin());
        int dosisDiaria = p.getDosis();
        int dosisCaja = p.getMedicamento().getNumeroDosis();
        
        int dosisTotal = nDias * dosisDiaria;
        int nRecetas = (int) Math.ceil(((double) dosisTotal)/dosisCaja);
        int duracionCaja = dosisCaja / dosisDiaria;
        Date start = p.getFechaInicio();
        for(int i = 0; i< nRecetas; i++){
            Date inicio = operarDiasFecha(start, -7);
            Date fin = operarDiasFecha(start, 14);
          
            Receta r = new Receta(p,1,inicio,fin,EstadoReceta.GENERADA);
            recetaDAO.crear(r);
            start = operarDiasFecha(start, duracionCaja);
        }
    }
    
    private int getDiferenciaDeDias(Date inicio, Date fin){
        long dif = fin.getTime() - inicio.getTime();
        return (int) TimeUnit.DAYS.convert(dif, 
                TimeUnit.MILLISECONDS);
        
    }
    
    //Se resta con dias<0
    private Date operarDiasFecha(Date fecha, int dias){
 
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(fecha); // Configuramos la fecha que se recibe
      calendar.add(Calendar.DAY_OF_YEAR, dias);  
      return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
 
 }

    
}
