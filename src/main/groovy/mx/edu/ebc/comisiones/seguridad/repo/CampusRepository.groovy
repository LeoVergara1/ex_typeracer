package mx.edu.ebc.comisiones.seguridad.repo

import mx.edu.ebc.comisiones.seguridad.domain.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
interface CampusRepository extends JpaRepository<Campus,Integer> {
	List<Campus> findAll()
}