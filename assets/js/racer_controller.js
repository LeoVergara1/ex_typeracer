import socket from "./socket"
import HandlebarsResolver from "./handlebars_resolver"

export var RacerController = {
  uuid: Math.floor((Math.random() * 10000) + 1),
  channelScore: null,
  channelRoom: null, 

  initChannelRoom: function () {
    this.channelRoom = socket.channel("room:new", {})
    let that = this
    this.channelRoom.join()
			.receive("ok", resp => {
        console.log("Room successfully 😎", resp)
        this.channelRoom.push("get_romms")
			})
      .receive("error", resp => { console.log("Unable to join", resp) })
	  this.channelRoom.on("list_rooms", msg => {
				$("#list_users").html("")
					$.each(msg.rooms, function( index, value ) {
            $("#list_roms").append(`<p><strong> Sala: </strong> ${value} <span></span></p> `)
					});
			});
  },
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

  initChannelPlayers: function () {
    let channelPlayer = socket.channel("players", {uuid: this.uuid})
    let that = this

			channelPlayer.on("players_list", msg => {
				$("#list_users").html("")
					$.each(msg.users, function( index, value ) {
            if (value == that.uuid)
              $("#list_users").append(`<p><strong> You </strong> Score: %<span id='${value}'>0</span></p> `)
            else
              $("#list_users").append(`<p><strong> Guess </strong> Score: %<span id='${value}'>0</span></p> `)
					});
			});

			channelPlayer.join()
				.receive("ok", resp => {
					console.log("Player successfully 😎", resp)
				})
				.receive("error", resp => { console.log("Unable to join", resp) })

			channelPlayer.push('get_list', {})
  },

  initChannelScores: function () {
    this.channelScore = socket.channel("scores", {})
			//Join al Channel de Scores
      this.channelScore.join()
      .receive("ok", resp => { console.log("Scores Channel Joined 😙 ", resp) })
      .receive("error", resp => { console.log("No se puede conectar al Scores Channel", resp) })
      this.channelScore
      .push('scores:get', { user: this.uuid })
      .receive('ok', resp => { console.log("ok",resp) })
			// Socket donde llegarán todos los scores
			this.channelScore.on("scores:show", msg => {
        $(`#${msg.user}`).text(msg.score)
			});
  },

  initRom: function(){
    let that = this
    $("#start-room").on("click", () =>{
      console.log("click")
      that.uuid = $("#recipient-name").val()
      console.log(that.uuid)
      this.channelRoom
      .push('init_reace', {username: that.uuid})
      .receive('ok', response =>{ 
        console.log("ok", response)
        HandlebarsResolver.constructor.mergeViewWithModel("#timer_area", response, "container-header-player")
      })
    })
  },

  sendScore: function (score) {
      this.channelScore
      .push('scores:set', {user: this.uuid, score:score})

  },

  bindEvents:function (){
    this.initRom()
    this.initChannel()
    this.initChannelPlayers()
    this.initChannelScores()
    this.initChannelRoom()
  },

  start: function(){
    this.bindEvents()
  }

}

