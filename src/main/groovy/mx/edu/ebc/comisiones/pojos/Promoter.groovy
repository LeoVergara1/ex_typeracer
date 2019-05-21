package mx.edu.ebc.comisiones.pojos

import groovy.transform.ToString
import wslite.json.JSONObject

@ToString
class Promoter {

  Long pidm
  String userName
  Long managerPidm
  String managerUserName
  String recrCode
  String managerRecrCode
  JSONObject promoterJSONObject

  Promoter(){}

  Promoter(JSONObject promoterJSONObject){
    this.promoterJSONObject = promoterJSONObject
  }

  Promoter(Long pidm, String userName, Boolean check) {
    this.pidm = pidm
    this.userName = userName
    this.check = check
  }

  Promoter createPromoterFromJSONObject(){
    new Promoter(
            pidm: Long.valueOf(promoterJSONObject?.pidm?:-1L),
            userName: promoterJSONObject?.userName,
            managerPidm: Long.valueOf(promoterJSONObject?.managerPidm?:-1L),
            managerUserName: promoterJSONObject?.managerUserName,
            recrCode: "",
            managerRecrCode: ""
    )
  }

  static PromoterCode createPromoterBannerForRadmCodeFromJSONObject(JSONObject jsonObject){
    new PromoterCode(
            pidm: Long.valueOf(jsonObject?.pidm?:-1L),
            radmCode: jsonObject?.radmCode,
            activityDate: jsonObject?.activityDate ? new Date(jsonObject?.activityDate) : null
    )
  }

  static PromoterCode createPromoterBannerForRecrCodeFromJSONObject(JSONObject jsonObject){
    new PromoterCode(
            fullName: jsonObject?.fullName,
            recrCode: jsonObject?.code,
            activityDate: jsonObject?.activityDate ? new Date(jsonObject?.activityDate) : null
    )
  }

  @Override
  String toString(){
    "Promoter :: pidm: $pidm, userName: $userName, check: $check, JSONIbject?: ${promoterJSONObject?true:false}"
  }

}