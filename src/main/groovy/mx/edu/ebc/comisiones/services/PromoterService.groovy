package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.pojos.PromoterCode
import mx.edu.ebc.comisiones.comision.domain.Promoter
import mx.edu.ebc.comisiones.pojos.Person

interface PromoterService {
  Boolean deletePromoter(String userName)
  Map createPromoter(Person person, String campusCode, String recrCode)
  List<PromoterCode> getRecrCodeCatalogue()
  Boolean isRecruiterCodeAlreadyInUse(String recrCode)
  Boolean isAPromoterSavedWithRecrCode(String recrCode)
  List<Map> getCoordinates()
  def createListOfEnrollmentPromoterByCampus(String campusCode)
}