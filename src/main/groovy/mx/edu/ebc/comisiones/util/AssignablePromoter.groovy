package mx.edu.ebc.comisiones.pojos

import groovy.transform.ToString

@ToString
class AssignablePromoter {
  String lastName
  String firstName
  Long pidm
  String enrollment
  String middleName
  String userName
  String campus
  String campusCode
  Long managerPidm
  String managerUserName

  String getName(){
    "$firstName ${middleName ? "$middleName " : ""}${lastName.replace('*',' ')}"
  }

  String setNameFormat(String string){
    def strings = string.trim().replace('*', ' ').split()
    strings.collect{ charChain ->
      charChain.with{
        toLowerCase().replaceFirst(it[0].toLowerCase(), it[0].toUpperCase())
      }
    }.join(' ')
  }
}
