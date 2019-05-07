package mx.edu.ebc.comisiones.comision.config

import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.context.annotation.Configuration
import mx.edu.ebc.comisiones.seguridad.repo.CampusRepository

@Configuration
//@EnableJpaRepositories(basePackageClasses = CampusRepository.class,
//		entityManagerFactoryRef = "secondEntityManagerFactory")
public class SeguridadConfig {

}