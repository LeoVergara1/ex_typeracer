package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.Promoter;
import mx.edu.ebc.comisiones.comision.domain.PidmAndUserNamePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
public interface PromoterRepository extends JpaRepository<Promoter,PidmAndUserNamePK> {

}