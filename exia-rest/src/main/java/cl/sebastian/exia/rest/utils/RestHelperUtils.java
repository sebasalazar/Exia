package cl.sebastian.exia.rest.utils;

import cl.sebastian.exia.modelo.Dato;
import cl.sebastian.exia.rest.vo.DatoVO;
import cl.sebastian.exia.utils.RutUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina
 */
public class RestHelperUtils implements Serializable {
    
    private static final long serialVersionUID = 1333194089290520576L;
    private static final Logger logger = LoggerFactory.getLogger(RestHelperUtils.class);
    
    public static DatoVO convertir(Dato dato) {
        DatoVO salida = null;
        if (dato != null) {
            salida = new DatoVO();
            salida.setFecha(dato.getFecha());
            salida.setId(dato.getId());
            salida.setIp(StringUtils.trimToEmpty(dato.getIp()));
            salida.setLatitud(dato.getLatitud());
            salida.setLongitud(dato.getLongitud());
            salida.setNombre(StringUtils.trimToEmpty(dato.getNombre()));
            salida.setRut(RutUtils.formatRut(dato.getRut()));
            salida.setValor(dato.getValor());
        }
        return salida;
    }
    
    public static List<DatoVO> convertirDatos(List<Dato> datos) {
        List<DatoVO> lista = new ArrayList<DatoVO>();
        if (datos != null) {
            if (!datos.isEmpty()) {
                for (Dato dato : datos) {
                    DatoVO datoVO = convertir(dato);
                    if (datoVO != null) {
                        lista.add(datoVO);
                    }
                }
            }
        }
        return lista;
    }
}
