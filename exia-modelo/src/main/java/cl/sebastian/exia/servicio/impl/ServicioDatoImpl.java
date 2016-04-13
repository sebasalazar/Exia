package cl.sebastian.exia.servicio.impl;

import cl.sebastian.exia.modelo.Dato;
import cl.sebastian.exia.repository.DatoRepository;
import cl.sebastian.exia.servicio.ServicioDato;
import java.io.Serializable;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Dato> consultarDatos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public Dato guardar(Dato dato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public boolean eliminar(Dato dato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
