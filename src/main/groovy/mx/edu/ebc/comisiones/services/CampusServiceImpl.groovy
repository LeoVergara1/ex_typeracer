package mx.edu.ebc.comisiones.services

import groovy.util.logging.Log4j
import groovy.transform.Memoized
import mx.edu.ebc.comisiones.pojos.Campus
import mx.edu.ebc.comisiones.pojos.UserCampus
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
  List<UserCampus> getAllCampusesforUser(String campusCode, String username){
    List<UserCampus> campuses = []
    List<Campus> listCampuses = list()
    List<JSONObject> jsonObject = restConnectionService.get(clientComissions, "/v1/api/users/${username}")
    jsonObject?.each{ campus ->
      String description = listCampuses?.findAll{ code -> code.code == campus.campusCode }.description.join()
      campuses << UserCampus.fromJsonObject(campus, description)
    }
    campuses
  }
}
