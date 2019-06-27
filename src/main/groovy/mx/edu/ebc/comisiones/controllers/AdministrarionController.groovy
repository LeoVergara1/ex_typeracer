
package mx.edu.ebc.comisiones.controllers

import org.springframework.stereotype.Controller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.ModelAndView
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import mx.edu.ebc.comisiones.services.AdministrationService
import mx.edu.ebc.comisiones.services.PromoterService
import mx.edu.ebc.comisiones.services.TrimesterService
import org.springframework.context.ApplicationContext
import mx.edu.ebc.comisiones.comision.repo.AdminDeComisionesRepository
import mx.edu.ebc.comisiones.comision.repo.CampaignRepository
import mx.edu.ebc.comisiones.comision.repo.TrimesterRepository
import mx.edu.ebc.comisiones.seguridad.repo.CampusRepository
import mx.edu.ebc.comisiones.comision.repo.GoalRepository
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
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.http.HttpSession
import org.springframework.security.core.userdetails.UserDetailsService;
import java.security.Principal
import org.springframework.security.cas.authentication.CasAuthenticationToken
import mx.edu.ebc.cas.util.pojo.EbcUser
import mx.edu.ebc.cas.util.pojo.UserProfile
import mx.edu.ebc.comisiones.comision.domain.Goal
import mx.edu.ebc.comisiones.comision.domain.Trimester

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
	@Autowired
	PromoterService promoterService
	@Value('#{${campus}}')
	Map<String, String> campus
	@Autowired
	UserDetailsService userDetailsService
	@Autowired
	TrimesterRepository trimesterRepository
	@Autowired
	TrimesterService trimesterService
	@Autowired
	GoalRepository goalRepository
	@Autowired
	CampaignRepository campaignRepository


  @RequestMapping("/")
  @ResponseBody
  public ModelAndView home() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "home");
		model.addObject("title", "Baeldung");
		model.addObject("any", "Hola mundo");
		return model
  }

  @RequestMapping("administration/show")
  @ResponseBody
  public ModelAndView administrationShow(Principal principal) {
		ModelAndView model = new ModelAndView("index");
		//def usuarioCas = ((CasAuthenticationToken) principal).getUserDetails()
		//EbcUser usuarioCas = (EbcUser) ((CasAuthenticationToken) principal).getUserDetails()
		//println session.dump()
		//println session.properties.each { k, v -> println "$k == ${v?.dump()}" }
		//println "*"*100
		//println session.session.dump()
		//println "+"*100
		//println session.session.properties.each { k, v -> println "$k == ${v?.dump()}" }
		//def user = 	userDetailsService.loadUserByUsername(session.session.username)
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

  @RequestMapping("administration/association/{username}")
  @ResponseBody
  public ModelAndView association(@PathVariable(value="username") String username) {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "registerAssociation");
		model.addObject("username", username);
		model.addObject("person", administrationService.getPersonWithValidations(username));
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

	@GetMapping("administration/coordinators")
  @ResponseBody
  List<Map> getCoordinator() {
		promoterService.getCoordinates()
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

	@PostMapping("administration/save/association")
  @ResponseBody
  public Map saveAssociation(@RequestBody Map associationData) {
		Map infoPerson = administrationService.saveAssociation(associationData.listPromoterToUser, associationData.person)
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
		def result = administrationService.saveRolAndCampus(user.person.userName, user.campus, user.roleCode.toString(), user.rcreCode)
    //def result = personService.deleteCampusAndRolToPerson(username, codeCampus, roleCode)
   // log.info result.dump()
	 [result: result]
  }

  @RequestMapping("administration/company")
  @ResponseBody
  public ModelAndView company() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "company");
		return model
  }

	@PostMapping("administration/save/trimester")
	@ResponseBody
  Map addTrimester(@RequestBody Map dataToSearch){
		println dataToSearch
		[result: administrationService.save_trimester(dataToSearch)]
  }

	@PostMapping("administration/delete/trimester")
	@ResponseBody
  Map deleteTrimester(@RequestBody Map dataToSearch){
		println dataToSearch
		[result: trimesterRepository.deleteById(dataToSearch.id)]
  }

	@GetMapping("administration/search/trimester/{name}/{clave}")
  @ResponseBody
  Map searhTrimester(@PathVariable String name, @PathVariable String clave) {
		def trimester = trimesterRepository.findByClave(clave)
		[trimester: trimester]
  }

	@GetMapping("administration/trimester/all")
  @ResponseBody
  Map searhTrimesterAll() {
		def trimesters = trimesterRepository.findAll().collect(){ c -> [trimester: c, editable: true]}
		[trimesters: trimesters]
  }

	@GetMapping("administration/trimester/year/{year}")
  @ResponseBody
  Map gatAllTrimester(@PathVariable String year) {
		def trimester = trimesterRepository.findAllByYear(year)
		[trimester: trimester]
  }

	@GetMapping("administration/trimester/create/goals/{id}")
  @ResponseBody
  Map gatAllTrimester(@PathVariable Integer id) {
		def goals = trimesterService.createAllGolsToCampaing(id)
		[goals: goals]
  }

  @RequestMapping("administration/goals")
  @ResponseBody
  public ModelAndView goal() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "goal");
		return model
  }

	@PostMapping("administration/save/goal")
	@ResponseBody
  Map saveGoal(@RequestBody Goal goal){
		[result: goalRepository.save(goal)]
  }

	@PostMapping("administration/update/trimester")
	@ResponseBody
  Map updateTrimester(@RequestBody Trimester trimester){
		[result: trimesterRepository.save(trimester)]
  }

  @RequestMapping("administration/campaign")
  @ResponseBody
  public ModelAndView campaign() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "campaign");
		return model
  }

	@GetMapping("administration/campaign/all/{year}")
  @ResponseBody
  Map gatAllCampaing(@PathVariable String year) {
		def campaigns = campaignRepository.findAllByYear(year)
		[campaigns: campaigns]
  }

}