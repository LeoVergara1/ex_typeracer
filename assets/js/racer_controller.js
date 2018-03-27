import socket from "./socket"

export var RacerController = {

  initChannel: () =>{
    console.log("init channel")
    var channel = socket.channel("timer:update", {})
      channel.join()
      .receive("ok", resp => { console.log("Timer Channel Joined 😉", resp) })
      .receive("error", resp => { console.log("No se puede conectar al Timer Channel", resp) })

      channel.on("new_time", msg => {
        console.log("The timer is ", msg.time)
          document.getElementById('status').innerHTML = msg.response
          document.getElementById('timer').innerHTML = msg.time

          $("#start-timer").hide()
          if (msg.time === 0){
            $("#start-timer").show()
          }
      });

    $("#start-timer").on("click" , () =>{
      channel
      .push('start_timer', {})
      .receive('ok', resp => { console.log("Starter timer",resp) })
    })
  },

  bindEvents:function (){
    this.initChannel()
  },

  start: function(){
    this.bindEvents()
  }

}

