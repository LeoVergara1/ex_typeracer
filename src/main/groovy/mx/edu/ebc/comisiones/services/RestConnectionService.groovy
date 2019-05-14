package mx.edu.ebc.comisiones.services

interface RestConnectionService {
  def get(String apiClient, String endpoint, Map query)

  def postJson(String apiClient, String endpoint, Map query, Closure callback)

  def post(String apiClient, String endpoint, Map query)

  def put(String apiClient, String endpoint, Map query)

  def delete(String apiClient, String endpoint, Map query)

  def post(String apiClient, String endPoint, Map query, def object)
  def put(String apiClient, String endPoint, Map query, def object)
}
