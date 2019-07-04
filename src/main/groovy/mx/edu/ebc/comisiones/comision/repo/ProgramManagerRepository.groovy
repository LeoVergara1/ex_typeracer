package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.Promoter;
import mx.edu.ebc.comisiones.comision.domain.ProgramManager;
import mx.edu.ebc.comisiones.comision.domain.PidmAndUserNamePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
interface ProgramManagerRepository extends JpaRepository<ProgramManager,PidmAndUserNamePK> {

  ProgramManager findOneById_UserName(String userName)
  ProgramManager findOneById_Pidm(Long pidm)
  ProgramManager findOneById_RecrCode(String recrCode)

}