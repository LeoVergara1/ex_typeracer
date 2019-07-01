package mx.edu.ebc.comisiones.network

enum HTTPMethod{
  GET("get"),
  POST("post"),
  PUT("put"),
  DELETE("delete")

  private final String code

  HTTPMethod(String code){ this.code = code }

  String getCode(){ return this.code }
}