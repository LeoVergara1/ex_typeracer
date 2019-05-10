var app = new Vue({
  el: '#app',
  data: {
		message: 'Hello Vue!',
		listAssociation: []
	},
	created: function (){

	},
  methods:{
    update: function (numero) {
      console.log(numero)
    }
  }
})