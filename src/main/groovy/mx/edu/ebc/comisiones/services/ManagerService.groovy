package mx.edu.ebc.comisiones.services

interface ManagerService {
  Map createManager(String userName, Long pidm, String recrCode)
  boolean deleteManager(String userName)
}