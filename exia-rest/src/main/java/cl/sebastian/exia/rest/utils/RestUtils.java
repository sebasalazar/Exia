package cl.sebastian.exia.rest.utils;

import cl.sebastian.exia.rest.vo.RespuestaVO;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Sebastián Salazar Molina
 */
public class RestUtils implements Serializable {

    private static final long serialVersionUID = 6307995951354967040L;
    private static final Logger logger = LoggerFactory.getLogger(RestUtils.class);

    private RestUtils() {
        throw new AssertionError();
    }

    public static String obtenerIp() {
        String ip = StringUtils.EMPTY;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (request != null) {
                ip = obtenerIp(request);
            }
        } catch (Exception e) {
            ip = StringUtils.EMPTY;
            logger.error("Error al consultar IP: {}", e.toString());
            logger.debug("Error al consultar IP: {}", e.toString(), e);
        }
        return ip;
    }

    /**
     *
     * @param request Petición HTTP
     * @return La dirección IP del requerimiento o vacío en cualquier caso de
     * error.
     */
    public static String obtenerIp(HttpServletRequest request) {
        String ip = StringUtils.EMPTY;
        try {
            if (request == null) {
                logger.error("La petición viene NULA");
            } else {
                ip = StringUtils.trimToEmpty(request.getRemoteAddr());

                String redirecciones = StringUtils.trimToEmpty(request.getHeader("X-Forwarded-For"));
                if (StringUtils.contains(redirecciones, ',')) {
                    String[] arreglo = StringUtils.split(redirecciones, ',');
                    if (arreglo != null) {
                        if (arreglo.length > 0) {
                            ip = StringUtils.trimToEmpty(arreglo[0]);
                        }
                    }
                } else if (StringUtils.isNotBlank(redirecciones)) {
                    ip = StringUtils.trimToEmpty(redirecciones);
                } else {
                    ip = StringUtils.trimToEmpty(request.getRemoteAddr());
                }
            }
        } catch (Exception e) {
            ip = StringUtils.EMPTY;
            logger.error("Error al consultar IP: {}", e.toString());
            logger.debug("Error al consultar IP: {}", e.toString(), e);
        }
        return ip;
    }

    /**
     * Crea un objeto respuesta con la excepción para entregar información al
     * usuario usando un mensaje genérico.
     *
     * @return Response
     */
    public static Response error500() {
        RespuestaVO respuesta = new RespuestaVO();
        respuesta.setOk(false);
        respuesta.setMensaje("Ha ocurrido un error inesperado procesando su solicitud");

        Response.ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        builder.entity(respuesta);
        return builder.build();
    }

    /**
     * Crea un objeto respuesta con la excepción para entregar información al
     * usuario
     *
     * @param e Excepción
     * @return Response
     */
    public static Response error500(Exception e) {
        Response salida = null;
        if (e != null) {
            RespuestaVO respuesta = new RespuestaVO(String.format("Error al procesar petición, servidor respondió: '%s'", e.toString()));

            Response.ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            builder.entity(respuesta);
            salida = builder.build();
        } else {
            salida = error500();
        }
        return salida;
    }

    /**
     * Crea un objeto respuesta, con un mensaje genérico
     *
     * @return Response
     */
    public static Response error403() {
        RespuestaVO respuesta = new RespuestaVO("No tiene credenciales para acceder a este contenido");

        Response.ResponseBuilder builder = Response.status(Response.Status.FORBIDDEN);
        builder.entity(respuesta);
        return builder.build();
    }

    /**
     * Crea un objeto respuesta, con un mensaje específico
     *
     * @param mensaje Texto a desplegar
     * @return Response
     */
    public static Response error403(String mensaje) {
        Response salida = null;
        if (StringUtils.isNotBlank(mensaje)) {
            RespuestaVO respuesta = new RespuestaVO(mensaje);

            Response.ResponseBuilder builder = Response.status(Response.Status.FORBIDDEN);
            salida = builder.entity(respuesta).build();
        } else {
            salida = error403();
        }
        return salida;
    }

    /**
     * Crea un objeto respuesta, con un mensaje genérico
     *
     * @return Response
     */
    public static Response error404() {
        RespuestaVO respuesta = new RespuestaVO("La información solicitada no ha sido encontrada en el servidor");

        Response.ResponseBuilder builder = Response.status(Response.Status.NOT_FOUND);
        builder.entity(respuesta);
        return builder.build();
    }

    /**
     * Crea un objeto respuesta, con un mensaje específico
     *
     * @param mensaje Texto a desplegar
     * @return Response
     */
    public static Response error404(String mensaje) {
        Response salida = null;
        if (StringUtils.isNotBlank(mensaje)) {
            RespuestaVO respuesta = new RespuestaVO(mensaje);

            Response.ResponseBuilder builder = Response.status(Response.Status.NOT_FOUND);
            salida = builder.entity(respuesta).build();
        } else {
            salida = error404();
        }
        return salida;
    }

    /**
     * Crea un objeto respuesta con el error 422, indicando que el objeto no
     * pudo ser procesado.
     *
     * @return Response
     */
    public static Response error422() {
        RespuestaVO respuesta = new RespuestaVO("El objeto no pudo ser procesado");

        Response.ResponseBuilder builder = Response.status(Response.Status.NOT_FOUND);
        builder.entity(respuesta);
        return builder.build();
    }

    /**
     * Crea un objeto respuesta con el error 422, indicando que el objeto no
     * pudo ser procesado.
     *
     * @param mensaje Mensaje que se mostrará
     * @return Response
     */
    public static Response error422(String mensaje) {
        Response salida = null;
        if (StringUtils.isNotBlank(mensaje)) {
            RespuestaVO respuesta = new RespuestaVO(mensaje);

            Response.ResponseBuilder builder = Response.status(Response.Status.NOT_FOUND);
            salida = builder.entity(respuesta).build();
        } else {
            salida = error422();
        }
        return salida;
    }

    public static boolean isUserInRole(String role) {
        boolean ok = false;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                ok = authentication.getAuthorities().contains(new SimpleGrantedAuthority(role));
            }
        } catch (Exception e) {
            ok = false;
            logger.error("Error al determinar Roles de Usuario: {}", e.toString());
        }
        return ok;
    }
}
