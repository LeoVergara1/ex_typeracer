import socket from "./socket"
import { RacerController } from "./racer_controller"

export var MatchController = {

  validateKeyWord: ()=>{
    let nextWord = 1
    let porcent = 0
    let textArea = $("#textToValidate").text()
    $("#currentWord").text(textArea.charAt(nextWord))
    $("#pressKey").on("keydown", (event)=>{
      if (textArea.charAt(nextWord) === " "){
        console.log("Espacio")
        $("#currentWord").text("Espacio")
      }
      else {
        $("#currentWord").text(textArea.charAt(nextWord))
      }
      console.log(`Presiona : ${event.key}, Numero de palabra: ${nextWord} , Letra del parrafo: ${textArea.charAt(nextWord)}`)
      if(event.key == textArea.charAt(nextWord)){
        nextWord +=1
        porcent = (nextWord * 100) / (textArea.length)
        RacerController.sendScore(porcent)
        $("#pressKey").removeClass("error")
      } else {
        $("#pressKey").addClass("error") 
      }
      console.log(event)
    })


  },
  bindEvents:function (){
    console.log("init envents from MatchControllet")
    this.validateKeyWord()
  
  },

  start: function(){
    this.bindEvents()
  }

}

