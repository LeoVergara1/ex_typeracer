
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
import mx.edu.ebc.comisiones.services.CampaignService
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
import mx.edu.ebc.comisiones.comision.domain.Campaign

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
  @Value('${promoterRoleId}')
  String promoterRoleId
  @Value('${managerRoleID}')
  String managerRoleID
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
	@Autowired
	CampaignService campaignService


  @RequestMapping("/")
  @ResponseBody
  ModelAndView home() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "home");
		model.addObject("title", "Baeldung");
		model.addObject("any", "Hola mundo");
		return model
  }

  @RequestMapping("administration/show")
  @ResponseBody
  ModelAndView administrationShow(Principal principal) {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "show");
		model.addObject("comisionesList", administrationService.findAllComission());
		model.addObject("comissionEjecutiva", administrationService.findAllComission().first().comisionEjecutivo);
		model.addObject("comissionCordinacion", administrationService.findAllComission().first().comisionCoordinacion);
		model.addObject("message", "Hola mundo");
		return model
  }

  @RequestMapping("administration/register")
  @ResponseBody
  ModelAndView register() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "register");
		model.addObject("promoterRoleId", promoterRoleId);
		model.addObject("managerRoleID", managerRoleID);
		model.addObject("listAssociation", administrationService.findAllPromoters())
		return model
  }

  @RequestMapping("administration/association")
  @ResponseBody
  ModelAndView association() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "association");
		model.addObject("promoterRoleId", promoterRoleId);
		model.addObject("managerRoleID", managerRoleID);
		model.addObject("listAssociation", administrationService.findAllPromoters())
		return model
  }

  @RequestMapping("administration/association/{username}")
  @ResponseBody
  ModelAndView association(@PathVariable(value="username") String username) {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "registerAssociation");
		model.addObject("username", username);
		model.addObject("person", administrationService.getPersonWithValidations(username));
		return model
  }

	@GetMapping("administration/updateCuotaFija")
  @ResponseBody
  RedirectView updateCuotaFija(@RequestParam(name = "id") String id, @RequestParam(name = "cuotaFija") String cuotaFija) {
		administrationService.updateCuotaFijaToComission(id, cuotaFija)
		return new RedirectView("/administration/show");
  }

	@GetMapping("administration/updateComission")
  @ResponseBody
  RedirectView updateComission(@RequestParam(name = "comissionEjecutiva") String comissionEjecutiva, @RequestParam(name = "comissionCordinacion") String comissionCordinacion) {
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
  Map getInfoAssociation() {
		Map data = [
			campus: campus,
			listAssociation: administrationService.findAllPromoters()
		]
		return data
  }

	//@RequestMapping(path = "administration/search/association", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PostMapping("administration/search/association")
  @ResponseBody
  Map getInfoAssociationCoordinater(@RequestBody Map searchData) {
		Map infoPerson = administrationService.getPersonWithValidations(searchData.user)
    return infoPerson
  }

	@PostMapping("administration/save/association")
  @ResponseBody
  Map saveAssociation(@RequestBody Map associationData) {
		Map infoPerson = administrationService.saveAssociation(associationData.listPromoterToUser, associationData.person)
    return infoPerson
  }


	@PostMapping("administration/delete/roleAndCampus")
	@ResponseBody
  Map deleteCampusAndRolToPerson(@RequestBody Map data){
    logger.info "Eliminar Campus y rol de una persona"
    def result = administrationService.deleteCampusAndRolToPerson(data.person.userName, data.person.campuses[0].campusCode, data.person.profiles[0].id.toString())
  }

	@PostMapping("administration/saveRolToPerson")
	@ResponseBody
  Map saveRolToPerson(@RequestBody Map user){
		def result = administrationService.saveRolAndCampus(user.person.userName, user.campus, user.roleCode.toString(), user.rcreCode)
	 [result: result]
  }

  @RequestMapping("administration/company")
  @ResponseBody
  ModelAndView company() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "company");
		return model
  }

	@PostMapping("administration/save/trimester")
	@ResponseBody
  Map addTrimester(@RequestBody Map dataToSearch, HttpSession session){
		[result: administrationService.save_trimester(dataToSearch)]
  }

	@PostMapping("administration/delete/trimester")
	@ResponseBody
  Map deleteTrimester(@RequestBody Map dataToSearch){
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
  ModelAndView goal() {
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
  ModelAndView campaign() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("content", "campaign");
		return model
  }

	@GetMapping("administration/campaign/all/{year}")
  @ResponseBody
  Map gatAllCampaing(@PathVariable String year) {
		def campaigns = campaignService.getALlCampaningsByYearAndCreateIfNotExist(year)
		[campaigns: campaigns]
  }

	@PostMapping("administration/update/campaign")
	@ResponseBody
  Map updateCampaign(@RequestBody Campaign campaign){
		[result: campaignRepository.save(campaign)]
  }

}