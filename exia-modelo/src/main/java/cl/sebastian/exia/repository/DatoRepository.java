package cl.sebastian.exia.repository;

import cl.sebastian.exia.modelo.Dato;
import javax.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Sebastián Salazar Molina
 */
@Resource(name = "datoRepository")
public interface DatoRepository extends JpaRepository<Dato, Long> {

}
