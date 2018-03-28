import socket from "./socket"

export var MatchController = {

  validateKeyWord: ()=>{
    let nextWord = 1
    let textArea = $("#textToValidate").text()
    $("#pressKey").on("keydown", (event)=>{
      console.log(`Presiona : ${event.key}, Numero de palabra: ${nextWord} , Letra del parrafo: ${textArea.charAt(nextWord)}`)
      if(event.key == textArea.charAt(nextWord)){
        nextWord +=1
        $("#pressKey").removeClass("error")
      } else {
        $("#pressKey").addClass("error") 
      }
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

