var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!',
    person: Object,
    headerBgVariant: "white",
    headerTextVariant: 'dark',
    promoters: Array,
    enableEdit: true,
    listPromoterToUser: [],
    trimester: {
      id: 0,
      status: "",
      initDate: "",
      endDate: "",
      name: ""
    },
    dataToSearch: {
      name: "",
      clave: "",
      year: "",
      claveWihtoutPeriod: "",
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
    trimesters: [],
    register: false,
    showCampags: false,
    period: 20
  },
  watch: {
    register: function(){
      (this.register) ? (this.trimester.id = 0) : "Nothing"
    }
  },
  computed: {

  },
  created: function() {
        let container = document.getElementById("app")
    container.classList.remove("display_current")
      this.loader.loading = true
      this.$http.get('/authorization/campueses').then(response => {
        this.campuses = response.body.campus
        this.person = response.body.person.person
        this.loader.loading = false
      }, response => {
        console.log("Fail")
        console.log(response)
      })
  },
  methods:{
    dateExpires(date){
      let parserDate = date.replace(/T+(\w|:|.)+/, "")
      parserDate = new Date(parserDate.split("-"))
      return parserDate < new Date()
    },
    addTrimester() {
      if(this.dataToSearch.name && this.dataToSearch.claveWihtoutPeriod){
        this.loader.loading = true
        this.dataToSearch.clave = this.period + this.dataToSearch.claveWihtoutPeriod
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
    searhTrimester() {
      this.register = false
      this.dataToSearch.clave = this.period + this.dataToSearch.claveWihtoutPeriod
      if(this.dataToSearch.claveWihtoutPeriod){
        this.findOne()
      }
      else {
        this.findAll()
      }
    },
    findOne(){
      this.loader.loading = true
      this.$http.get(`/administration/search/trimester/notName/${this.dataToSearch.clave}`).then( response =>{
        (response.body.trimester) ? (this.trimester = response.body.trimester) : (this.register = true)
        this.loader.loading = false
        this.showCampags = false
      }, response => {
        console.log(response)
      })
    },
    findAll(){
      this.loader.loading = true
      this.$http.get(`/administration/trimester/all`).then( response =>{
        console.log(response.body)
        this.trimesters = response.body.trimesters
        this.loader.loading = false
        this.showCampags = true
        this.trimester.id = 0
      }, response => {
        console.log(response)
      })
    },
    updateTrimester(event, trimester){
      console.log(event.target)
      console.log(trimester)
      this.loader.loading = true
      this.$http.post('/administration/update/trimester', trimester ).then( response =>{
        console.log(response)
        this.loader.loading = false
      }, response => {
        console.log(response)
      })
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
    this.$root.$on('bv::modal::hide', (bvEvent, modalId) => {
      this.loader.loading = true
      this.getCoordinators()
    })
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