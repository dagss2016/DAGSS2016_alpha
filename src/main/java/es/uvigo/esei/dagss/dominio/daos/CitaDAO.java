/*
 Proyecto Java EE, DAGSS-2014
 */

package es.uvigo.esei.dagss.dominio.daos;

import es.uvigo.esei.dagss.dominio.entidades.Cita;
import es.uvigo.esei.dagss.dominio.entidades.Medico;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;


@Stateless
@LocalBean
public class CitaDAO  extends GenericoDAO<Cita>{    
    
    public List<Cita> getCitasDeHoy(Medico m){
        Date fecha = Calendar.getInstance().getTime();
        TypedQuery<Cita> tq = em.createQuery("SELECT c FROM Cita AS c "
                + "WHERE c.medico = :medico AND c.fecha = :f", Cita.class); //OJO
        tq.setParameter("medico", m);
        tq.setParameter("f", fecha);
        return tq.getResultList();
    }
     
}
