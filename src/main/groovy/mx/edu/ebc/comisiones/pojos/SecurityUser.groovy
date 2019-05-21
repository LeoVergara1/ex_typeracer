package mx.edu.ebc.comisiones.pojos

import wslite.json.JSONObject

class SecurityUser {
  Long id
  String name
  Date dateCreated
  String email
  String userName
  String administrator
  String enrollment
	JSONObject securityUserJSON

  @Override
  String toString(){
    "SecurityUser :: " +
            "id: $id, " +
            "name: $name, " +
            "dateCreated: $dateCreated, " +
            "email: $email, " +
            "username: $userName, " +
            "administrator: $administrator, " +
            "enrollment: $enrollment"
  }

	SecurityUser(){}

	SecurityUser(JSONObject securityUserJSON) {
    this.securityUserJSON = securityUserJSON
  }

	SecurityUser fromJSONObjectToSecurityUser(){
    new SecurityUser(
            id: securityUserJSON?.id,
            name: securityUserJSON?.name,
            dateCreated: new Date(securityUserJSON?.dateCreated),
            email: securityUserJSON?.email,
            userName: securityUserJSON?.username,
            administrator: securityUserJSON?.administrator,
            enrollment: securityUserJSON?.enrollment
    )
  }
}