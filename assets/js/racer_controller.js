import socket from "./socket"
import HandlebarsResolver from "./handlebars_resolver"
import { MatchController } from "./match_controller";

export var RacerController = {
  uuid: Math.floor((Math.random() * 10000) + 1),
  channelScore: null,
  channelRoom: null, 
  processRoom: null,
  username:null,

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
				$("#list_roms").html("")
					$.each(msg.rooms, function( index, value ) {
            $("#list_roms").append(`<p><strong> Sala: </strong> ${value} <span></span></p> `)
					});
			});
  },
  initChannel: function() {
    let that = this 
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
            that.showRunArea(that.processRoom)
            $("#timer_run_area").hide();
            that.channelRoom.push("show_run_area", that.processRoom)
            
          }
      });

      $("#timer_run_area").on("click", "#start-timer" , () =>{
      channel
      .push('start_timer', {})
      .receive('ok', resp => { console.log("Starter timer",resp) })
    })
  },

  showRunArea: function(nameRom) {
    this.channelRoom.on(`${nameRom}`, msg => {
      console.log(msg)
      HandlebarsResolver.constructor.mergeViewWithModel("#run_area", msg, "container-run-area")
      MatchController.validateKeyWord(msg.data)
    })

  },

  initChannelPlayers: function () {
    let channelPlayer = socket.channel("players", {uuid: this.uuid})
    let that = this

//			channelPlayer.on("players_list", msg => {
//				$("#list_users").html("")
//					$.each(msg.users, function( index, value ) {
//            if (value == that.uuid)
//              $("#list_users").append(`<p><strong> You </strong> Score: %<span id='${value}'>0</span></p> `)
//            else
//              $("#list_users").append(`<p><strong> Guess </strong> Score: %<span id='${value}'>0</span></p> `)
//					});
//			});

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
        that.processRoom = response.process;
        that.uuid = response.process
        that.username = response.user
        that.channelRoom.on(`updating_player_${that.uuid}`, msg =>{
          console.log(msg)  
          let userList = msg.game.players;
          that.username = msg.user;
          console.log(that.username)
          HandlebarsResolver.constructor.mergeViewWithModel("#list_users_players", {userList}, "list_user_area")
        });
        HandlebarsResolver.constructor.mergeViewWithModel("#timer_area", response, "timer_run_area")
      })
    })
  },

  joinRom: function(){
    let that = this
    $("#join-room").on("click", () =>{
      that.username = $("#username").val()
      that.uuid = $("#game-id").val()
      this.channelRoom
      .push('join_race', {username: that.username, uuid: that.uuid})
      .receive('ok', response =>{ 
        console.log("ok", response)
        that.username = response.user
        that.processRoom = response.process;
        if(response.process == this.uuid){
          that.channelRoom.push("updating_players", that.uuid)
          HandlebarsResolver.constructor.mergeViewWithModel("#timer_area", response, "timer_run_area")
          HandlebarsResolver.constructor.mergeViewWithModel("#list_users_players", response, "list_user_area")
        }
      })
    })
  },

  sendScore: function (score) {
    this.channelScore
      .push('scores:set', {user: this.username, score:score, uuid: this.uuid})

  },

  bindEvents:function (){
    this.initRom()
    this.joinRom()
    this.initChannel()
    this.initChannelPlayers()
    this.initChannelScores()
    this.initChannelRoom()
  },

  testContext: function(){
  },

  start: function(){
    this.bindEvents()
  }

}

