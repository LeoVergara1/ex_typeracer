import socket from "../global/socket"
import "bootstrap-notify"

export var LoginController ={
  channelRoom: null,
  initChannelRoom: function () {
    $("#container_timer_waiting").hide()
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
            $("#list_roms").append(`<p><i class="fa fa-cloud "></i><strong > Sala: </strong> <span class="pointer room_ref">${value}</span></p> `)
					});
			});
  },
recoveryPass: function() {
    $("#recovery_button").on("click", ()=> {
      let email = $("#validEmail_pass").val()
      console.log(email)
      $("#modalPassword").modal("hide")
      this.channelRoom.push("recovery_pass", email)
        .receive('ok', resp => { console.log(resp)
          if (resp.existed){
            $.notify({message: 'Datos enviados' },{type: 'danger'});
          }
          else {
            $.notify({message: 'El correo no existe' },{type: 'danger'});
          }
        })
    })
  },

  bindEvents:function (){
    console.log("Init envents Logind")
    this.initChannelRoom()
    this.recoveryPass()
  },

  testContext: function(){
  },

  start: function(){
    this.bindEvents()
  }
}

LoginController.start()