package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query

@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer> {
	List<Goal> findAll()
}