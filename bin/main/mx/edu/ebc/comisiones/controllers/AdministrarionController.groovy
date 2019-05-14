
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

@Controller
class AdministrarionController {

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
		ModelAndView model = new ModelAndView("administration/home");
		//def list = adminDeComisionesRepository.findAll()
		model.addObject("title", "Baeldung");
		model.addObject("any", "Hola mundo");
		return model
  }

  @RequestMapping("administration/show")
  @ResponseBody
  public ModelAndView administrationShow() {
		ModelAndView model = new ModelAndView("administration/show");
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
		ModelAndView model = new ModelAndView("administration/association");
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
		Map person = Transform.mapFromBodyJson(administrationService.findPerson(searchData.user))
    return person
  }
}