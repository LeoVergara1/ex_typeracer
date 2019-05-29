var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!',
    username: document.getElementById("username").value,
    person: Object,
    headerBgVariant: "white",
    headerTextVariant: 'dark'
  },
  created: function() {
    this.$http.post('/administration/search/association', {user: this.username}).then(response => {
      // get body data
      console.log(response.body);
      this.person = response.body.person
    }, response => {
      console.log("Fail")
      console.log(response)
      // error callback
    })
    this.getCoordinators()
  },
  methods:{
    update: function (numero) {
      console.log(numero)
    },
    sendAssociation: function() {
      console.log("Click")
      this.$bvModal.show("modal-1")
    },
    closedModal: function(){
      this.$bvModal.hide("modal-1")
    },
    getCoordinators: function(){
      this.$http.get('/administration/coordinators').then(response => {
        // get body data
        console.log(response.body);
      }, response => {
        console.log("Fail")
        console.log(response)
        // error callback
      })
    }
  }
})