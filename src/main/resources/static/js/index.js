var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!'
  },
  methods:{
    update: function (numero) {
      console.log(numero)
    }
  }
})