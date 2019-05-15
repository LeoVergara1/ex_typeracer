package mx.edu.ebc.comisiones.pojos

import wslite.json.JSONObject

class RoleCommand {
  Long id
  String description


  static RoleCommand convertJSONtoRoles(JSONObject jsonObject){
    new RoleCommand(
                   id:jsonObject?.id,
                   description:jsonObject?.description
                   )
  }
}