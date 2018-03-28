import socket from "./socket"

export var RacerController = {

  initChannel: () =>{
    var channel = socket.channel("timer:update", {})
      channel.join()
      .receive("ok", resp => { console.log("Timer Channel Joined 😉", resp) })
      .receive("error", resp => { console.log("No se puede conectar al Timer Channel", resp) })

      channel.on("new_time", msg => {
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

    let channelPlayer = socket.channel("players", {uuid: Math.floor((Math.random() * 10000) + 1)})

			channelPlayer.on("players_list", msg => {
				$("#list_users").html("")
					$.each(msg.users, function( index, value ) {
						$("#list_users").append(`<p>username: ${value.uuid}</p>`)
					});
			});

			channelPlayer.join()
				.receive("ok", resp => {
					console.log("Player successfully 😎", resp)
				})
				.receive("error", resp => { console.log("Unable to join", resp) })

			channelPlayer.push('get_list', {})
  },

  initChannelScores: () =>{
    var channel = socket.channel("scores", {})
			//Join al Channel de Scores
      channel.join()
      .receive("ok", resp => { console.log("Scores Channel Joined 😙 ", resp) })
      .receive("error", resp => { console.log("No se puede conectar al Scores Channel", resp) })
			// Socket para registrar los scores por usuario
      channel
      .push('scores:set', {user:"carlogilmar", score:100})
      .receive('ok', resp => { console.log("Starter timer",resp) })
			// Socket para traer todos los scores
      channel
      .push('scores:get', { user: Math.floor((Math.random() * 10000) + 1) })
      .receive('ok', resp => { console.log("ok",resp) })
			// Socket donde llegarán todos los scores
			channel.on("scores:show", msg => {
				console.log(msg)
			});
  },

  bindEvents:function (){
    this.initChannel()
    this.initChannelPlayers()
    this.initChannelScores()
  },

  start: function(){
    this.bindEvents()
  }

}

