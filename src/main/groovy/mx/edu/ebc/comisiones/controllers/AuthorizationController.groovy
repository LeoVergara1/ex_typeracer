package mx.edu.ebc.comisiones.controllers

import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.ModelAndView
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import mx.edu.ebc.comisiones.services.AdministrationService
import mx.edu.ebc.comisiones.services.AuthorizationService
import mx.edu.ebc.comisiones.services.TrimesterService
import mx.edu.ebc.comisiones.services.CalculationService
import mx.edu.ebc.comisiones.services.PromoterService
import org.springframework.context.ApplicationContext
import mx.edu.ebc.comisiones.comision.repo.AdminDeComisionesRepository
import mx.edu.ebc.comisiones.comision.repo.AuthorizationRepository
import mx.edu.ebc.comisiones.comision.repo.AuthorizationCrescentRepository
import mx.edu.ebc.comisiones.seguridad.repo.CampusRepository
import mx.edu.ebc.comisiones.comision.repo.TrimesterRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.servlet.view.RedirectView
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMethod
import mx.edu.ebc.comisiones.util.Transform
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PathVariable
import mx.edu.ebc.comisiones.comision.domain.Promoter
import mx.edu.ebc.comisiones.comision.domain.Trimester
import mx.edu.ebc.comisiones.comision.domain.AuthorizationComission
import mx.edu.ebc.comisiones.comision.domain.AuthorizationCrescent
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat

@RequestMapping("/authorization")
@Controller
class AuthorizationController {

	Logger logger = LoggerFactory.getLogger(AuthorizationController.class)

	@Value('#{${campus}}')
	Map<String, String> campus
	@Autowired
	AdministrationService administrationService
	@Autowired
	AuthorizationService authorizationService
	@Autowired
	TrimesterService trimesterService
  @Autowired
  TrimesterRepository trimesterRepository
  @Autowired
  CalculationService calculationService
  @Autowired
  AuthorizationRepository authorizationRepository
  @Autowired
  AuthorizationCrescentRepository authorizationCrescentRepository

  @RequestMapping("/")
  @ResponseBody
  ModelAndView authorization() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "authorization");
		//def list = adminDeComisionesRepository.findAll()
		return model
  }

	@GetMapping("/campueses")
  @ResponseBody
  Map campueses(HttpServletRequest request) {
		String username = request.getUserPrincipal().getUserDetails().username
		return [campus: campus, person: administrationService.getPersonWithValidations(username)];
  }

	@PostMapping("/getCalculation")
  @ResponseBody
  Map getCalculation(@RequestBody Map data) {
    logger.info "Calculado comisiones corrientes"
		Map calculationWithComissions = authorizationService.getCalculation(data.campus, data.initDate, data.finDate)
    List<Trimester> trimester = trimesterService.findByInitDateGreaterThanAndEndDateLessThan(data.initDate, data.finDate)
    if(trimester.size() > 1){
      logger.error "La busqueda incluye más de un trimestre"
      return calculationWithComissions << [moreThanTwo: true, calculationCrecent: [], withoutTrimester: false]
    }
    else if(trimester.size() == 0){
      logger.error "La busqueda no tiene trimestres"
      return calculationWithComissions << [moreThanTwo: false, calculationCrecent: [], withoutTrimester: true]
    }
    logger.info "Calculado comisiones crecientes"
    def calculationCrecents = calculationService.getAuthorizationsCrescentcalculationByGoalsAndFilterAlreadyAuthorized(trimester.first(), data.campus)
    calculationWithComissions << [moreThanTwo: false, withoutTrimester: false, calculationCrecent: calculationCrecents, groupsCalculations: calculationCrecents.groupBy({ it.idPromotor }) ]
  }

	@PostMapping("/sendAuthorization")
  @ResponseBody
  Map sendAuthorization(HttpServletRequest request, @RequestBody Map data) {
		String username = request.getUserPrincipal().getUserDetails().username
		authorizationService.saveListAuthorization(data.listAuthorization, username)
		[response: 200]
  }

	@PostMapping("/sendAuthorizationCrecent")
  @ResponseBody
  Map sendAuthorizationCrecent(HttpServletRequest request, @RequestBody List<AuthorizationCrescent> authorizationCrescents) {
		String username = request.getUserPrincipal().getUserDetails().username
		authorizationService.saveListAuthorizationCrecent(authorizationCrescents, username)
		[response: 200]
  }

	@PostMapping("/sendAuthorizationMarketing")
  @ResponseBody
  Map sendAuthorizationMarketing(HttpServletRequest request, @RequestBody List<AuthorizationComission> authorizationComission) {
		String username = request.getUserPrincipal().getUserDetails().username
		authorizationService.saveListAuthorizationMarketing(authorizationComission, username)
		[response: 200]
  }

	@PostMapping("/sendAuthorizationCrecentMarketing")
  @ResponseBody
  Map sendAuthorizationCrecentMarketing(HttpServletRequest request, @RequestBody List<AuthorizationCrescent> authorizationCrescents) {
		String username = request.getUserPrincipal().getUserDetails().username
		authorizationService.saveListAuthorizationCrecentMarketing(authorizationCrescents, username)
		[response: 200]
  }

	@PostMapping("/denegateComissions")
  @ResponseBody
  Map denegateComissions(HttpServletRequest request, @RequestBody AuthorizationComission authorizationComission) {
		String username = request.getUserPrincipal().getUserDetails().username
    authorizationComission.dateCreated = new Date()
    authorizationComission.lastUpdated = new Date()
    authorizationComission.user = username
    authorizationComission.fechaAutorizado = new Date()
    authorizationRepository.save(authorizationComission)
		[response: 200]
  }

  @RequestMapping("/query")
  @ResponseBody
  ModelAndView queryAuthorization() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "queryAuthorization");
		return model
  }

  @PostMapping("/query/searchCommisions")
  @ResponseBody
  Map queryAuthorizationSearchCommissions(@RequestBody Map data) {
    if(data.typeComission == "Corrientes"){
		  def list = authorizationService.getCommissionsByStatus(data)
		  def groups = authorizationService.structureGrups(list.groupBy({ it.idPromotor }))
		  return [response:200, commissions: authorizationService.getCommissionsByStatus(data), groups: groups]
    }
    List<Trimester> trimester = trimesterService.findByInitDateGreaterThanAndEndDateLessThan(data.selectInit, data.selectFin)
    if(trimester.size() > 1){
      logger.error "La busqueda incluye más de un trimestre"
      return [response: 404, commissions: [], groups: []]
    }
    else if(trimester.size() == 0){
      logger.error "La busqueda incluye más de un trimestre"
      return [response: 404, commissions: [], groups: []]
    }
    data << [trimester: trimester.first()]
    List<AuthorizationCrescent> authorizationCrescents = authorizationService.getCommissionsCrecentByStatus(data)
    def groups = authorizationService.structureGrups(authorizationCrescents.groupBy({ it.idPromotor }))
    [response:200, commissions: authorizationCrescents, groups: groups]
  }

	@PostMapping("/sicoss")
  @ResponseBody
  Map sicoss(HttpServletRequest request, @RequestBody Map data) {
		String username = request.getUserPrincipal().getUserDetails().username
    Date initDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(data.selectInit)
    Date finDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(data.selectFin)
		def result = authorizationService.getAllAuthorizationsCommisionsWithStructureToReport("AUTORIZADO", initDateFrom, finDateFrom , data.campusSelected)
		return [response: result, groups: result.groupBy({ it.campus })]
  }

  @PostMapping("/denegateCrecent")
  @ResponseBody
  Map denegateCrecent(HttpServletRequest request, @RequestBody AuthorizationCrescent authorizationCrescent) {
		String username = request.getUserPrincipal().getUserDetails().username
    authorizationCrescent.dateCreated = new Date()
    authorizationCrescent.lastUpdated = new Date()
    authorizationCrescent.fechaAutorizado = new Date()
    authorizationCrescent.user = username
    authorizationCrescentRepository.save(authorizationCrescent)
		[response:200]
  }

	@GetMapping("/marketing")
  @ResponseBody
  ModelAndView marketing(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "marketing");
		//def list = adminDeComisionesRepository.findAll()
		return model
  }

  	@PostMapping("/getCalculationMarketing")
  @ResponseBody
  Map getCalculationMarketing(@RequestBody Map data) {
    logger.info "Calculado comisiones corrientes"
		Map calculationWithComissions = authorizationService.getCalculationMarketing(data.campus, data.initDate, data.finDate)
    List<Trimester> trimester = trimesterService.findByInitDateGreaterThanAndEndDateLessThan(data.initDate, data.finDate)
    if(trimester.size() > 1){
      logger.error "La busqueda incluye más de un trimestre"
      return calculationWithComissions << [moreThanTwo: true, calculationCrecent: [], withoutTrimester: false]
    }
    else if(trimester.size() == 0){
      logger.error "La busqueda no tiene trimestres"
      return calculationWithComissions << [moreThanTwo: false, calculationCrecent: [], withoutTrimester: true]
    }
    logger.info "Calculado comisiones crecientes"
    def calculationCrecents = calculationService.getAuthorizationsCrescentcalculationByGoalsAndFilterAlreadyAuthorizedToMarketing(trimester.first(), data.campus)
    calculationWithComissions << [moreThanTwo: false, withoutTrimester: false, calculationCrecent: calculationCrecents, groupsCalculations: calculationCrecents.groupBy({ it.idPromotor }) ]
  }

}
