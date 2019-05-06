
package mx.edu.ebc.comisiones.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.servlet.ModelAndView
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class AdministrarionController {

   @RequestMapping("/")
   @ResponseBody
   public ModelAndView home() {
		 	ModelAndView model = new ModelAndView("administration/home");
			model.addObject("title", "Baeldung");
			return model
   }
}