
package mx.edu.ebc.comisiones.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wslite.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import mx.edu.ebc.comisiones.comision.domain.Campaign
import mx.edu.ebc.comisiones.comision.domain.Sicoss
import mx.edu.ebc.comisiones.comision.domain.AuthorizationComission
import mx.edu.ebc.comisiones.comision.repo.AuthorizationCrescentRepository
import mx.edu.ebc.comisiones.comision.repo.AuthorizationRepository

@Service
class SicossServiceImpl implements SicossService {

  @Autowired
  AuthorizationRepository authorizationRepository
  @Autowired
  AuthorizationCrescentRepository authorizationCrescentRepository

  Map getCommissionNormalAndCrecients(Campaign campaign){
    [
      commissionsNormal: authorizationRepository.findAllByAutorizadoDirectorAndFechaAutorizadoBetween("AUTORIZADO", campaign.initDate, campaign.endDate),
      commissionsCrecent: authorizationCrescentRepository.findAllByAutorizadoDirectorAndFechaAutorizadoBetween("AUTORIZADO", campaign.initDate, campaign.endDate)
    ]
  }

  List<Sicoss> covertCommissiosNormaToSicoss(List<AuthorizationComission> listAuthorizationComission){

  }

  List<Sicoss> separetePromoterAndCoordinater(def listAuthorizationComission){
    Date currentDate = new Date()
    List<Sicoss> listSicoss = []
    listAuthorizationComission.each(){ commission ->
      if(commission.idCoordinador){
        listSicoss << new Sicoss(claveEmployee: commission.adCoordinador.replace("AD", ""), dateMovenment: new Date("${currentDate.month+1}/01/${currentDate.year+1900}"))
      }
      listSicoss << new Sicoss(claveEmployee: commission.adPromotor.replace("AD", ""),  dateMovenment: new Date("${currentDate.month+1}/01/${currentDate.year+1900}"))
    }
    listSicoss
  }

  List<Sicoss> plusOneDayThanMoreClavesSames(List<Sicoss> listSicoss){
    Date currentDate = new Date()
    List<Sicoss> listSicossWithoutDuplicates = []
    def groups = listSicoss.groupBy({ it.claveEmployee })
    groups.each(){ key, value ->
      if(value.size() > 1){
        value.eachWithIndex(){ element, index ->
          int day = 1 + index
          element.dateMovenment = new Date("${currentDate.month+1}/${day}/${currentDate.year+1900}") 
          listSicossWithoutDuplicates << element
        }
      }
      else{
        listSicossWithoutDuplicates << value[0]
      }
    }
    listSicoss
  }
}