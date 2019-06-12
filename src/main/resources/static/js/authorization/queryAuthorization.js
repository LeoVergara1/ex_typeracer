var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!',
    username: "",
    person: Object,
    headerBgVariant: "white",
    headerTextVariant: 'dark',
    promoters: Array,
    campuses: Object,
    campusSelected: "CMX",
    alumns: [],
    listPromoterToUser: [],
    date: '2019/01/01',
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
    }
  },
  computed: {
    totalCommission() {
      let cost = 0
      this.alumns.forEach(element => {
         cost = element.comision + cost
      });
      return cost.toFixed(2)
    },
    period(){
      let options = { year: 'numeric', month: 'long', day: 'numeric' }
      let dateParts = this.date.selectInit.split("/")
      console.log(dateParts)
      let init = new Date(Date.parse(`${dateParts[1]}/${dateParts[0]}/${dateParts[2]}`)).toLocaleDateString("es-ES", options)
      dateParts = this.date.selectFin.split("/")
      let fin = new Date(Date.parse(`${dateParts[1]}/${dateParts[0]}/${dateParts[2]}`)).toLocaleDateString("es-ES", options)
      console.log(init)
      console.log(fin)
      return `${init} al ${fin}`
    }
  },
  created: function() {

  },
  methods:{
    },
    getCalculation() {
      this.loader.loading = true
      this.$http.post(`/authorization/getCalculation`, {
        campus: this.campusSelected,
        initDate: this.date.selectInit,
        finDate: this.date.selectFin
      }).then(response => {
        console.log(response.body);
        this.loader.loading = false
        this.alumns = response.body.out_comisiones
      }, response => {
        console.log("Fail")
        console.log(response)
      })

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
    }
  }
})