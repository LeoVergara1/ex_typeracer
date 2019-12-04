package mx.edu.ebc.comisiones.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wslite.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import mx.edu.ebc.comisiones.comision.domain.UserCampus
import mx.edu.ebc.comisiones.comision.domain.UserCampusComposite
import mx.edu.ebc.comisiones.comision.repo.UserCampusRepository

@Service
class UserCampusServiceImpl implements UserCampusService{

  Logger logger = LoggerFactory.getLogger(UserCampusServiceImpl.class)

	@Autowired
	UserCampusRepository userCampusRepository

	UserCampus created(String campusCode, String userName, Long pidm, String nameLong, String roleDescription) {
		logger.debug("CREANDO USUARIO")
    UserCampusComposite pk = new UserCampusComposite(
            campusCode: campusCode,
            userName: userName,
            pidm: pidm
    )
		UserCampus userCampus = new UserCampus(
                            userCampusPK: pk,
                            dateCreated: new Date(),
                            lastUpdated: new Date(),
                            nameLong: nameLong,
                            roleDescription: roleDescription
                           )
		userCampusRepository.save(userCampus)
	}

	void delete(String codeCampus,String userName) {
    log.debug("ELIMINANDO USUARIO")
    //UserCampus userCampus = findByCampusCodeAndUserName(codeCampus,userName)
    //if(userCampus)
    //userCampusRepository.delete(userCampus)
  }

  List<UserCampus> findByUserCampusPK_UserName(String userName){
    userCampusRepository.findByUserCampusPK_UserName(userName)
  }

  UserCampus findByCampusCodeAndUserName(String codeCampus, String userName){
    userCampusRepository.findByCampusCodeAndUserName(codeCampus, userName)
  }

  UserCampus deleteByCodeCampusAndUserName(String codeCampus, String userName){
    UserCampus userCampus = findByCampusCodeAndUserName(codeCampus, userName)
    userCampus ? userCampusRepository.delete(userCampus) : userCampus
  }
}