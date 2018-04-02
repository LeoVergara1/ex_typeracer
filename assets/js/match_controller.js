import socket from "./socket"
import { RacerController } from "./racer_controller"

export var MatchController = {
  randoomText: "",

  validateKeyWord: function(){
    let lastWord = 0
    let textCurent = ""
    let nextWord = 0
    let score = 0
    console.log(this.randoomText)
    let textArea = this.randoomText
    $("#currentWord").text(textArea.charAt(nextWord))
    lastWord = textArea.length
    $("#pressKey").on("keydown", (event)=>{
      if (textArea.charAt(nextWord) === " "){
        console.log("Espacio")
        $("#currentWord").text("Espacio")
        $("#pressKey").val("")
      }
      else {
        $("#currentWord").text(textArea.charAt(nextWord))
      }
      console.log(`Presiona : ${event.key}, Numero de palabra: ${nextWord} , Letra del parrafo: ${textArea.charAt(nextWord)}`)
      if(event.key == textArea.charAt(nextWord)){
        textCurent +=textArea.charAt(nextWord)
        nextWord +=1
        score = (nextWord * 100) / (textArea.length)
        RacerController.sendScore(score)
        console.log(`Texto actual: ${textCurent} cadena:  ${textArea.substring(nextWord)}`)
        $("#textCurrent").text(textCurent)
        $("#textToValidate").text(textArea.substring(nextWord))
        $("#pressKey").removeClass("error")
      } else {
        $("#pressKey").addClass("error") 
      }
      console.log(event)
    })


  },

  initChannelRoom: function () {
    let channelRoom = socket.channel("room:new", {})
    let that = this
    channelRoom.join()
			.receive("ok", resp => {
        console.log("Room successfully 😎", resp)
        this.randoomText = resp.text 
        console.log(this.randoomText)
        $("#textToValidate").text(resp.text)
        this.validateKeyWord()
			})
      .receive("error", resp => { console.log("Unable to join", resp) })
      

  },
  bindEvents:function (){
    console.log("init envents from MatchControllet")
    this.initChannelRoom()
  
  },

  start: function(){
    this.bindEvents()
  }

}

