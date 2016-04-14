package cl.sebastian.exia.rest.vo;

import cl.sebastian.exia.modelo.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Sebastián Salazar Molina
 */
@ApiModel
public class RespuestaVO extends BaseBean {

    private static final long serialVersionUID = 7853188795284237312L;

    private boolean ok = false;
    private String mensaje = null;
    private Date fecha = new Date();

    /**
     * Constructor. Setea a ok a false, mensaje a vacío y la fecha actual.
     */
    public RespuestaVO() {
        this.ok = false;
        this.mensaje = StringUtils.EMPTY;
        this.fecha = new Date();
    }

    /**
     * Constructor. Setea a ok a false, utiliza el mensaje del parámetro y la
     * fecha actual.
     *
     * @param mensaje Mensaje a usar
     */
    public RespuestaVO(String mensaje) {
        this.ok = false;
        this.mensaje = StringUtils.trimToEmpty(mensaje);
        this.fecha = new Date();
    }

    /**
     * Constructor. Setea a ok utilizando el parámetro, utiliza el mensaje del
     * parámetro y la fecha actual.
     *
     * @param ok Propiedad de la respuesta
     * @param mensaje Mensaje a usar
     */
    public RespuestaVO(boolean ok, String mensaje) {
        this.ok = ok;
        this.mensaje = StringUtils.trimToEmpty(mensaje);
        this.fecha = new Date();
    }

    @ApiModelProperty(position = 1, required = true, value = "Estado de la respuesta, por defecto es falso.")
    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    @ApiModelProperty(position = 2, required = true, value = "Mensaje explicativo de la respuesta")
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @ApiModelProperty(position = 3, required = true, value = "Fecha de generación de la respuesta")
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
