package mx.edu.ebc.comisiones.services

import groovy.util.logging.Log4j
import groovy.transform.Memoized
import mx.edu.ebc.comisiones.pojos.Campus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wslite.json.JSONObject

@Service
class CampusServiceImpl implements CampusService {

  @Autowired
  RestConnectionService restConnectionService
  @Autowired
  Properties properties

  @Override
  List<JSONObject> findAll() {
    restConnectionService.get(properties.getProperty("core.url.apibannercomisiones"), "/v1/api/campuses", [:])
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
}
