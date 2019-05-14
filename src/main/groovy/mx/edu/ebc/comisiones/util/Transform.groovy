package mx.edu.ebc.comisiones.util

import groovy.json.*

class Transform {

  static JsonBuilder gatMapWithNameToSharedMapAndObject(Map map){
    new JsonBuilder(map)
  }

  static String getProcessId(){
    int number = Math.abs(new Random().nextInt() % 600 + 1)
    "process${number}"
  }

  static JsonBuilder getJsonObjectFromClass(def object){
    new JsonBuilder(object)
  }


  static Map mapFromBodyJson(def map){
    Map newMap = [:]
    map.each{ k, v ->
      newMap.put(k,
      v ? v : "null"
      )
    }
    newMap
  }

  static def getJsonFromString(String message){
    def jsonSlurper = new JsonSlurper()
    def jsonMap = jsonSlurper.parseText(message)
    jsonMap
  }


  static def extractProperties(obj) {
    obj.getClass()
       .declaredFields
       .findAll { !it.synthetic }
       .collectEntries { field ->
           [field.name, obj."$field.name"]
      }
  }
}
