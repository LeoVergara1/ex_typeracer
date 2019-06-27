var app = new Vue({
  el: '#app',
  data: {
    showCampus: false,
    showInfoTrimester: false,
    message: 'Hello Vue!',
    person: Object,
    headerBgVariant: "white",
    headerTextVariant: 'dark',
    promoters: Array,
    enableEdit: true,
    listPromoterToUser: [],
    trimesters: [],
    goals: [],
    notifyOptions: {
      timeout: 9000,
      showProgressBar: true,
      closeOnClick: false,
      pauseOnHover: true,
      position: 'rightTop',
      helperNotificationCycle: true
    },
    selectedCamping: 0,
    trimester: {
      id: 0,
      status: "",
      initDate: "",
      endDate: "",
      name: ""
    },
    dataToSearch: {
      name: "ss",
      clave: "jh378",
      year: "",
      dateInit: new Date().toLocaleString('es-ES', {year: 'numeric', month: '2-digit', day: 'numeric'}),
      dateEnd: new Date().toLocaleString('es-ES', {year: 'numeric', month: '2-digit', day: 'numeric'})
    },
    date:{
      months: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"],
      weekDays: ["Lun", "Mar", "Mier", "Jue", "Vie", "Sab", "Dom"]
    },
    loader:{
      color: '#0b93d1',
      height: '15px',
      width: '5px',
      margin: '2px',
      radius: '2px',
      loading: false,
      size: "95px",
    },
    register: false,
    yearSelected: new Date().getFullYear(),
    year: new Date().getFullYear(),
  },
  watch: {
    register: function(){
      (this.register) ? (this.trimester.id = 0) : "Nothing"
    }
  },
  computed: {

  },
  created: function() {
      this.loader.loading = true
      this.$http.get('/authorization/campueses').then(response => {
        this.campuses = response.body.campus
        this.person = response.body.person.person
        this.loader.loading = false
      }, response => {
        console.log("Fail")
        console.log(response)
      })
      this.getCampaings()
  },
  methods:{
    addTrimester() {
      if(this.dataToSearch.name && this.dataToSearch.clave){
        this.loader.loading = true
        this.$http.post('/administration/save/trimester', this.dataToSearch ).then( response =>{
          console.log(response)
          this.loader.loading = false
          this.trimester = response.body.result
          this.register = false
        }, response => {
          console.log(response)
        })
      }
      else {
        this.$snotify.warning("Completa los datos", this.notifyOptions)
      }
    },
    saveGoal(goal, index) {
      console.log("Submit")
      goal.trimester = this.goals[0].trimester
      console.log(this.errors.first(`porcentage${index}`))
      if(!this.errors.has(`porcentage${index}`) && !this.errors.has(`registers${index}`) ){
        this.loader.loading = true
        this.$http.post('/administration/save/goal', goal).then(response => {
          console.log(response)
          this.loader.loading = false
        }, response => {
          console.log(response)
        })
      }
      else {
        if(this.errors.has(`porcentage${index}`))
          return this.$snotify.warning(this.errors.first(`porcentage${index}`), this.notifyOptions)
        this.$snotify.warning(this.errors.first(`registers${index}`), this.notifyOptions)
      }
     // if(goal.percentCommission < 99){
     //   this.loader.loading = true
     //   this.$http.post('/administration/save/goal', goal).then(response => {
     //     console.log(response)
     //     this.loader.loading = false
     //   }, response => {
     //     console.log(response)
     //   })
     // }
     // else {
     //   this.$snotify.warning("Porcentage maximo 99", this.notifyOptions)
     // }
    },
    getCampaing(){
      console.log("dkjghdjdih")
      this.trimester = this.trimesters[this.selectedCamping]
      this.showInfoTrimester = (this.trimester) ? true : false
      this.showCampus = false
    },
    addGoals(id){
      this.loader.loading = true
      this.$http.get(`/administration/trimester/create/goals/${id}`).then(response => {
        console.log(response.body.goals)
        this.goals = response.body.goals
        this.showCampus = (this.goals) ? true : false
        this.loader.loading = false
      }, response => {
        console.log("Fail")
        console.log(response)
      })

    },
    getCampaings(){
      this.$http.get(`/administration/trimester/year/${this.yearSelected}`).then(response => {
        this.trimesters = response.body.trimester
        this.loader.loading = false
      }, response => {
        console.log("Fail")
        console.log(response)
      })
    },
    searhTrimester() {
    if(this.dataToSearch.name && this.dataToSearch.clave){
        this.loader.loading = true
        this.$http.get(`/administration/search/trimester/${this.dataToSearch.name}/${this.dataToSearch.clave}`).then( response =>{
          (response.body.trimester) ? (this.paserDates(response)) : (this.register = true)
          this.loader.loading = false
        }, response => {
          console.log(response)
        })
      }
    else {
      this.$snotify.warning("Completa los datos", this.notifyOptions)
    }
    },
    paserDates(response){
      this.register = false
      this.trimester = response.body.trimester
      this.trimester.initDate = this.trimester.initDate.replace(/-/g, "/").replace(/T+(\w|:|.)+/, "")
      this.trimester.endDate = this.trimester.endDate.replace(/-/g, "/").replace(/T+(\w|:|.)+/, "")
    },
    deleteById(id){
      this.loader.loading = true
      this.$http.post('/administration/delete/trimester', {id: id} ).then( response =>{
        console.log(response)
        this.loader.loading = false
        this.trimester.id = 0
        this.$snotify.info("Borrado Exitoso!", this.notifyOptions)
        this.register = false
      }, response => {
        console.log(response)
      })
    }
  },
  mounted() {
  },
  components: {
    RingLoader: VueSpinner.RingLoader,
    DatePick: VueDatePick
  },
  filters: {
		removeExtendTime(time) {
			return time.replace(/T+(\w|:|.)+/, "")
		}
	},
})