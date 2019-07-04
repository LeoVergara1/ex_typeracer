package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query

@Repository
interface PersonRepository extends JpaRepository<Person, Integer> {
	List<Person> findAll()

	@Query("SELECT u FROM Person u WHERE u.id = 26872")
	List<Person> findAllPersons()

	Person findByIdBanner(String idBanner)
}