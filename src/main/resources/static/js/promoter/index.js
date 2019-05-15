var app = new Vue({
  el: '#app',
  data: {
		message: 'Hello Vue!',
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