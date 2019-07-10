package mx.edu.ebc.comisiones.controllers

import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.ModelAndView
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import mx.edu.ebc.comisiones.services.AdministrationService
import mx.edu.ebc.comisiones.services.AuthorizationService
import mx.edu.ebc.comisiones.services.PromoterService
import org.springframework.context.ApplicationContext
import mx.edu.ebc.comisiones.comision.repo.AdminDeComisionesRepository
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
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat

@RequestMapping("/authorization")
@Controller
class AuthorizationController {

	@Value('#{${campus}}')
	Map<String, String> campus
	@Autowired
	AdministrationService administrationService
	@Autowired
	AuthorizationService authorizationService
  @Autowired
  TrimesterRepository trimesterRepository

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
		Map calculationWithComissions = authorizationService.getCalculation(data.campus, data.initDate, data.finDate)
    Trimester trimester = trimesterRepository.findByInitDateGreaterThanAndEndDateLessThan(data.initDate, data.finDate)
    calculationWithComissions
  }

	@PostMapping("/sendAuthorization")
  @ResponseBody
  Map sendAuthorization(HttpServletRequest request, @RequestBody Map data) {
		String username = request.getUserPrincipal().getUserDetails().username
		authorizationService.saveListAuthorization(data.listAuthorization, username)
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
		def list = authorizationService.getCommissionsByStatus(data)
		def groups = authorizationService.structureGrups(list.groupBy({ it.idPromotor }))
		[response:200, commissions: authorizationService.getCommissionsByStatus(data), groups: groups]
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

  @PostMapping("/denegate")
  @ResponseBody
  Map queryAuthorizationSearchCommissions(@RequestBody AuthorizationComission comision) {
		[response:200]
  }


}
