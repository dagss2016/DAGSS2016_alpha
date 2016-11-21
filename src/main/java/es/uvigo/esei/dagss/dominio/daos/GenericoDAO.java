/*
 Proyecto Java EE, DAGSS-2016
 */
package es.uvigo.esei.dagss.dominio.daos;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GenericoDAO<T> {

    @PersistenceContext(unitName = "dagss-PU")
    protected EntityManager em;

    protected Class<T> claseEntidad;  // TODO: inicializar aqui y omitir comprobacion+llamada a metodo

    private void establecerClaseEntidad() {
        // Identifica la clase real de las entidades gestionada por este objeto (T.class)
        this.claseEntidad = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T crear(T entidad) {
        em.persist(entidad); // Crea una nueva tupla en la BD con los datos de "entidad"
        // -> se generara su ID en el "commit" contra la BD
        return entidad;
    }

    public T actualizar(T entidad) {
        return em.merge(entidad);   // Actualiza los datos de la "entidad" en su correspondiente tupla BD
    }

    public void eliminar(T entidad) {
        em.remove(em.merge(entidad));  // Actualiza y elimina
    }

    public T buscarPorId(Object id) {
        if (this.claseEntidad == null) {
            establecerClaseEntidad();
        }
        return em.find(this.claseEntidad, id);
    }

    public List<T> buscarTodos() {
        if (this.claseEntidad == null) {
            establecerClaseEntidad();
        }
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(this.claseEntidad);
        query.select(query.from(this.claseEntidad));
        return em.createQuery(query).getResultList();
    }

    public Long contarTodos() {
        if (this.claseEntidad == null) {
            establecerClaseEntidad();
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(this.claseEntidad)));
        return em.createQuery(query).getSingleResult();
    }

    // Metodos de utilidad comunes    
    public List<T> buscarQueryParametrizada(String consulta, Map<String, Object> parametros) {
        Query query = em.createQuery(consulta);
        for (Map.Entry<String, Object> parametro : parametros.entrySet()) {
            query.setParameter(parametro.getKey(), parametro.getValue());
        }
        return query.getResultList();
    }

    protected T filtrarResultadoUnico(Query query) {
        List<T> resultados = query.getResultList();
        if (resultados == null) {
            return null;  // No encontrado
        } else if (resultados.size() != 1) {
            return null; // No encontrado (hay más con el mismo login ¿?)
        } else {
            return resultados.get(0);  // Devuelve el encontrado
        }
    }
}
