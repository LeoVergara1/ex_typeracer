package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.comision.domain.Campaign

interface CampaignService {
	List<Campaign> getALlCampaningsByYearAndCreateIfNotExist(String year)
	Campaign checkActiveBeforeSave(Campaign campaing)
}