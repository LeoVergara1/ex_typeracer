package mx.edu.ebc.comisiones.controllers

import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.ModelAndView
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestBody
import mx.edu.ebc.comisiones.services.AdministrationService
import mx.edu.ebc.comisiones.services.AuthorizationService
import mx.edu.ebc.comisiones.services.PromoterService
import mx.edu.ebc.comisiones.services.SicossService
import mx.edu.ebc.comisiones.comision.domain.Campaign;
import mx.edu.ebc.comisiones.comision.domain.Sicoss;
import org.springframework.context.ApplicationContext
import mx.edu.ebc.comisiones.comision.repo.AdminDeComisionesRepository
import mx.edu.ebc.comisiones.comision.repo.CampaignRepository
import mx.edu.ebc.comisiones.seguridad.repo.CampusRepository
import mx.edu.ebc.comisiones.comision.repo.SicossRepository
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
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat

@RequestMapping("/sicoss")
@Controller
class SicossController {

	@Value('#{${campus}}')
	Map<String, String> campus
	@Autowired
	AdministrationService administrationService
	@Autowired
	SicossService sicossService
	@Autowired
	CampaignRepository campaignRepository
	@Autowired
	SicossRepository sicossRepository

	@GetMapping("/")
	@ResponseBody
	ModelAndView sicoss(){
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "sicoss");
		model
	}

	@GetMapping("/process")
	@ResponseBody
	ModelAndView process(){
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "processSicoss");
		model
	}

	@GetMapping("/queryProcess")
	@ResponseBody
	ModelAndView queryProcess(){
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "queryProcess");
		model
	}

	@GetMapping("/campings")
	@ResponseBody
	List<Campaign> campings(){
		campaignRepository.findAllByStatus("ACTIVE")
	}

	@GetMapping("/employee/{clave}")
	@ResponseBody
	List<Sicoss> employee(@PathVariable String clave, @RequestParam String initDate, @RequestParam String endDate){
    Date initDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(initDate)
    Date finDateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(endDate)
		List<Sicoss> listSicoss = sicossRepository.findAllByClaveEmployeeAndDateMovenmentBetween(clave, initDateFrom, finDateFrom) 
	}

	@PostMapping("/porcossSicoss")
	@ResponseBody
	Map processSicoss(HttpServletRequest request, @RequestBody Campaign camping){
		String username = request.getUserPrincipal().getUserDetails().username
		camping.statusSicoss = "SEND"
		camping.usernameSicoss = username
		camping.lastUpdated = new Date()
		def mapSicoss = sicossService.getCommissionNormalAndCrecients(camping)
		List<Sicoss> listSicoss = sicossService.covertCommissiosNormaToSicoss(mapSicoss)
		sicossService.saveSicossList(listSicoss)
		campaignRepository.save(camping)
		[response: 200, listSicoss: listSicoss]
	}

}