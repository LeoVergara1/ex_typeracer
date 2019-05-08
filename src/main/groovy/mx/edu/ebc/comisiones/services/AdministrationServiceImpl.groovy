package mx.edu.ebc.comisiones.services

import org.springframework.stereotype.Service
import mx.edu.ebc.comisiones.comision.repo.AdminDeComisionesRepository
import mx.edu.ebc.comisiones.comision.repo.PromoterAssociationRepository
import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones;
import mx.edu.ebc.comisiones.comision.domain.PromoterAssociation;
import org.springframework.beans.factory.annotation.Autowired

@Service
public class AdministrationServiceImpl implements AdministrationService {

	@Autowired
	AdminDeComisionesRepository adminDeComisionesRepository
	@Autowired
	PromoterAssociationRepository promoterAssociationRepository

	@Override
	List<AdminDeComisiones> findAllComission(){
		adminDeComisionesRepository.findAll()
	}

	@Override
	List<PromoterAssociation> findAllPromoters(){
		promoterAssociationRepository.findAll()
	}
}