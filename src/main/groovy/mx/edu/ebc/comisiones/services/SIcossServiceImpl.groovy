
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
import mx.edu.ebc.comisiones.comision.repo.SicossRepository
import java.time.LocalDate

@Service
class SicossServiceImpl implements SicossService {

  @Autowired
  AuthorizationRepository authorizationRepository
  @Autowired
  AuthorizationCrescentRepository authorizationCrescentRepository
  @Autowired
  SicossRepository sicossRepository

  Map getCommissionNormalAndCrecients(Campaign campaign){
    [
      commissionsNormal: authorizationRepository.findAllByStatusMarketingAndFechaAutorizadoBetween(true, campaign.initDate, campaign.endDate),
      commissionsCrecent: authorizationCrescentRepository.findAllByStatusMarketingAndFechaAutorizadoBetween(true, campaign.initDate, campaign.endDate)
    ]
  }

  List<Sicoss> covertCommissiosNormaToSicoss(Map mapWithListCommissions){
    def listComissionsToSicoss = mapWithListCommissions.commissionsCrecent + mapWithListCommissions.commissionsNormal
    listComissionsToSicoss = separetePromoterAndCoordinater(listComissionsToSicoss) 
    plusOneDayThanMoreClavesSames(listComissionsToSicoss)
  }

  List<Sicoss> separetePromoterAndCoordinater(def listAuthorizationComission){
    Date currentDate = new Date()
    List<Sicoss> listSicoss = []
    listAuthorizationComission.each(){ commission ->
      if(commission.idCoordinador){
        println commission.dump()
        listSicoss << new Sicoss(
          claveEmployee: commission.adCoordinador?.replace("AD", ""),
          dateMovenment: new Date("${currentDate.month+1}/01/${currentDate.year+1900}"),
          typePaysheet: "1",
          clavePaysheet: "0",
          concept: "422",
          reference1: "0",
          reference2: "0",
          dataPayhseet: "0",
          salary: "0",
          importe: commission.comisionCoordinador,
          payPeriod:  calculateQuincena(LocalDate.now()).toString(),
          typeSicoss: (commission.class == AuthorizationComission) ? "CORRIENTE" : "CRECIENTE",
          campus: commission.campus,
          dateAuthorized: commission.fechaAutorizado
          )
      }
      listSicoss << new Sicoss(
        claveEmployee: commission.adPromotor?.replace("AD", ""),
        typePaysheet: "1",
        clavePaysheet: "0",
        concept: "422",
        dateMovenment: new Date("${currentDate.month+1}/01/${currentDate.year+1900}"),
        reference1: "0",
        reference2: "0",
        dataPayhseet: "0",
        salary: "0",
        importe: commission.comision.toFloat(),
        payPeriod:  calculateQuincena(LocalDate.now()).toString(),
        typeSicoss: (commission.class == AuthorizationComission) ? "CORRIENTE" : "CRECIENTE",
        campus: commission.campus,
        dateAuthorized: commission.fechaAutorizado
        )
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

  def calculateQuincena(LocalDate currenDate){
    int month = currenDate.getMonthValue()
    int day = currenDate.getDayOfMonth()
    if(day <= 15)
      return (month * 2) - 1
    (month * 2)
  }

  def saveSicossList(List<Sicoss> sicossList){
    sicossRepository.deleteAll()
    sicossList.each(){ sicoss ->
      sicossRepository.save(sicoss)
    }
  }
}