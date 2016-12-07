/*
 Proyecto Java EE, DAGSS-2014
 */
package es.uvigo.esei.dagss.dominio.daos;

import es.uvigo.esei.dagss.dominio.entidades.Medicamento;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import es.uvigo.esei.dagss.dominio.entidades.Medicamento;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class MedicamentoDAO extends GenericoDAO<Medicamento> {

    public List<Medicamento> buscarMedicamento(String busqueda) {
       TypedQuery tq = (TypedQuery) em.createQuery("SELECT m FROM Medicamento AS m WHERE m.nombre LIKE :busqueda" );
       tq.setParameter("busqueda", "%"+busqueda+"%");
       return tq.getResultList();
    }

    // Completar aqui
}
