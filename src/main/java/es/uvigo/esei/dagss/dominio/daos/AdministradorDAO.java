/*
 Proyecto Java EE, DAGSS-2014
 */
package es.uvigo.esei.dagss.dominio.daos;

import es.uvigo.esei.dagss.dominio.entidades.Administrador;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class AdministradorDAO extends GenericoDAO<Administrador> {

    public Administrador buscarPorLogin(String login) {
        TypedQuery<Administrador> q = em.createQuery("SELECT a FROM Administrador AS a "
                                                   + "  WHERE a.login = :login", Administrador.class);
        q.setParameter("login", login);

        return filtrarResultadoUnico(q);
    }

    // Completar aqui
}
