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
    campaign: {
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
    campaigns: [],
    register: false,
    showCampags: false
  },
  watch: {
    register: function(){
      (this.register) ? (this.campaign.id = 0) : "Nothing"
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
  },
  methods:{
    dateExpires(date){
      let parserDate = date.replace(/T+(\w|:|.)+/, "")
      parserDate = new Date(parserDate.split("-"))
      return parserDate < new Date()
    },
    addCampaign() {
      if(this.dataToSearch.name && this.dataToSearch.clave){
        this.loader.loading = true
        this.$http.post('/administration/save/campaign', this.dataToSearch ).then( response =>{
          console.log(response)
          this.loader.loading = false
          this.campaign = response.body.result
          this.register = false
        }, response => {
          console.log(response)
        })
      }
      else {
        this.$snotify.warning("Completa los datos", this.notifyOptions)
      }
    },
    searhCampaign() {
      this.register = false
      if(this.dataToSearch.clave){
        this.findOne()
      }
      else {
        this.findAll()
      }
    },
    findOne(){
      this.loader.loading = true
      this.$http.get(`/administration/search/campaign/notName/${this.dataToSearch.clave}`).then( response =>{
        (response.body.campaign) ? (this.campaign = response.body.campaign) : (this.register = true)
        this.loader.loading = false
        this.showCampags = false
      }, response => {
        console.log(response)
      })
    },
    findAll(){
      this.loader.loading = true
      this.$http.get(`/administration/campaign/all`).then( response =>{
        console.log(response.body)
        this.campaigns = response.body.campaigns
        this.loader.loading = false
        this.showCampags = true
        this.campaign.id = 0
      }, response => {
        console.log(response)
      })
    },
    updateCampaign(event, campaign){
      console.log(event.target)
      console.log(campaign)
      this.loader.loading = true
      this.$http.post('/administration/update/campaign', campaign ).then( response =>{
        console.log(response)
        this.loader.loading = false
      }, response => {
        console.log(response)
      })
    },
    paserDates(response){
      this.register = false
      this.campaign = response.body.campaign
      this.campaign.initDate = this.campaign.initDate.replace(/-/g, "/").replace(/T+(\w|:|.)+/, "")
      this.campaign.endDate = this.campaign.endDate.replace(/-/g, "/").replace(/T+(\w|:|.)+/, "")
    },
    deleteById(id){
      this.loader.loading = true
      this.$http.post('/administration/delete/campaign', {id: id} ).then( response =>{
        console.log(response)
        this.loader.loading = false
        this.campaign.id = 0
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