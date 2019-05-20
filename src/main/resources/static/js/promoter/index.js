var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!',
    user: {
      person: {},
      managerRoleId: 0,
      mapRol: {}
    },
    campus: {
      list: {}
    },
    listAssociation: [],
    notifyOptions: {
      timeout: 3000,
      showProgressBar: true,
      closeOnClick: false,
      pauseOnHover: true,
      position: 'rightTop'
    },
	},
	created: function (){

	},
  methods:{
    update: function (numero) {
      console.log(numero)
    }
  }
})