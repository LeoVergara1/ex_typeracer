package mx.edu.ebc.comisiones.commands

import groovy.transform.ToString
import wslite.json.JSONObject

@ToString
class CampusCommand {
  String code
  String description

  static CampusCommand fromJsonObject(JSONObject jsonObject) {
    new CampusCommand(
            code: jsonObject.isNull("code") ? null : jsonObject.code,
            description: jsonObject.isNull("description") ? null : jsonObject.description
    )
  }
}