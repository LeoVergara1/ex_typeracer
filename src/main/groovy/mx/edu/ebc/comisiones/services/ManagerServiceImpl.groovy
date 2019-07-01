package mx.edu.ebc.comisiones.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Value
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import mx.edu.ebc.comisiones.comision.domain.ProgramManager
import mx.edu.ebc.comisiones.comision.domain.PidmAndUserNamePK
import mx.edu.ebc.comisiones.comision.repo.ProgramManagerRepository
import mx.edu.ebc.comisiones.comision.repo.PromoterRepository

@Service
class ManagerServiceImpl implements ManagerService {

  Logger logger = LoggerFactory.getLogger(ManagerServiceImpl.class)
  private static String ALREADY_EXISTS = "ERROR, this relation already exists"
  private static String RECR_CODE = "ERROR, this recruiter code already exists"
  private static String NOT_FOUND = "ERROR, this promoter does not exists"
  private static String MANAGER_NOT_FOUND = "ERROR, this manager does not exists"
  private static String DB_ERROR = "ERROR, data base error"
  private static String SUCCESS = "Operation succesfully achieved..."

  @Value('${url.apicomisiones}')
  String clientComissions
  @Autowired
  ProgramManagerRepository programManagerRepository
  @Autowired
  PromoterService promoterService
  @Autowired
  PromoterRepository promoterRepository

  @Override
  Map createManager(String userName, Long pidm, String recrCode) {
    logger.info "Creating new Program Manager: $userName with pidm: $pidm and recrCode: $recrCode"
    PidmAndUserNamePK id = new PidmAndUserNamePK(pidm: pidm, userName: userName, recrCode: recrCode)
    if (programManagerRepository.findOneById_RecrCode(recrCode) || promoterService.isAPromoterSavedWithRecrCode(recrCode)) {
      logger.info RECR_CODE
      return [message: RECR_CODE, created: false]
    }
    if(programManagerRepository.findOneById_UserName(userName)){
      logger.error(ALREADY_EXISTS)
      return [message: ALREADY_EXISTS, created: false]
    }
    ProgramManager programManager = new ProgramManager(id: id)
    programManager = programManagerRepository.save(programManager)
    if (programManager){
      logger.info("Successfuly created...")
      [message: "Successfuly created...", created: true]
    }
    else{
      logger.error(DB_ERROR)
      [message: DB_ERROR, created: false]
    }
  }

  @Override
  boolean deleteManager(String userName) {
    logger.info "Deleting manager: $userName"
    def manager = programManagerRepository.findOneById_UserName(userName)
    if(manager){
      deletePromotersFromManager(manager)
      programManagerRepository.delete(manager)
      return true
    }
    false
  }

  void deletePromotersFromManager(ProgramManager manager){
    manager.promoters?.each(){ promoter ->
      promoter.programManager = null
      promoterRepository.save(promoter)
    }
  }
}
