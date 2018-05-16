import socket from "./socket"
import HandlebarsResolver from "./handlebars_resolver"
import { MatchController } from "./match_controller";
import "sprite-js"
import "./sprite"
import "bootstrap-notify"

export var RacerController = {
  uuid: Math.floor((Math.random() * 10000) + 1),
  channelScore: null,
  channelRoom: null, 
  processRoom: null,
  username:null,
  name_room: null,
  uuid_game: null,

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
  knowExistUsername: function (username) {
    this.channelRoom.push("exist_username", username)
      .receive('ok', resp => { console.log(resp)
        if (resp.existed){
          $("#icon_check").hide()
          $("#icon_warning").show()
        }
        else {
          $("#icon_check").show()
          $("#icon_warning").hide()
        }
      })
  },

  knowExistEmail: function (email) {
    this.channelRoom.push("exist_email", email)
      .receive('ok', resp => { console.log(resp)
        if (resp.existed){
          $("#icon_check_eamil").hide()
          $("#icon_warning_email").show()
        }
        else {
          $("#icon_check_email").show()
          $("#icon_warning_email").hide()
        }
      })
  },
  listnenKeyBoeard: function(){
    let that = this
    $("#validUsername").on("keyup", () => {
      console.log("Tecla.")
      let username = $("#validUsername").val() 
      console.log(username)
      that.knowExistUsername(username)
    });
    $("#validEmail").on("keyup", () => {
      let email = $("#validEmail").val()
      that.knowExistEmail(email)
    }) 
  },
  initChannelTimer: function(name_room, uuid_game) {
    let that = this 
    console.log(uuid_game);
    var channel = socket.channel("timer:update", {})
      channel.join()
      .receive("ok", resp => { console.log("Timer Channel Joined 😉", resp) })
      .receive("error", resp => { console.log("No se puede conectar al Timer Channel", resp) })

      channel.on(`new_time_${uuid_game}`, msg => {
          document.getElementById('status').innerHTML = msg.response
          document.getElementById('timer').innerHTML = msg.time

          $("#start-timer").hide()
          if (msg.time === 0){
            $("#start-timer").show()
            that.showRunArea(name_room)
            $("#timer_run_area").hide();
            that.channelRoom.push("show_run_area", name_room)
            that.animattionSprite()
          }
      });

      channel.on(`waiting_time_${uuid_game}`, msg => {
        $("#container_timer_waiting").show()
        $("#timer_waiting").text(msg.time)
        if(msg.time == 0){
          $("#start-timer").trigger("click")
        }
      });

      channel.on(`playing_time_${uuid_game}`, msg => {
        $("#container_timer_waiting").hide()
        $("#container_timer_playing").show()
        $("#timer_playing").text(msg.time)
        if(msg.time == 0){
          console.log("Juego terminado")
        }
      });

      $("#timer_run_area").on("click", "#start-timer" , () =>{
        console.log(`Este es id ${uuid_game}`)
      channel
      .push('start_timer', {name_room})
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
    let that = this
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
          let score = (element.score) * .83
          console.log(score)
          $(`#${element.username}-sprite`).css("margin-left", `${score}%`)
          console.log($(`#${that.username}-sprite`))
        });
      });
      
      this.channelScore.on("socore:winer_show", msg => {
        console.log(msg);
        if(msg.uuid_game == that.uuid_game){
          console.log("Estas aquí...");
          HandlebarsResolver.constructor.mergeViewWithModel("#positions_to_player", msg, "list_positions_container")
        }
      });
  },
  initRom: function(){
    let that = this
    $("#start-room").on("click", () =>{
      console.log("click")
      that.uuid = $("#recipient_name").val()
      that.name_room = $("#name_room_txt").val()
      console.log(that.name_room)
      console.log(that.uuid)
      this.channelRoom
      .push('init_reace', {username: that.uuid, name_room: that.name_room})
      .receive('ok', response =>{ 
        console.log("ok", response)
        that.processRoom = response.process;
        that.uuid = response.process
        that.username = response.user
        that.uuid_game = response.uuid 
        that.updatingPlayers(that.name_room)
        that.initChannelTimer(that.name_room, that.uuid_game)
        HandlebarsResolver.constructor.mergeViewWithModel("#timer_area", response, "timer_run_area")
        HandlebarsResolver.constructor.mergeViewWithModel("#list_users_players", response, "list_user_area")
        $("#container-header-player").hide()
      })
    })
  },

  updatingPlayers: function (name_room) {
    console.log(`Reinicia lista. ${name_room}`)
    this.channelRoom.on(`updating_player_${name_room}`, msg => {
      console.log(msg)
      let userList = msg.game.players;
      console.log(this.username)
      HandlebarsResolver.constructor.mergeViewWithModel("#list_users_players", { userList }, "list_user_area")
    });
  },
  joinRom: function(){
    let that = this
    $("#join-room").on("click", () =>{
      that.username = $("#username_join").val()
      that.name_room = $("#game-id").val()
      this.channelRoom
      .push('join_race', {username: that.username, name_room: that.name_room})
      .receive('ok', response =>{ 
        console.log("ok", response)
        if(response.status === "waiting"){
          if(response.process == this.name_room){
            that.username = response.user
            that.processRoom = response.process;
            that.uuid_game = response.uuid;
            console.log(`Este es el game id: ${that.uuid_game}`)
            that.updatingPlayers(that.name_room)
            that.channelRoom.push("updating_players", that.name_room)
            that.initChannelTimer(that.name_room, that.uuid_game)
            HandlebarsResolver.constructor.mergeViewWithModel("#timer_area", response, "timer_run_area")
            HandlebarsResolver.constructor.mergeViewWithModel("#list_users_players", response, "list_user_area")
            $("#container-header-player").hide()
          }
        }
        else {
          console.log("La sala esta en juego o ya no")
          $.notify({
            // options
            message: 'La sala esta en juego o ya no está disponible' 
          },{
            // settings
            type: 'danger'
          });
        }
      })
    })
  },

  sendScore: function (score) {
    console.log(this.username);
    this.channelScore
      .push('scores:set', {user: this.username, score:score, name_rom: this.processRoom, tes:this.username})

  },
  sendPosition: function () {
    this.channelScore
      .push('scores:position', {username: this.username, name_room: this.processRoom})
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

  validateFormRegister: function (){
    $("#submit_button_register").on("click", () => {
      console.log("hgh")
      if ($("#icon_check").is(":visible") && $("#icon_check_email").is(":visible")){
        $("#register_form").submit()
      }
      else {
        $("#submit_button_register").append("Revisa")
      }
      
    })
  },

  bindEvents:function (){
    this.initRom()
    this.joinRom()
    this.initChannelPlayers()
    this.initChannelScores()
    this.initChannelRoom()
    this.scenePlayer()
    this.listnenKeyBoeard()
    this.validateFormRegister()
  },

  testContext: function(){
  },

  start: function(){
    this.bindEvents()
  }

}

