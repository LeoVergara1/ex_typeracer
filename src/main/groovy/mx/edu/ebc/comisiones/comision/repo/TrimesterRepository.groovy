package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.Trimester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query

@Repository
interface TrimesterRepository extends JpaRepository<Trimester, Integer> {
	List<Trimester> findAll()
	List<Trimester> findAllByYear(String year)
	Trimester findByClave(String clave)
	Trimester findById(Integer id)
	Trimester findByInitDateGreaterThanAndEndDateLessThan(Date initDate, Date endDate)
}