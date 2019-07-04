package mx.edu.ebc.comisiones.seguridad.repo

import mx.edu.ebc.comisiones.seguridad.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
interface RolesRepository extends JpaRepository<Roles,Integer> {
	List<Roles> findAll()
	List<Roles> findAllByNidRolPortal(String nidRolPortal)
	List<Roles> findAllByDescriptionRol(String descriptionRol)
}