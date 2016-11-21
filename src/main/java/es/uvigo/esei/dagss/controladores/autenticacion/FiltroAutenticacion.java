package es.uvigo.esei.dagss.controladores.autenticacion;

import es.uvigo.esei.dagss.dominio.entidades.TipoUsuario;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filtro de URL para evitar el acceso a las "zonas privadas" de otros tipos de
 * usuarios por parte de usuarios no autenticados o usuarios autenticado de otro
 * tipo
 *
 * @author ribadas
 */
@WebFilter(filterName = "FiltroAutenticacion",
        urlPatterns = {"/faces/administrador/privado/*",
            "/faces/farmacia/privado/*",
            "/faces/medico/privado/*",
            "/faces/paciente/privado/*",})
public class FiltroAutenticacion implements Filter {

    @Inject
    AutenticacionControlador autenticacionControlador;

    public FiltroAutenticacion() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean aceptarPeticion = false;

        // Recuperar el path de la URL y extraer el tipo
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        TipoUsuario tipoUsuarioPeticion = extraerTipoUsuarioPeticion(httpServletRequest.getPathInfo());

        TipoUsuario tipoUsuarioLogueado = null;  // Not logged
        HttpSession session = httpServletRequest.getSession();
        if (session != null) {
            tipoUsuarioLogueado = (TipoUsuario) session.getAttribute("tipoUsuarioLogueado");
        }

        if (tipoUsuarioPeticion == null) { //  ¿URL publica?
            // Dejar continuar la petición
            chain.doFilter(request, response);
        } else if (tipoUsuarioLogueado != null) { // Hay un usuario logueado
            if (tipoUsuarioPeticion.equals(tipoUsuarioLogueado)) {
                // Coincide con usuario de peticion -> dejar continuar la petición
                aceptarPeticion = true;
            }
        }

        if (aceptarPeticion) {
            // Dejar continuar la petición
            chain.doFilter(request, response);
        } else {
            // Finalizar la sesión del usuario (¿demasiado extrema?)
            httpServletRequest.getSession().invalidate();

            // TODO: loguear el intento de acceso
            // Redirir a la página de inicio
            String contextPath = httpServletRequest.getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/faces/index.xhtml");
        }
    }

    private TipoUsuario extraerTipoUsuarioPeticion(String path) {
        if (path.contains("/administrador/privado")) {
            return TipoUsuario.ADMINISTRADOR;
        }
        if (path.contains("/medico/privado")) {
            return TipoUsuario.MEDICO;
        }
        if (path.contains("/paciente/privado")) {
            return TipoUsuario.PACIENTE;
        }
        if (path.contains("/farmacia/privado")) {
            return TipoUsuario.FARMACIA;
        }
        return null; // Path no protegido
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
