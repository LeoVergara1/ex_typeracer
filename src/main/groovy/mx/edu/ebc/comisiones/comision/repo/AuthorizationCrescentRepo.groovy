
package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.AuthorizationCrescent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
interface AuthorizationCrescentRepository extends JpaRepository<AuthorizationCrescent,Integer> {
	List<AuthorizationCrescent> findAll()
	List<AuthorizationCrescent> findAllByAutorizadoDirector(String autorizadoDirector)
	List<AuthorizationCrescent> findAllByAutorizadoDirectorAndFechaAutorizadoBetween(String autorizadoDirector, Date initDate, Date finDate)
	List<AuthorizationCrescent> findAllByStatusMarketingAndFechaAutorizadoBetween(Boolean statusMarketing, Date initDate, Date finDate)
	List<AuthorizationCrescent> findAllByStatusMarketingAndStatusRectorAndFechaAutorizadoBetween(Boolean statusMarketing, Boolean statusRector, Date initDate, Date finDate)
	List<AuthorizationCrescent> findAllByAutorizadoDirectorAndCampusAndFechaAutorizadoBetween(String autorizadoDirector, String campus, Date initDate, Date finDate)
	List<AuthorizationCrescent> findAllByStatusMarketingAndCampusAndFechaAutorizadoBetween(Boolean statusMarketing, String campus, Date initDate, Date finDate)
	List<AuthorizationCrescent> findAllByStatusMarketingAndStatusRectorAndCampusAndFechaAutorizadoBetween(Boolean statusMarketing, Boolean statusRector, String campus, Date initDate, Date finDate)
	AuthorizationCrescent findByIdPromotorAndIdCoordinadorAndIdAlumno(String idPromoter, String idCoordinator, String idAlumno)
}