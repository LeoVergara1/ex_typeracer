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
        public CampusService campusService() {
            return new CampusServiceImpl();
        }

				@Bean
        public PersonService personService() {
            return new PersonServiceImpl();
        }
				@Bean
        public ManagerService managerService() {
            return new ManagerServiceImpl();
        }
				@Bean
        public UserCampusService userCampusService() {
            return new UserCampusServiceImpl();
        }

    }

	@Autowired
	AdministrationService administrationService
	@Autowired
	RestConnectionService restConnectionService
	@Autowired
	PersonService personService
	@Autowired
 	CampusService campusService

	@MockBean
	AdminDeComisionesRepository adminDeComisionesRepository
	@MockBean
	PromoterAssociationRepository promoterAssociationRepositor
	@MockBean
	PersonRepository personRepository
	@MockBean
	UserCampusRepository userCampusRepository
	@MockBean
	ProgramManagerRepository programManagerRepository
	@MockBean
	SecurityApiService securityApiService
	@MockBean
	PromoterAsignmentService promoterAsignmentService
	@MockBean
	PromoterService promoterService


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
	    // when
			Map mapResponse  = administrationService.getPersonWithValidations("r.martinez026")
			println "++"*100
			println mapResponse.person.campuses.dump()
	    // then
			assertThat(mapResponse).isNotNull()
	}
}