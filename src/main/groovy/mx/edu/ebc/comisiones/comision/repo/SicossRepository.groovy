package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.Sicoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query

@Repository
interface SicossRepository extends JpaRepository<Sicoss, Integer> {
  List<Sicoss> findAllByClaveEmployeeAndDateMovenmentBetween(String claveEmployee, Date initDate, Date endDate)
}