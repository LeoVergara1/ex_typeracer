package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.UserCampus;
import mx.edu.ebc.comisiones.comision.domain.UserCampusComposite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
public interface UserCampusRepository extends JpaRepository<UserCampus,UserCampusComposite> {

}