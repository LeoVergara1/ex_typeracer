package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.pojos.AssignablePromoter
import mx.edu.ebc.comisiones.pojos.Person
import mx.edu.ebc.comisiones.pojos.Promoter
import mx.edu.ebc.comisiones.pojos.PromoterCode
//import mx.edu.ebc.comisiones.pojos.PromoterManagerAssociation
import mx.edu.ebc.comisiones.pojos.SecurityUser
import mx.edu.ebc.comisiones.pojos.UserCampus

interface PromoterAsignmentService {
  //List<AssignablePromoter> findAllPromotersByCampus(String campusCode)
  //List<UserCampus> getAllUsersByCampusCode(String campusCode)
  //List<SecurityUser> getAllUsersByRoleId(Long roleId)
  //List<Promoter> findPromotersByUserNames(List<String> userNamesList)
  //Person getManagerByUserName(String userName)
  //Map getManagerAndPromotersFromMap(Map<String, String> formParameters)
  //void asignAndDeletePromotersForManager(String managerUserName, List<PromoterCommand> promoterUserNames)
  //boolean newManagerPromoterRelation(PromoterManagerAssociation promoterManagerAssociation)
  //boolean deleteManagerPromoterRelation(PromoterManagerAssociation promoterManagerAssociation)
  //void asignListOfPromotersToManager(List<PromoterManagerAssociation> promoterManagerAssociations)
  //void unasignListOfPromotersToManager(List<PromoterManagerAssociation> promoterManagerAssociations)
  //List<AssignablePromoter> findPromotersOfAManagerByPidm(Long managerPidm, List<AssignablePromoter> assignablePromoters)
  //List<PromoterManagerAssociation> findPromotersForManagerAssignment(String managerUserName, List<AssignablePromoter> assignablePromoters, List<PromoterCommand> checkedPromoters)
  //List<PromoterManagerAssociation> findPromotersForManagerUnassignment(String managerUserName, List<AssignablePromoter> assignablePromoters, List<PromoterCommand> checkedPromoters)
  //Map getCampusAndRoleDescriptionFromAManager(Person manager)
  Integer isPromoterPidmAndRecrCodeValidForRegistration(Long promoterPidm, String recrCode)
  Boolean isValidRadmCode(Long promoterPidm)
  Boolean isValidRecrCode(String recrCode)
  //List<PromoterCode> findAllPromotersFromBannerByRadmCode(String radmCode)
}
