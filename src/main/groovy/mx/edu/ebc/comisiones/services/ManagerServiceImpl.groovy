package mx.edu.ebc.comisiones.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Value

@Service
class ManagerServiceImpl implements ManagerService {

  @Autowired
  RestConnectionService restConnectionService
	@Value('${url.apicomisiones}')
	String clientComissions

  @Override
  boolean createManager(String userName, Long pidm, String recrCode) {
    log.info "Saving new manager: $userName"
    def postStatus = restConnectionService.post(
            clientComissions,
            "/v1/api/manager/program/",
            [user_name: userName,
             pidm: pidm,
             recr_code: recrCode])
    postStatus?: log.error("ERROR creating manager: $userName)")
    postStatus ? true : false
  }

  @Override
  boolean deleteManager(String userName) {
    log.info "Deleting manager: $userName"
    def postStatus = restConnectionService.delete(
            clientComissions,
            "/v1/api/manager/program/",
            [user_name: userName])
    if (postStatus?.statusCode != 200) log.error("ERROR deleting manager: $userName)")
    postStatus?.statusCode == 200 ? true : false
  }
}
