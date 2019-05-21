package mx.edu.ebc.comisiones.services

interface ManagerService {
  boolean createManager(String userName, Long pidm, String recrCode)
  boolean deleteManager(String userName)
}