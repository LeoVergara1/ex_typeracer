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

  initChannelPlayers: () => {
    console.log("Init players channel")
    let channelPlayer = socket.channel("players", {uuid: Math.floor((Math.random() * 10000) + 1)})

    channelPlayer.on("players_list", msg => {
      console.log(msg)
    });

    channelPlayer.join()
      .receive("ok", resp => {
        console.log("Player successfully 😎", resp)
      })
      .receive("error", resp => { console.log("Unable to join", resp) })
    channelPlayer.push('get_list', {})
  },

  bindEvents:function (){
    this.initChannel()
    this.initChannelPlayers()
  },

  start: function(){
    this.bindEvents()
  }

}

