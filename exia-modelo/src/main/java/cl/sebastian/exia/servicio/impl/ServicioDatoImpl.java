package cl.sebastian.exia.servicio.impl;

import cl.sebastian.exia.modelo.Dato;
import cl.sebastian.exia.repository.DatoRepository;
import cl.sebastian.exia.servicio.ServicioDato;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sebastián Salazar Molina
 */
@Service("servicioDato")
public class ServicioDatoImpl implements ServicioDato, Serializable {

    private static final long serialVersionUID = 7810277056298273792L;
    @Resource(name = "datoRepository")
    private DatoRepository datoRepository;
    private static final Logger logger = LoggerFactory.getLogger(ServicioDatoImpl.class);

    @Override
    public Dato consultarDato(Long id) {
        Dato dato = null;
        try {
            if (id != null) {
                dato = datoRepository.findOne(id);
            }
        } catch (Exception e) {
            dato = null;
            logger.error("Error al consultar dato: {}", e.toString());
            logger.debug("Error al consultar dato: {}", e.toString(), e);
        }
        return dato;
    }

    @Override
    public Dato consultarDatoPorCodigo(Integer codigo) {
        Dato dato = null;
        try {
            if (codigo != null) {
                dato = datoRepository.findByCodigo(codigo);
            }
        } catch (Exception e) {
            dato = null;
            logger.error("Error al consultar dato: {}", e.toString());
            logger.debug("Error al consultar dato: {}", e.toString(), e);
        }
        return dato;
    }

    @Override
    public List<Dato> consultarDatos() {
        List<Dato> datos = new ArrayList<Dato>();
        try {
            datos = datoRepository.findByOrderByFechaDesc();
        } catch (Exception e) {
            datos = new ArrayList<Dato>();
            logger.error("Error al consultar datos: {}", e.toString());
            logger.debug("Error al consultar datos: {}", e.toString(), e);
        }
        return datos;
    }

    @Override
    public List<Dato> consultarDatos(Integer rut) {
        List<Dato> datos = new ArrayList<Dato>();
        try {
            if (rut != null) {
                datos = datoRepository.findByRutOrderByFechaDesc(rut);
            }
        } catch (Exception e) {
            datos = new ArrayList<Dato>();
            logger.error("Error al consultar datos: {}", e.toString());
            logger.debug("Error al consultar datos: {}", e.toString(), e);
        }
        return datos;
    }

    @Override
    @Transactional
    public Dato guardar(Dato dato) {
        Dato salida = null;
        try {
            if (dato != null) {
                salida = datoRepository.saveAndFlush(dato);
            }
        } catch (Exception e) {
            salida = null;
            logger.error("Error al guardar dato: {}", e.toString());
            logger.debug("Error al guardar dato: {}", e.toString(), e);
        }
        return salida;
    }

    @Override
    @Transactional
    public boolean eliminar(Dato dato) {
        boolean ok = false;
        try {
            if (dato != null) {
                datoRepository.delete(dato);
                ok = true;
            }
        } catch (Exception e) {
            ok = false;
            logger.error("Error al eliminar dato: {}", e.toString());
            logger.debug("Error al eliminar dato: {}", e.toString(), e);
        }
        return ok;
    }
}
