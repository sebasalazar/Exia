package cl.sebastian.exia.rest.vo;

import cl.sebastian.exia.modelo.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Sebastián Salazar Molina
 */
@ApiModel
public class DatoVO extends BaseBean {

    private Long id = null;
    private Date fecha = null;
    private String ip = null;
    private Integer codigo = null;
    private String nombre = null;
    private String rut = null;
    private BigDecimal valor = null;
    private Double latitud = null;
    private Double longitud = null;

    @ApiModelProperty(required = true, value = "Identificador del Dato.")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ApiModelProperty(required = true, value = "Fecha del dato.")
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @ApiModelProperty(required = true, value = "IP desde donde se envió el dato.")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @ApiModelProperty(required = true, value = "Código único.")
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @ApiModelProperty(required = true, value = "Nombre de quien envió el dato.")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @ApiModelProperty(required = true, value = "Rut de quien envió el dato.")
    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    @ApiModelProperty(required = true, value = "Valor numérico del dato.")
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @ApiModelProperty(required = true, value = "Latitud del envio.")
    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    @ApiModelProperty(required = true, value = "Longitud del envio.")
    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DatoVO other = (DatoVO) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
