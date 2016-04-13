package cl.sebastian.exia.modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sebastián Salazar Molina
 */
@Entity
@Table(name = "datos")
public class Dato extends BaseBean {

    private static final long serialVersionUID = 1954082798024307712L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    @Column(name = "pk", nullable = false)
    private Long id = null;
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha = new Date();
    @Column(name = "ip", nullable = false)
    private String ip = null;
    @Column(name = "nombre", nullable = false)
    private String nombre = null;
    @Column(name = "valor", nullable = false)
    private BigDecimal valor = null;
    @Column(name = "latitud", nullable = false)
    private Double latitud = null;
    @Column(name = "longitud", nullable = false)
    private Double longitud = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Dato other = (Dato) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
