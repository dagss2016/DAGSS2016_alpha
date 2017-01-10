/*
 Proyecto Java EE, DAGSS-2014
 */

package es.uvigo.esei.dagss.dominio.daos;

import es.uvigo.esei.dagss.dominio.entidades.Cita;
import es.uvigo.esei.dagss.dominio.entidades.Medico;
import es.uvigo.esei.dagss.dominio.entidades.Receta;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class RecetaDAO extends GenericoDAO<Receta>{
    
    public List<Receta> getRecetasActuales(String nTarjeta){
        Date fecha = Calendar.getInstance().getTime(); //TODO usar a partir de una fecha a mayores
        TypedQuery<Receta> tq = em.createQuery("SELECT r FROM Receta AS r"
                + " WHERE r.prescripcion.paciente.numeroTarjetaSanitaria = :ab AND :fecha BETWEEN r.inicioValidez AND r.finValidez", Receta.class); 
        tq.setParameter("ab", nTarjeta);
        tq.setParameter("fecha", fecha);
        List<Receta> toret = tq.getResultList();
        return toret;
    }

    public List<Receta> getRecetasPrescripcion(Long id) {
        TypedQuery<Receta> tq = em.createQuery("SELECT r FROM Receta AS r"
                + " WHERE r.prescripcion.id = :id", Receta.class); 
        tq.setParameter("id", id);
        List<Receta> toret = tq.getResultList();
        return toret;
    }

   
}
