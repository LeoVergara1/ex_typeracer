package mx.edu.ebc.comisiones.pojos

import groovy.transform.ToString
import wslite.json.JSONObject

@ToString
class Campus {
  String code
  String description

  static Campus fromJsonObject(JSONObject jsonObject) {
    new Campus(
            code: jsonObject.isNull("code") ? null : jsonObject.code,
            description: jsonObject.isNull("description") ? null : jsonObject.description
    )
  }
}