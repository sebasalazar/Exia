package cl.sebastian.exia.rest.api.v1.impl;

import cl.sebastian.exia.modelo.Dato;
import cl.sebastian.exia.rest.api.v1.ExiaRest;
import cl.sebastian.exia.rest.utils.RestHelperUtils;
import cl.sebastian.exia.rest.utils.RestUtils;
import cl.sebastian.exia.rest.vo.DatoVO;
import cl.sebastian.exia.rest.vo.RespuestaVO;
import cl.sebastian.exia.servicio.ServicioDato;
import cl.sebastian.exia.utils.FechaUtils;
import cl.sebastian.exia.utils.RutUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
public class ExiaRestImpl implements ExiaRest, Serializable {

    private static final long serialVersionUID = 7760131315025296384L;
    @Resource(name = "servicioDato")
    private ServicioDato servicioDato;
    private static final Logger logger = LoggerFactory.getLogger(ExiaRestImpl.class);

    @Override
    @GET
    @Path("/consultar/dato/{codigo}")
    @ApiOperation(value = "Consulta el dato por su código")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Respuesta con los datos", response = DatoVO.class),
        @ApiResponse(code = 404, message = "No existen datos", response = RespuestaVO.class),
        @ApiResponse(code = 500, message = "Error interno del servidor", response = RespuestaVO.class)
    })
    public Response consultarDatosPorCodigo(
            @ApiParam(value = "Código para la búsqueda", example = "17", required = true)
            @PathParam("codigo") Integer codigo) {
        Response respuesta = RestUtils.error404("No se ha encontrado el dato");
        try {
            Dato dato = servicioDato.consultarDatoPorCodigo(codigo);
            if (dato != null) {
                DatoVO datoVO = RestHelperUtils.convertir(dato);
                if (datoVO != null) {
                    respuesta = Response.ok(datoVO).build();
                }
            }
        } catch (Exception e) {
            respuesta = RestUtils.error500(e);
            logger.error("Error al consultar dato: {}", e.toString());
            logger.debug("Error al consultar dato: {}", e.toString(), e);
        }
        return respuesta;
    }

    @Override
    @GET
    @Path("/consultar/datos")
    @ApiOperation(value = "Consulta los datos existentes en el sistema",
            notes = "Trae todos los datos ordenados por fecha descendentemente")
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
            @PathParam("rut") String rut) {
        Response respuesta = RestUtils.error404("No existen datos");
        try {
            Integer rutNum = RutUtils.parseRut(rut);
            if (rutNum != null) {
                List<Dato> lista = servicioDato.consultarDatos(rutNum);
                List<DatoVO> datos = RestHelperUtils.convertirDatos(lista);
                if (datos != null) {
                    if (!datos.isEmpty()) {
                        respuesta = Response.ok(datos).build();
                    }
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
        @ApiResponse(code = 400, message = "La petición es inválida", response = RespuestaVO.class),
        @ApiResponse(code = 404, message = "No existen datos", response = RespuestaVO.class),
        @ApiResponse(code = 422, message = "No se puede procesar la petición", response = RespuestaVO.class),
        @ApiResponse(code = 500, message = "Error interno del servidor", response = RespuestaVO.class)
    })
    public Response guardarDatos(
            @ApiParam(value = "Código único", example = "17", required = true)
            @QueryParam("codigo") Integer codigo,
            @ApiParam(value = "Fecha del dato en formato: yyyy-MM-dd HH:mm:ss", example = "2016-04-14 18:25:52", required = true)
            @QueryParam("fecha") String fechaStr,
            @ApiParam(value = "Nombre de quien guarda el dato", example = "Juan Peréz", required = true)
            @QueryParam("nombre") String nombre,
            @ApiParam(value = "Rut de quien guarda el dato", example = "12.111.111-K", required = true)
            @QueryParam("rut") String rut,
            @ApiParam(value = "Valor numérico del dato", example = "17.7", required = true)
            @QueryParam("valor") BigDecimal valor,
            @ApiParam(value = "Latitud de la petición", example = "-33.4428798", required = true)
            @QueryParam("latitud") Double latitud,
            @ApiParam(value = "Longitud de la petición", example = "-70.6619173", required = true)
            @QueryParam("longitud") Double longitud) {
        Response respuesta = RestUtils.error422();
        try {
            Integer rutNum = RutUtils.parseRut(rut);
            Date fecha = FechaUtils.crearFecha(fechaStr, "yyyy-MM-dd HH:mm:ss");

            if (rutNum != null && fecha != null && codigo != null) {
                Dato dato = servicioDato.consultarDatoPorCodigo(codigo);
                if (dato == null) {
                    dato = new Dato();
                    dato.setCodigo(codigo);
                    dato.setFecha(fecha);
                    dato.setIp(RestUtils.obtenerIp());
                    dato.setLatitud(latitud);
                    dato.setLongitud(longitud);
                    dato.setNombre(StringUtils.trimToEmpty(nombre));
                    dato.setRut(rutNum);
                    dato.setValor(valor);
                } else {
                    dato.setFecha(fecha);
                    dato.setIp(RestUtils.obtenerIp());
                    dato.setLatitud(latitud);
                    dato.setLongitud(longitud);
                    dato.setNombre(StringUtils.trimToEmpty(nombre));
                    dato.setRut(rutNum);
                    dato.setValor(valor);
                }

                Dato salida = servicioDato.guardar(dato);
                if (salida != null) {
                    DatoVO datoVO = RestHelperUtils.convertir(dato);
                    if (datoVO != null) {
                        respuesta = Response.ok(datoVO).build();
                    }
                }
            } else {
                respuesta = RestUtils.error400("Rut inválido");
            }
        } catch (Exception e) {
            respuesta = RestUtils.error500(e);
            logger.error("Error al guardar datos: {}", e.toString());
            logger.debug("Error al guardar datos: {}", e.toString(), e);
        }
        return respuesta;
    }
}
