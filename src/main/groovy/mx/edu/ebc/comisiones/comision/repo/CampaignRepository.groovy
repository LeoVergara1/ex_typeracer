package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
	List<Campaign> findAll()
	List<Campaign> findAllByYear(String year)
	Campaign findByClave(String clave)
	Campaign findById(Integer id)
}