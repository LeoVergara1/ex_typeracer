
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
		ModelAndView model = new ModelAndView("administration/association");
		model.addObject("listAssociation", administrationService.findAllPromoters())
		return model
  }
}