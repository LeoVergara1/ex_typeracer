package mx.edu.ebc.comisiones.pojos

import groovy.transform.ToString
import mx.edu.ebc.api.pojo.*
import wslite.json.JSONObject

@ToString
class UserCampus {
    Long pidm
    String campusCode
    String userName
    Date dateCreated
    Date lastUpdated
    String description


  static UserCampus fromJsonObject(JSONObject jsonObject, String description) {
    new UserCampus(
                    pidm:jsonObject?.pidm,
                    campusCode:jsonObject?.campusCode,
                    userName:jsonObject?.userName,
                    dateCreated:jsonObject?.dateCreated ? new Date(jsonObject?.dateCreated) : new Date(),
                    lastUpdated:jsonObject?.lastUpdated ? new Date(jsonObject?.lastUpdated) : new Date(),
                    description:description
    )
  }

}