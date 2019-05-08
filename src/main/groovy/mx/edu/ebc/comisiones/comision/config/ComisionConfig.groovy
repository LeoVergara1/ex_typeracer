package mx.edu.ebc.comisiones.comision.config

import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.context.annotation.Configuration

@Configuration
@EnableJpaRepositories(basePackages = ["mx.edu.ebc.comisiones.comision.repo"],
		entityManagerFactoryRef = "firstEntityManagerFactory")
public class ComisionConfig {

}