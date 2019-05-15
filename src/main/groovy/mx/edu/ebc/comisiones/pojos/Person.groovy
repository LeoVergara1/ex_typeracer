package mx.edu.ebc.comisiones.pojos

import groovy.transform.ToString
import wslite.json.JSONObject

@ToString
class Person {
  String lastName
  String firstName
  Long pidm
  String enrollment
  String adminId
  String middleName
  String userName
  String email
  List<Profile> profiles = []
  List<UserCampus> campuses = []

  String getBannerName(){
    "${firstName ?: ''} ${middleName ? "$middleName " : ""}${lastName ?: ""}"
  }

  String getName(){
    setNameFormat("${firstName ?: ''} ${middleName ? "$middleName " : ""}${lastName ?: ''}")
  }

  private String setNameFormat(String string){
    def strings = string.trim().replace('*', ' ').split()
    strings.collect{ charChain ->
      charChain.with{
        toLowerCase().replaceFirst(it[0].toLowerCase(), it[0].toUpperCase())
      }
    }.join(' ')
  }

	static Person fromJsonObject(JSONObject jsonObject) {
    new Person(
            lastName:jsonObject?.lastName,
            firstName:jsonObject?.firstName,
            pidm:jsonObject?.pidm,
            enrollment:jsonObject?.enrollment,
            adminId: jsonObject?.adminId,
            middleName:jsonObject?.middleName ?: null,
            userName: jsonObject?.userName,
            email: jsonObject?.email
    )
  }
}