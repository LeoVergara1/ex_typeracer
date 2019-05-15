package mx.edu.ebc.comisiones.commands

import groovy.transform.ToString
import mx.edu.ebc.api.pojo.*
import wslite.json.JSONObject

@ToString
class UserCampusCommand {
    Long pidm
    String campusCode
    String userName
    Date dateCreated
    Date lastUpdated
    String description


  static UserCampusCommand fromJsonObject(JSONObject jsonObject, String description) {
    new UserCampusCommand(
                    pidm:jsonObject?.pidm,
                    campusCode:jsonObject?.campusCode,
                    userName:jsonObject?.userName,
                    dateCreated:jsonObject?.dateCreated ? new Date(jsonObject?.dateCreated) : new Date(),
                    lastUpdated:jsonObject?.lastUpdated ? new Date(jsonObject?.lastUpdated) : new Date(),
                    description:description
    )
  }

}