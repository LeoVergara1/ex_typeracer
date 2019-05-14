package mx.edu.ebc.comisiones.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import mx.edu.ebc.comisiones.comision.repo.AdminDeComisionesRepository
import mx.edu.ebc.comisiones.comision.repo.PromoterAssociationRepository
import mx.edu.ebc.comisiones.comision.repo.PersonRepository
import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones;
import mx.edu.ebc.comisiones.comision.domain.PromoterAssociation;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import wslite.json.JSONObject


@Service
public class AdministrationServiceImpl implements AdministrationService {

	Logger logger = LoggerFactory.getLogger(RestConnectionServiceImpl.class)

	@Autowired
	AdminDeComisionesRepository adminDeComisionesRepository
	@Autowired
	PromoterAssociationRepository promoterAssociationRepository
	@Autowired
	PersonRepository personRepository
	@Autowired
	RestConnectionService restConnectionService
	@Value('${url.apibannercomisiones}')
	String clientApiBannerComissions

	@Override
	List<AdminDeComisiones> findAllComission(){
		adminDeComisionesRepository.findAll()
	}

	@Override
	List<PromoterAssociation> findAllPromoters(){
		promoterAssociationRepository.findAll()
	}

	@Transactional
	@Override
	AdminDeComisiones updateCuotaFijaToComission(String id, String cuotaFija){
		AdminDeComisiones comissionToUpdate = adminDeComisionesRepository.findById(id.toInteger())
		comissionToUpdate.cuotaFija = cuotaFija.toInteger()
		adminDeComisionesRepository.save(comissionToUpdate)
	}

	@Override
	List<AdminDeComisiones> updateComissions(String comissionEjecutiva, String comissionCordinacion){
		List<AdminDeComisiones> adminDeComisionesList = adminDeComisionesRepository.findAll()
		adminDeComisionesList.each{ admin ->
			admin.comisionCoordinacion = comissionCordinacion.toInteger()
			admin.comisionEjecutivo = comissionEjecutiva.toInteger()
			adminDeComisionesRepository.save(admin)
		}
	}

	@Override
	Map findPerson(String user){
		def person = restConnectionService.get(clientApiBannerComissions, "/v1/api/person/${user}")
	}
}