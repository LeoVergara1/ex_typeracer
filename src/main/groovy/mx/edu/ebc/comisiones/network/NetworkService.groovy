package mx.edu.ebc.comisiones.network

class NetworkService {

	static buildRequest(String url, @DelegatesTo(Request) Closure cl){
    def request = new Request(url)
    def code = cl.rehydrate(request, this, this)
    code.resolveStrategy = Closure.DELEGATE_ONLY
    code()
  }

}