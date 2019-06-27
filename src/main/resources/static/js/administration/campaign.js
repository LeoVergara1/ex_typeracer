var app = new Vue({
  el: '#app',
  data: {
    notifyOptions: {
      timeout: 9000,
      showProgressBar: true,
      closeOnClick: false,
      pauseOnHover: true,
      position: 'rightTop',
      helperNotificationCycle: true
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
    year: new Date().getFullYear(),
    lastYear: new Date().getFullYear() -1,
    nextYear: new Date().getFullYear() +1,
    campaigns: [],
  },
  watch: {
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
      this.$http.get(`/administration/campaign/all/${this.year}`).then(response => {
        console.log(response)
        this.campaigns = response.body.campaigns
        this.loader.loading = false
      }, response => {
        console.log("Fail")
        console.log(response)
      })
  },
  methods:{
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