package mx.edu.ebc.comisiones.pojos

class PromoterCode {

  Long pidm
  String fullName
  String radmCode
  String recrCode
  Date activityDate

  @Override
  String toString(){
    "pojo.PromoterCode :: " +
            "pidm: $pidm, " +
            "fullName: $fullName, " +
            "radmCode: $radmCode, " +
            "recrCode: $recrCode, " +
            "activityDate: $activityDate"
  }

}