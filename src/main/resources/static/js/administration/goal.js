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
    register: false
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
    if(this.dataToSearch.name && this.dataToSearch.clave){
        this.loader.loading = true
        this.$http.get(`/administration/search/campaign/${this.dataToSearch.name}/${this.dataToSearch.clave}`).then( response =>{
          (response.body.campaign) ? (this.paserDates(response)) : (this.register = true)
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