var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!',
    username: document.getElementById("username").value,
    person: Object,
    headerBgVariant: "white",
    headerTextVariant: 'dark',
    promoters: Array,
    listPromoterToUser: []
  },
  computed: {
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
    });
    this.getCoordinators()
  },
  methods:{
    update: function (numero) {
      console.log(numero)
    },
    sendAssociation: function() {
      console.log("Click")
      this.$http.post('/administration/save/association', { listPromoterToUser: this.listPromoterToUser, person: this.person } ).then( response =>{
        console.log(response)
      }, response => {
        console.log(response)
      })
      this.$bvModal.show("modal-1")
    },
    closedModal: function(){
      this.$bvModal.hide("modal-1")
    },
    getCoordinators: function(){
      this.$http.get('/administration/coordinators').then(response => {
        console.log(response.body);
        this.promoters = response.body
        this.listPromoterToUser = this.promoters.filter((element, index, array)=>{
          return (element.campuses[0].code == this.person.campuses[0].campusCode)
        })
      }, response => {
        console.log("Fail")
        console.log(response)
      })
    },
    validateAssociation: function(promoter){
      console.log(promoter)
      let username = promoter.promoter.programManager.userName
      if(!username){
       return "notAssociate"
      }
      else if(username != this.person.userName){
        console.log(this.person)
        return "associateHer"
      }
      else{
        return "associateYou"
      }
    }
  }
})