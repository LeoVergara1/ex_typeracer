package mx.edu.ebc.comisiones.services

import mx.edu.ebc.comisiones.services.RestConnectionService
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service
import wslite.http.HTTPClientException
import wslite.rest.RESTClient
import groovy.json.*

@Service
class RestConnectionServiceImpl implements RestConnectionService {

  Logger logger = LoggerFactory.getLogger(RestConnectionServiceImpl.class);

  @Override
  def get(String apiClient, String endpoint, Map query = [:]){
    try{
      def client = new RESTClient(apiClient)
      def response = client.get(path:endpoint, query:query, headers:["Accept":"application/json; charset=utf-8"])
      response.json
    }catch (HTTPClientException e) {
      logger.error "query ${query}"
    }
  }

  def postJson(String apiClient, String endpoint, Map query = [:], Closure callback = {}){
    try{
      def client = new RESTClient(apiClient)
      def response = client.post([path:endpoint,query:query, headers:["Accept":"application/json; charset=utf-8", "Content-Type":"application/json"]], callback)
      response
    }catch (HTTPClientException e) {
      logger.error e
      logger.error endpoint
      logger.error "query ${query}"
    }
  }

  def post(String apiClient, String endpoint, Map query = [:]){
    try{
      def client = new RESTClient(apiClient)
      def response = client.post([path:endpoint,query:query, headers:["Accept":"application/json; charset=utf-8"]])
      response
    }catch (HTTPClientException e) {
      logger.error "${e}"
      logger.error "${endpoint}"
      logger.error "query ${query}"
      e.response
    }
  }

  @Override
  def put(String apiClient, String endpoint, Map query = [:]){
    try{
      def client = new RESTClient(apiClient)
      def response = client.put(path:endpoint, query:query, headers:["Accept":"application/json; charset=utf-8"])
      response
    }catch (HTTPClientException e) {
      logger.error e
      logger.error endpoint
      logger.error "query ${query}"
      e.response
    }
  }

  @Override
  def delete(String apiClient, String endpoint, Map query) {
    try{
      logger.info "Ejecuta el borrado"
      def client = new RESTClient(apiClient)
      def response = client.delete(path:endpoint, query:query, headers:["Accept":"application/json; charset=utf-8"])
      response
    }catch (HTTPClientException e) {
      logger.error "${e}"
      logger.error "query ${query}"
      e.response
    }
  }

  @Override
  def post(String apiClient, String endPoint, Map query, def object){
    try {
      def client = new RESTClient(apiClient)
      def response = client.post(path: endPoint, query: query, headers: ["Accept": "application/json; charset=utf-8"]) {
        type 'application/json'
        charset 'utf-8'
        text JsonOutput.toJson(object)
      }
      response
    }catch (HTTPClientException e){
      logger.error "endPoint: $endPoint, query: $query, object:${object.dump()}, error: $e"
      e.response
    }
  }

  @Override
  def put(String apiClient, String endPoint, Map query, Object object) {
    try {
      def client = new RESTClient(apiClient)
      def response = client.put(path: endPoint, query: query, headers: ["Accept": "application/json; charset=utf-8"]) {
        type 'application/json'
        charset 'utf-8'
        text JsonOutput.toJson(object)
      }
      response
    }catch (HTTPClientException e){
      logger.error "endPoint: $endPoint, query: $query, object:${object.dump()}, error: $e"
      e.response
    }
  }
}
