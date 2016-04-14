package cl.sebastian.exia.repository;

import cl.sebastian.exia.modelo.Dato;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Sebastián Salazar Molina
 */
@Resource(name = "datoRepository")
public interface DatoRepository extends JpaRepository<Dato, Long> {

    /**
     *
     * @return La lista de datos ordenadas por fecha de forma descendente.
     */
    public List<Dato> findByOrderByFechaDesc();

    /**
     *
     * @param rut Rut en formato númerico sin dígito verificador
     * @return La lista de datos cuyo rut coincida, ordenados por fecha de forma
     * descendente.
     */
    public List<Dato> findByRutOrderByFechaDesc(Integer rut);
}
