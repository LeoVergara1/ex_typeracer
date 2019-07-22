var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!',
    username: document.getElementsByName("_username")[0].content,
    campuses: Object,
    campusSelected: "CMX",
    campings: [],
    periodSelected: 0,
    date:{
      selectInit: new Date().toLocaleString('es-ES', {year: 'numeric', month: '2-digit', day: 'numeric'}),
      selectFin: new Date().toLocaleString('es-ES', {year: 'numeric', month: '2-digit', day: 'numeric'}),
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
  },
  computed: {

  },
  created: function() {
    let container = document.getElementById("app")
    container.classList.remove("display_current")
    this.loader.loading = true
    this.$http.get('/authorization/campueses').then(response => {
      console.log(response.body);
      this.campuses = response.body.campus
      this.person = response.body.person.person
      this.loader.loading = false
    }, response => {
      console.log("Fail")
      console.log(response)
    })
    this.$http.get('/sicoss/campings').then(response => {
      console.log(response.body);
      this.campings = response.body
      this.loader.loading = false
    }, response => {
      console.log("Fail")
      console.log(response)
    })
  },
  methods:{
    processSicoss(){
      this.$http.post(`/sicoss/porcossSicoss`, this.campings[this.periodSelected]).then(response => {
        console.log(response.body);
        this.loader.loading = false
        this.$bvModal.show('modal-download')
      }, response => {
        console.log(response)
      })
    }
  },
  mounted() {
    this.$root.$on('bv::modal::hide', (bvEvent, modalId) => {
    })
  },
  components: {
    RingLoader: VueSpinner.RingLoader,
    DatePick: VueDatePick
  },
  filters: {
		getDescriptionToRol: function (value){
			let map = {
				"DIR_CAMPUS": "Director de campus",
				"ADMIN_JP": "Administrador",
				"COORD_MERCADOTECNIA_CORP": "Coordinador",
				"NOMINA_ADMIN_SICOSS": "Sicoss de nomina",
				"JEFE_PROMOCION": "Jefe de promoción",
				"Promotor": "Promotor",
				"PROMOCIÓN": "Promoción"
			}
			return map[value] ? map[value] : value
    },
    percentDiscount(alumno){
      let percent = alumno.totalDescuentos * 100
      return (percent/alumno.valorContratoReal.toFixed(3))
    },
    parserCamping(camping){
      return `${camping.name} ${camping.initDate.replace(/T+(\w|:|.)+/, "")} - ${camping.endDate.replace(/T+(\w|:|.)+/, "")}`
    }
  }
})