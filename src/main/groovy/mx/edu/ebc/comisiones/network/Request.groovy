package mx.edu.ebc.comisiones.network

import wslite.http.HTTPClientException
import groovy.util.logging.Slf4j
import wslite.rest.*

@Slf4j
class Request {
  String baseUrl = ""
  String endpointUrl = ""
  Map query = [:]
  Map headers = ["Accept":"application/json; charset=utf-8"]
  HTTPMethod method = HTTPMethod.GET
  Closure callback = {}

  Request(String url){
    this.baseUrl = url
  }

  def baseUrl(String url) { this.baseUrl = url; this }
  def endpointUrl(String e) { this.endpointUrl = e; this }
  def query(q) { this.query = q; this }
  def headers(h) { this.headers = h; this }
  def method(m) { this.method = m; this }
  def callback(c) { this.callback = c; this }

  def execute(){
    try{
      def client = new RESTClient(this.baseUrl)
      def response = client."${this.method.code}"([path:this.endpointUrl, query:this.query,
        headers:this.headers], this.callback)
      response
    }catch (HTTPClientException e) {
      handleError(
        e:e, method:this.method, baseUrl:this.baseUrl, endpoint:this.endpointUrl, query:this.query)
    }
  }

  private def handleError(Map params) {
    log.error "${params?.e} -- ${params?.e?.message} por ${params?.method}"
    log.error "Base URl ${params?.baseUrl}"
    log.error "Endpoint: ${params?.endpoint}"
    log.error "Query: ${params?.query ?: 'Sin query'}"
  }
}