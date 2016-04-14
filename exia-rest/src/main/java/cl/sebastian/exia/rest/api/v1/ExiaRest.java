package cl.sebastian.exia.rest.api.v1;

import java.math.BigDecimal;
import java.util.Date;
import javax.ws.rs.core.Response;

/**
 *
 * @author Sebastián Salazar Molina
 */
public interface ExiaRest {

    public Response consultarDatos();

    public Response consultarDatos(Integer rut);

    public Response guardarDatos(Date fecha, String nombre, String rut, BigDecimal valor, Double latitud, Double longitud);
}
