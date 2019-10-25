package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.AuthorizationComission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
interface AuthorizationRepository extends JpaRepository<AuthorizationComission,Integer> {
	List<AuthorizationComission> findAll()
	List<AuthorizationComission> findAllByAutorizadoDirector(String autorizadoDirector)
	List<AuthorizationComission> findAllByAutorizadoDirectorAndFechaAutorizadoBetween(String autorizadoDirector, Date initDate, Date finDate)
	List<AuthorizationComission> findAllByStatusMarketingAndFechaAutorizadoBetween(Boolean statusMarketing, Date initDate, Date finDate)
	List<AuthorizationComission> findAllByStatusMarketingAndStatusRectorAndFechaAutorizadoBetween(Boolean statusMarketing, Boolean statusRector, Date initDate, Date finDate)
	List<AuthorizationComission> findAllByAutorizadoDirectorAndCampusAndFechaAutorizadoBetween(String autorizadoDirector, String campus, Date initDate, Date finDate)
	List<AuthorizationComission> findAllByStatusMarketingAndCampusAndFechaAutorizadoBetween(Boolean statusMarketing, String campus, Date initDate, Date finDate)
	AuthorizationComission findByIdPromotorAndIdCoordinadorAndIdAlumno(String idPromoter, String idCoordinator, String idAlumno)
}