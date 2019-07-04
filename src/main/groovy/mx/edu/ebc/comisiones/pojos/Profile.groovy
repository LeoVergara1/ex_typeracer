package mx.edu.ebc.comisiones.pojos

import wslite.json.JSONObject
import static java.util.Calendar.*
import java.time.LocalDate

class Profile {
  Long id
  String description
  String enrollment
  String fullName
  LocalDate dateCreated

    static Profile fromJsonObject(JSONObject jsonObject){
      new Profile(
                   id:jsonObject?.id,
                   description:jsonObject?.description,
                   enrollment:jsonObject?.enrollment,
                   fullName:jsonObject?.fullName,
                   dateCreated: jsonObject?.dateCreated ?  LocalDate.parse(jsonObject.dateCreated) : LocalDate.now()
                        )
  }
}
