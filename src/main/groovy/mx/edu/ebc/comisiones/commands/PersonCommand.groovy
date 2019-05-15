package mx.edu.ebc.comisiones.commands

import groovy.transform.ToString
import wslite.json.JSONObject

@ToString
class PersonCommand {
  String lastName
  String firstName
  Long pidm
  String enrollment
  String adminId
  String middleName
  String userName
  String email
  List<ProfileCommand> profiles = []
  List<UserCampusCommand> campuses = []

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

	static PersonCommand fromJsonObject(JSONObject jsonObject) {
    new PersonCommand(
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