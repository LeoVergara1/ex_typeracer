package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.pojos.PromoterCode

interface PromoterService {
  Boolean deletePromoter(String userName)
  Boolean createPromoter(String userName, Long pidm, String recrCode)
  List<PromoterCode> getRecrCodeCatalogue()
  Boolean isRecruiterCodeAlreadyInUse(String recrCode)
}