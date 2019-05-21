package mx.edu.ebc.comisiones.enumeration

enum AdministratorAccess {
  GRANT_ACCESS(1),
  NO_ACCESS(0)

  AdministratorAccess(Integer value) {
    this.value = value
  }
  private final Integer value
  Integer getValue() {
    value
  }
}