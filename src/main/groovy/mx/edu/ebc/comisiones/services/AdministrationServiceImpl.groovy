package mx.edu.ebc.comisiones.services

import org.springframework.stereotype.Service

@Service
public class AdministrationServiceImpl implements AdministrationService {

	@Override
	void anyfunction(){
		println "Any hello"
		print "*"*100
	}
}