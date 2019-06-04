package mx.edu.ebc.comisiones.seguridad.repo

import mx.edu.ebc.comisiones.seguridad.domain.Portales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
public interface PortalRepository extends JpaRepository<Portales,Integer> {
	List<Portales> findAll()
}