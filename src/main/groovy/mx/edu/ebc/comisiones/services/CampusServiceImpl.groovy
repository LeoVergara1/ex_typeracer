package mx.edu.ebc.comisiones.services

import groovy.util.logging.Log4j
import groovy.transform.Memoized
import mx.edu.ebc.comisiones.pojos.Campus
import mx.edu.ebc.comisiones.pojos.UserCampusCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wslite.json.JSONObject
import org.springframework.beans.factory.annotation.Value

@Service
class CampusServiceImpl implements CampusService {

  @Autowired
  RestConnectionService restConnectionService
  @Value('${url.apibannercomisiones}')
	String clientApiBannerComissions
  @Value('${url.apicomisiones}')
	String clientComissions
  @Autowired
  UserCampusService userCampusService

  @Override
  List<JSONObject> findAll() {
    restConnectionService.get(clientApiBannerComissions, "/v1/api/campuses", [:])
  }

  @Memoized
  List<Campus> list() {
    findAll().collect {
      Campus.fromJsonObject(it)
    }
  }

  @Override
  List<Campus> filteredListByPropertiesConf() {
    List<String> excludedCampus = properties.getProperty("excludedCampus").split(',')
    list().inject([]){ list, campus ->
      if (!excludedCampus.contains(campus.code))
        list << campus
      list
    }
  }

  @Override
  List<UserCampusCommand> getAllCampusesforUser(String campusCode, String username){
    List<Campus> listCampuses = list()
    def jsonObject = userCampusService.findByUserCampusPK_UserName(username)
    jsonObject?.collect{ campus ->
      String description = listCampuses?.findAll{ code -> code.code == campus.userCampusPK.campusCode }.description.join()
      UserCampusCommand userCampus = UserCampusCommand.fromJsonObject(campus, description)
    }
  }
}
