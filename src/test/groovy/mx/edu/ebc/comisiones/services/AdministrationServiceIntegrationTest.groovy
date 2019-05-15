package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.comision.domain.AdminDeComisiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import org.junit.runner.RunWith
import org.junit.Test
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.context.annotation.Bean
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.TestPropertySource
import mx.edu.ebc.comisiones.comision.repo.*
import org.springframework.test.context.ContextConfiguration
import mx.edu.ebc.comisiones.pojos.*

@RunWith(SpringRunner.class)
@ContextConfiguration
@TestPropertySource("classpath:application-dev.properties")
public class AdministrationServiceIntegrationTest {

	  @TestConfiguration
    static class AdministrationServiceIntegrationTestContextConfiguration {
        @Bean
        public AdministrationService administrationService() {
            return new AdministrationServiceImpl();
        }
				@Bean
        public RestConnectionService restConnectionService() {
            return new RestConnectionServiceImpl();
        }

				@Bean
        public PersonService personService() {
            return new PersonServiceImpl();
        }
    }

	@Autowired
	AdministrationService administrationService
	@Autowired
	RestConnectionService restConnectionService
	@Autowired
	PersonService personService

	@MockBean
	AdminDeComisionesRepository adminDeComisionesRepository
	@MockBean
	PromoterAssociationRepository promoterAssociationRepositor
	@MockBean
	PersonRepository personRepository


	@Test
	public void searchUser() {
	    // given a username
			String username = "r.martinez026"
	    // when
			def person = administrationService.findPerson(username)
			println person.dump()
	    // then
			assertThat(person).isNotNull()
	}

	@Test
	public void setProfile() {
	    // given a person
			Person person = administrationService.findPerson("r.martinez026")
	    // when
			println person.dump()
	    // then
			assertThat(person).isNotNull()
	}
}