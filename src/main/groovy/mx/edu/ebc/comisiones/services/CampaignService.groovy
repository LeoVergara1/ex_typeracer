package mx.edu.ebc.comisiones.services
import mx.edu.ebc.comisiones.comision.domain.Goal


interface CampaignService {
	List<Goal> createAllGolsToCampaing(Integer id)
}