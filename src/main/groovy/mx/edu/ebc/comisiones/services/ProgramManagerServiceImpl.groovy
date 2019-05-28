package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.pojos.PromoterCode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import mx.edu.ebc.comisiones.comision.domain.ProgramManager
import mx.edu.ebc.comisiones.comision.repo.ProgramManagerRepository
import org.springframework.stereotype.Service

@Service
class ProgramManagerServiceImpl implements ProgramManagerService {

	Logger logger = LoggerFactory.getLogger(ProgramManagerServiceImpl.class)

	@Autowired
	ProgramManagerRepository programManagerRepository

  ProgramManager findOneById_UserName(String userName){
		programManagerRepository.findOneById_UserName(userName)
	}

  ProgramManager findOneById_Pidm(Long pidm){
		programManagerRepository.findOneById_Pidm(pidm)
	}

  ProgramManager findOneById_RecrCode(String recrCode){
		programManagerRepository.findOneById_RecrCode(recrCode)
	}

}