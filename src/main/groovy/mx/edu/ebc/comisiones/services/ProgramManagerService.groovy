package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.pojos.PromoterCode
import mx.edu.ebc.comisiones.comision.domain.ProgramManager

interface ProgramManagerService {

  ProgramManager findOneById_UserName(String userName)
  ProgramManager findOneById_Pidm(Long pidm)
  ProgramManager findOneById_RecrCode(String recrCode)

}