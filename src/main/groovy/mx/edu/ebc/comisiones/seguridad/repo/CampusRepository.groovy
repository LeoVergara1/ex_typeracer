package mx.edu.ebc.comisiones.seguridad.repo

import mx.edu.ebc.comisiones.seguridad.data.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
public interface CampusRepository extends JpaRepository<Campus,Integer> {

}