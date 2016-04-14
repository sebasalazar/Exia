package cl.sebastian.exia.rest.api.v1.impl;

import cl.sebastian.exia.modelo.Dato;
import cl.sebastian.exia.rest.api.v1.ExiaRest;
import cl.sebastian.exia.rest.utils.RestHelperUtils;
import cl.sebastian.exia.rest.utils.RestUtils;
import cl.sebastian.exia.rest.vo.DatoVO;
import cl.sebastian.exia.rest.vo.RespuestaVO;
import cl.sebastian.exia.servicio.ServicioDato;
import cl.sebastian.exia.utils.RutUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina
 */
@Path("/exia")
@Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8"})
@Api(value = "/exia", consumes = "application/json", produces = "application/json")
public class ExiaRestImpl implements ExiaRest {

    private static final long serialVersionUID = 7760131315025296384L;
    @Resource(name = "servicioDato")
    private ServicioDato servicioDato;
    private static final Logger logger = LoggerFactory.getLogger(ExiaRestImpl.class);

    @Override
    @GET
    @Path("/consultar/datos")
    @ApiOperation(value = "Consulta los datos existentes en el sistema",
            notes = "Trae todos los datos ordenados por fecha descendentemente",
            responseContainer = "List",
            response = DatoVO.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Respuesta con los datos", responseContainer = "List", response = DatoVO.class),
        @ApiResponse(code = 404, message = "No existen datos", response = RespuestaVO.class),
        @ApiResponse(code = 500, message = "Error interno del servidor", response = RespuestaVO.class)
    })
    public Response consultarDatos() {
        Response respuesta = RestUtils.error404("No existen datos");
        try {
            List<Dato> lista = servicioDato.consultarDatos();
            List<DatoVO> datos = RestHelperUtils.convertirDatos(lista);
            if (datos != null) {
                if (!datos.isEmpty()) {
                    respuesta = Response.ok(datos).build();
                }
            }
        } catch (Exception e) {
            respuesta = RestUtils.error500(e);
            logger.error("Error al consultar datos: {}", e.toString());
            logger.debug("Error al consultar datos: {}", e.toString(), e);
        }
        return respuesta;
    }

    @Override
    @GET
    @Path("/consultar/datos/{rut}")
    @ApiOperation(value = "Consulta los datos existentes en el sistema",
            notes = "Trae todos los datos asociados al rut, ordenados por fecha descendentemente")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Respuesta con los datos", responseContainer = "List", response = DatoVO.class),
        @ApiResponse(code = 404, message = "No existen datos", response = RespuestaVO.class),
        @ApiResponse(code = 500, message = "Error interno del servidor", response = RespuestaVO.class)
    })
    public Response consultarDatos(
            @ApiParam(value = "Rut por el cuál se buscará", example = "12.111.111-K", required = true)
            @PathParam("rut") Integer rut) {
        Response respuesta = RestUtils.error404("No existen datos");
        try {
            List<Dato> lista = servicioDato.consultarDatos();
            List<DatoVO> datos = RestHelperUtils.convertirDatos(lista);
            if (datos != null) {
                if (!datos.isEmpty()) {
                    respuesta = Response.ok(datos).build();
                }
            }
        } catch (Exception e) {
            respuesta = RestUtils.error500(e);
            logger.error("Error al consultar datos: {}", e.toString());
            logger.debug("Error al consultar datos: {}", e.toString(), e);
        }
        return respuesta;
    }

    @Override
    @POST
    @Path("/persistir/dato")
    @ApiOperation(value = "Persiste un dato en el sistema",
            notes = "Se utiliza POST y no un PUT porque esta no es una operación idempotente")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Respuesta con los datos persistidos", response = DatoVO.class),
        @ApiResponse(code = 404, message = "No existen datos", response = RespuestaVO.class),
        @ApiResponse(code = 422, message = "No se puede procesar la petición", response = RespuestaVO.class),
        @ApiResponse(code = 500, message = "Error interno del servidor", response = RespuestaVO.class)
    })
    public Response guardarDatos(
            @ApiParam(value = "Fecha del dato en formato yyyy-MM-dd'T'HH:mm:ss.SZ", example = "2016-03-13T14:50:56.235-0300", required = true)
            @FormParam("fecha") Date fecha,
            @ApiParam(value = "Nombre de quien guarda el dato", example = "Juan Peréz", required = true)
            @FormParam("nombre") String nombre,
            @FormParam("rut") String rut,
            @ApiParam(value = "Rut de quien guarda el dato", example = "12.111.111-K", required = true)
            @FormParam("valor") BigDecimal valor,
            @ApiParam(value = "Latitud de la petición", example = "-33.4428798", required = true)
            @FormParam("latitud") Double latitud,
            @ApiParam(value = "Longitud de la petición", example = "-70.6619173", required = true)
            @FormParam("longitud") Double longitud) {
        Response respuesta = RestUtils.error422();
        try {
            Dato dato = new Dato();
            dato.setFecha(fecha);
            dato.setIp(RestUtils.obtenerIp());
            dato.setLatitud(latitud);
            dato.setLongitud(longitud);
            dato.setNombre(StringUtils.trimToEmpty(nombre));
            dato.setRut(RutUtils.parseRut(rut));
            dato.setValor(valor);

            Dato salida = servicioDato.guardar(dato);
            if (salida != null) {
                DatoVO datoVO = RestHelperUtils.convertir(dato);
                if (datoVO != null) {
                    respuesta = Response.ok(datoVO).build();
                }
            }
        } catch (Exception e) {
            respuesta = RestUtils.error500(e);
            logger.error("Error al guardar datos: {}", e.toString());
            logger.debug("Error al guardar datos: {}", e.toString(), e);
        }
        return respuesta;
    }

}
