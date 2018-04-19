import socket from "./socket"
import HandlebarsResolver from "./handlebars_resolver"
import { MatchController } from "./match_controller";
import "sprite-js"
import "./sprite"

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
            $("#list_roms").append(`<p><i class="fa fa-cloud"></i><strong> Sala: </strong> ${value} <span></span></p> `)
					});
			});
  },
  initChannelTimer: function(uuid) {
    let that = this 
    var channel = socket.channel("timer:update", {})
      channel.join()
      .receive("ok", resp => { console.log("Timer Channel Joined 😉", resp) })
      .receive("error", resp => { console.log("No se puede conectar al Timer Channel", resp) })

      channel.on(`new_time_${that.uuid}`, msg => {
          document.getElementById('status').innerHTML = msg.response
          document.getElementById('timer').innerHTML = msg.time

          $("#start-timer").hide()
          if (msg.time === 0){
            $("#start-timer").show()
            that.showRunArea(that.processRoom)
            $("#timer_run_area").hide();
            that.channelRoom.push("show_run_area", that.processRoom)
            that.animattionSprite()
          }
      });

      $("#timer_run_area").on("click", "#start-timer" , () =>{
      channel
      .push('start_timer', {uuid})
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
//      this.channelScore
//      .push('scores:get', { user: this.uuid })
//      .receive('ok', resp => { console.log("ok",resp) })
			// Socket donde llegarán todos los scores
			this.channelScore.on("scores:show", msg => {
        msg.game.forEach(element => {
          console.log(element)
          $(`#${element.username}`).text(element.score)
        });
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
        that.updatingPlayers(that.uuid)
        that.initChannelTimer(that.uuid)
        HandlebarsResolver.constructor.mergeViewWithModel("#timer_area", response, "timer_run_area")
        $("#container-header-player").hide()
      })
    })
  },

  updatingPlayers: function (uuid) {
    this.channelRoom.on(`updating_player_${uuid}`, msg => {
      console.log(msg)
      let userList = msg.game.players;
      console.log(this.username)
      HandlebarsResolver.constructor.mergeViewWithModel("#list_users_players", { userList }, "list_user_area")
    });
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
        if(response.process == this.uuid){
          that.username = response.user
          that.processRoom = response.process;
          that.updatingPlayers(that.uuid)
          that.channelRoom.push("updating_players", that.uuid)
          that.initChannelTimer(that.uuid)
          HandlebarsResolver.constructor.mergeViewWithModel("#timer_area", response, "timer_run_area")
          HandlebarsResolver.constructor.mergeViewWithModel("#list_users_players", response, "list_user_area")
          $("#container-header-player").hide()
        }
      })
    })
  },

  sendScore: function (score) {
    console.log(this.username);
    this.channelScore
      .push('scores:set', {user: this.username, score:score, uuid: this.uuid, tes:this.username})

  },

  animattionSprite: function () {
    console.log("Inicia animación")
    $(".scott").animateSprite({
      fps: 12,
      animations: {
          walkRight: [0, 1, 2, 3, 4, 5, 6, 7],
          walkLeft: [15, 14, 13, 12, 11, 10, 9, 8]
      },
      loop: true,
      complete: function(){
          // use complete only when you set animations with 'loop: false'
          alert("animation End");
      }
  });
  },

  scenePlayer: function() {
    var image = new Image()
    image.src = '/images/pan.png'
    console.log(image)
    var sprite = new Sprite({
      canvas: document.getElementById('canvas'),
      image: image,
      rows: 4,
      columns: 3,
      columnFrequency: 1
    });
  },

  bindEvents:function (){
    this.initRom()
    this.joinRom()
    this.initChannelPlayers()
    this.initChannelScores()
    this.initChannelRoom()
    this.scenePlayer()
  },

  testContext: function(){
  },

  start: function(){
    this.bindEvents()
  }

}

