package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.pojos.Campus
import mx.edu.ebc.comisiones.pojos.UserCampus
import wslite.json.JSONObject

interface CampusService {
  List<JSONObject> findAll()
  List<Campus> list()
  List<Campus> filteredListByPropertiesConf()
  List<UserCampus> getAllCampusesforUser(String campusCode, String username)
}