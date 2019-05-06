
package mx.edu.ebc.comisiones.controllers

import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.ModelAndView
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import mx.edu.ebc.comisiones.services.AdministrationService

@Controller
class AdministrarionController {

	@Autowired
	AdministrationService administrationService

  @RequestMapping("/")
  @ResponseBody
  public ModelAndView home() {
		ModelAndView model = new ModelAndView("administration/home");
		administrationService.anyfunction()
		model.addObject("title", "Baeldung");
		model.addObject("any", "Hola mundo");
		return model
  }
}