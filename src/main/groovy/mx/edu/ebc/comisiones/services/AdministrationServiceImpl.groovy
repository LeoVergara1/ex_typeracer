package mx.edu.ebc.comisiones.services

import org.springframework.stereotype.Service
import mx.edu.ebc.comisiones.comision.repo.AdminDeComisionesRepository
import mx.edu.ebc.comisiones.comision.repo.PromoterAssociationRepository
import mx.edu.ebc.comisiones.comision.repo.PersonRepository
import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones;
import mx.edu.ebc.comisiones.comision.domain.PromoterAssociation;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Service
public class AdministrationServiceImpl implements AdministrationService {

	@Autowired
	AdminDeComisionesRepository adminDeComisionesRepository
	@Autowired
	PromoterAssociationRepository promoterAssociationRepository
	@Autowired
	PersonRepository personRepository

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
	Map findPerson(String promoter, String coordinater){
		println "*"*100
		def personas = personRepository.findByIdBanner("M00000087")
		println personas.dump()
		[:]
	}
}