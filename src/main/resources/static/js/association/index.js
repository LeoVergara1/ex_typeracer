var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!',
    username: document.getElementById("username").value,
    person: Object,
    headerBgVariant: "white",
    headerTextVariant: 'dark',
    promoters: Array,
    listPromoterToUser: [],
    loader:{
      color: '#0b93d1',
      height: '15px',
      width: '5px',
      margin: '2px',
      radius: '2px',
      loading: false,
      size: "95px",
    }
  },
  computed: {
  },
  created: function() {
    this.loader.loading = true
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
      this.loader.loading = true
      this.$http.post('/administration/save/association', { listPromoterToUser: this.listPromoterToUser, person: this.person } ).then( response =>{
        console.log(response)
        this.loader.loading = false
        this.$bvModal.show("modal-1")
      }, response => {
        console.log(response)
      })
    },
    closedModal: function(){
      this.$bvModal.hide("modal-1")
    },
    getCoordinators: function(){
      this.$http.get('/administration/coordinators').then(response => {
        console.log(response.body);
        this.promoters = response.body
        this.listPromoterToUser = this.promoters.filter((element, index, array)=>{
          if(element.promoter.programManager.userName == this.person.userName){ element.associate = true}
          return (element.campuses[0].code == this.person.campuses[0].campusCode)
        })
        this.loader.loading = false
      }, response => {
        console.log("Fail")
        console.log(response)
      })
    },
    validateAssociation: function(promoter){
      let username = promoter.promoter.programManager.userName
      if(!username){
       return "notAssociate"
      }
      else if(username != this.person.userName){
        return "associateHer"
      }
      else{
        return "associateYou"
      }
    }
  },
  mounted() {
    this.$root.$on('bv::modal::hide', (bvEvent, modalId) => {
      this.loader.loading = true
      this.getCoordinators()
    })
  },
  components: {
    RingLoader: VueSpinner.RingLoader
  }
})