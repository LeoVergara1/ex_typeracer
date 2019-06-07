package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.AuthorizationComission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthorizationComission,Integer> {
	List<AuthorizationComission> findAll()
}