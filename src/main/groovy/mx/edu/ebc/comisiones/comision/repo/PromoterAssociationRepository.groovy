package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.PromoterAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
public interface PromoterAssociationRepository extends JpaRepository<PromoterAssociation,Integer> {
	List<PromoterAssociation> findAll()
}