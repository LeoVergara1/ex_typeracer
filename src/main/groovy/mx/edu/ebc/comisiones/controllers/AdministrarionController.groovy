
package mx.edu.ebc.comisiones.controllers

import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.ModelAndView
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import mx.edu.ebc.comisiones.services.AdministrationService
import org.springframework.context.ApplicationContext
import mx.edu.ebc.comisiones.comision.repo.AdminDeComisionesRepository
import mx.edu.ebc.comisiones.seguridad.repo.CampusRepository
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

@Controller
class AdministrarionController {

	Logger logger = LoggerFactory.getLogger(AdministrarionController.class)

	@Autowired
	AdministrationService administrationService
	@Autowired
	ApplicationContext applicationContext
	@Autowired
	CampusRepository campusRepository
	@Autowired
	AdminDeComisionesRepository adminDeComisionesRepository
	@Value('#{${campus}}')
	Map<String, String> campus

  @RequestMapping("/")
  @ResponseBody
  public ModelAndView home() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "home");
		//def list = adminDeComisionesRepository.findAll()
		model.addObject("title", "Baeldung");
		model.addObject("any", "Hola mundo");
		return model
  }

  @RequestMapping("administration/show")
  @ResponseBody
  public ModelAndView administrationShow() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "show");
		model.addObject("comisionesList", administrationService.findAllComission());
		model.addObject("comissionEjecutiva", administrationService.findAllComission().first().comisionEjecutivo);
		model.addObject("comissionCordinacion", administrationService.findAllComission().first().comisionCoordinacion);
		model.addObject("message", "Hola mundo");
		return model
  }

  @RequestMapping("administration/association")
  @ResponseBody
  public ModelAndView association() {
		println campus
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "association");
		model.addObject("listAssociation", administrationService.findAllPromoters())
		return model
  }

	@GetMapping("administration/updateCuotaFija")
  @ResponseBody
  public RedirectView updateCuotaFija(@RequestParam(name = "id") String id, @RequestParam(name = "cuotaFija") String cuotaFija) {
		administrationService.updateCuotaFijaToComission(id, cuotaFija)
		return new RedirectView("/administration/show");
  }

	@GetMapping("administration/updateComission")
  @ResponseBody
  public RedirectView updateComission(@RequestParam(name = "comissionEjecutiva") String comissionEjecutiva, @RequestParam(name = "comissionCordinacion") String comissionCordinacion) {
		administrationService.updateComissions(comissionEjecutiva, comissionCordinacion)
		return new RedirectView("/administration/show");
  }


  @RequestMapping("administration/data/association")
  @ResponseBody
  public Map getInfoAssociation() {
		Map data = [
			campus: campus,
			listAssociation: administrationService.findAllPromoters()
		]
		return data
  }

	//@RequestMapping(path = "administration/search/association", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PostMapping("administration/search/association")
  @ResponseBody
  public Map getInfoAssociationCoordinater(@RequestBody Map searchData) {
		Map infoPerson = administrationService.getPersonWithValidations(searchData.user)
    return infoPerson
  }

	@PostMapping("administration/delete/roleAndCampus")
	@ResponseBody
  Map deleteCampusAndRolToPerson(@RequestBody Map data){
    logger.info "Eliminar Campus y rol de una persona"
    def result = administrationService.deleteCampusAndRolToPerson(data.person.userName, data.person.campuses[0].campusCode, data.person.profiles[0].id.toString())
   // log.info result.dump()
  }

	@PostMapping("administration/saveRolToPerson")
	@ResponseBody
  Map saveRolToPerson(@RequestBody Map user){
		println user.dump()
		println "Despues"
		println user.person.userName
		println user.campus
		def result = administrationService.saveRolAndCampus(user.person.userName, user.campus, user.roleCode, user.rcreCode)
    //def result = personService.deleteCampusAndRolToPerson(username, codeCampus, roleCode)
   // log.info result.dump()
	 [result: result]
  }

}