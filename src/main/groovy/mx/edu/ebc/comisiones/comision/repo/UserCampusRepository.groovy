package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.UserCampus;
import mx.edu.ebc.comisiones.comision.domain.UserCampusComposite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.Query

@Repository
interface UserCampusRepository extends JpaRepository<UserCampus,UserCampusComposite> {
	List<UserCampus> findByUserCampusPK_UserName(String userName)
	@Query("select u from UserCampus u where u.userCampusPK.campusCode = :campusCode and u.userCampusPK.userName = :userName")
  UserCampus findByCampusCodeAndUserName(@Param("campusCode")String codeCampus, @Param("userName")String userName)
}