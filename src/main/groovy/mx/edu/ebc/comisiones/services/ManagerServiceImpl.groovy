package mx.edu.ebc.comisiones.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Value
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Service
class ManagerServiceImpl implements ManagerService {

  Logger logger = LoggerFactory.getLogger(ManagerServiceImpl.class)

  @Autowired
  RestConnectionService restConnectionService
	@Value('${url.apicomisiones}')
	String clientComissions

  @Override
  boolean createManager(String userName, Long pidm, String recrCode) {
    logger.info "Saving new manager: $userName"
    def postStatus = restConnectionService.post(
            clientComissions,
            "/v1/api/manager/program/",
            [user_name: userName,
             pidm: pidm,
             recr_code: recrCode])
    postStatus?: logger.error("ERROR creating manager: $userName)")
    postStatus ? true : false
  }

  @Override
  boolean deleteManager(String userName) {
    logger.info "Deleting manager: $userName"
    def postStatus = restConnectionService.delete(
            clientComissions,
            "/v1/api/manager/program/",
            [user_name: userName])
    if (postStatus?.statusCode != 200) logger.error("ERROR deleting manager: $userName)")
    postStatus?.statusCode == 200 ? true : false
  }
}
