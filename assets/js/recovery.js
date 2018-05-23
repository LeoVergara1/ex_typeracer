
export var RecoveryController = {

  comaperePasswords: function () {
    $("#new_password").on("keyup", () => {
      if($("#new_password").val() == $("#repeat_password").val()){
        $("#icon_check").show()
        $("#icon_warning").hide()
      }
      else {
        $("#icon_check").hide()
        $("#icon_warning").show()
      }
    });
    $("#change_button").on("click", () => {
      if($("#new_password").val() == $("#repeat_password").val()){
        $("#restore_form").submit()
      }
    });
  },

  bindEvents:function (){
    this.comaperePasswords()
  },

  start: function(){
    this.bindEvents()
  }

}