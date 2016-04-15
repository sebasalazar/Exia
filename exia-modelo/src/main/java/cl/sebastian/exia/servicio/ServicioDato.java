package cl.sebastian.exia.servicio;

import cl.sebastian.exia.modelo.Dato;
import java.util.List;

/**
 *
 * @author Sebastián Salazar Molina
 */
public interface ServicioDato {

    public Dato consultarDato(Long id);

    public Dato consultarDatoPorCodigo(Integer codigo);

    public List<Dato> consultarDatos();

    public List<Dato> consultarDatos(Integer rut);

    public Dato guardar(Dato dato);

    public boolean eliminar(Dato dato);
}
