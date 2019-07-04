package mx.edu.ebc.comisiones.pojos

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


  static UserCampusCommand fromJsonObject(def jsonObject, String description) {
    new UserCampusCommand(
                    pidm:jsonObject?.userCampusPK.pidm,
                    campusCode:jsonObject?.userCampusPK.campusCode,
                    userName:jsonObject?.userCampusPK.userName,
                    dateCreated:jsonObject?.dateCreated,
                    lastUpdated:jsonObject?.lastUpdated,
                    description:description
    )
  }

}