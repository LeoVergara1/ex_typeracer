package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.commands.CampusCommand
import wslite.json.JSONObject

interface CampusService {
  List<JSONObject> findAll()
  List<CampusCommand> list()
  List<CampusCommand> filteredListByPropertiesConf()
}