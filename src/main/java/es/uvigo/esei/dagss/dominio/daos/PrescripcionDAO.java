/*
 Proyecto Java EE, DAGSS-2016
 */
package es.uvigo.esei.dagss.dominio.daos;

import es.uvigo.esei.dagss.dominio.entidades.Medicamento;
import es.uvigo.esei.dagss.dominio.entidades.Paciente;
import es.uvigo.esei.dagss.dominio.entidades.Prescripcion;
import java.util.Date;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.persistence.Query;

@Stateless
@LocalBean
public class PrescripcionDAO extends GenericoDAO<Prescripcion> {

    public Prescripcion buscarPorIdConRecetas(Long id) {
        TypedQuery<Prescripcion> q = em.createQuery("SELECT p FROM Prescripcion AS p JOIN FETCH p.recetas"
                                                  + "  WHERE p.id = :id", Prescripcion.class);
        q.setParameter("id", id);
        
        return q.getSingleResult();
    }
    
    public List<Prescripcion> buscarPorPaciente(Paciente p){
        TypedQuery<Prescripcion> q = em.createQuery("SELECT p FROM Prescripcion AS p WHERE p.paciente = :paciente",
                Prescripcion.class);
        q.setParameter("paciente", p);
        return q.getResultList();
    }
    /* //Esto está dando demasiado trabajo
    public void actualizar(Long id, int dosisDiaria, Date fechaFin, Date fechaInicio, String indicaciones, Medicamento medicamento) {
        Query q = em.createQuery("UPDATE Prescripcion SET dosis = :dosisD, "
                                + "fechafin = :fFin, fechainicio = :fInicio, "
                                + "indicaciones = :ind ,medicamento_id = :med " 
                                +" WHERE id = :id");
        q.setParameter("id", id);
        q.setParameter("dosisD", dosisDiaria);
        q.setParameter("fFin", fechaFin);
        q.setParameter("fInicio", fechaInicio);
        q.setParameter("ind", indicaciones);
        q.setParameter("med", medicamento.getId());
        q.executeUpdate();
    }
    */
}
