package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.data.AdminDeComisiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
public interface AdminDeComisionesRepository extends JpaRepository<AdminDeComisiones,Integer> {
	List<AdminDeComisiones> findAll()
}