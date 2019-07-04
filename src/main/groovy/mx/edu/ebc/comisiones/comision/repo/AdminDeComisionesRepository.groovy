package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
interface AdminDeComisionesRepository extends JpaRepository<AdminDeComisiones,Integer> {
	List<AdminDeComisiones> findAll()
	AdminDeComisiones findById(Integer id)
}