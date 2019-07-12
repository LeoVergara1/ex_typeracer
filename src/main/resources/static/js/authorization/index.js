var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!',
    username: document.getElementsByName("_username")[0].content,
    person: Object,
    headerBgVariant: "white",
    headerTextVariant: 'dark',
    promoters: Array,
    campuses: Object,
    campusSelected: "CMX",
    alumns: [],
    listPromoterToUser: [],
    comissionsGroups: [],
    totalCommissionPromoter: 0,
    authorizationsCrecent: [],
    period: 20,
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
  },
  methods:{
    calculateComissionByPromoter (commissions){
      let cost = 0
      commissions.forEach(element => {
         cost = element.comision + cost
      });
      this.totalCommissionPromoter =  cost.toFixed(2)
    },
    setAuthorization(alumno, { target }){
      (target.checked) ? alumno.autorizadoDirector = "AUTORIZADO" : alumno.autorizadoDirector = "POR_AUTORIZAR"
      console.log(target.checked)
    },
    denegateAuthorization(alumno){
      console.log(alumno)
      this.loader.loading = true
      this.$http.post(`/authorization/denegate`, alumno).then(response => {
        console.log(response.body);
        this.loader.loading = false
      }, response => {
        console.log(response)
      })
    },
    sendAuthorization(){
      let listTosend = this.alumns.filter(alumno => alumno.autorizadoDirector == "AUTORIZADO")
      console.log(listTosend)
      this.loader.loading = true
      this.$http.post(`/authorization/sendAuthorization`, {
        listAuthorization: listTosend
      }).then(response => {
        console.log(response.body);
        this.alumns = this.alumns.filter(alumno => alumno.autorizadoDirector != "AUTORIZADO")
        this.resetChecksBoxes()
        this.loader.loading = false
      }, response => {
        console.log("Fail")
        console.log(response)
      })
    },
    resetChecksBoxes(){
      let checkboxes = document.getElementsByClassName("check_element")
      for(var i=0, n=checkboxes.length;i<n;i++) {
        checkboxes[i].checked = false;
      }
    },
    getCalculation() {
      this.loader.loading = true
      this.$http.post(`/authorization/getCalculation`, {
        campus: this.campusSelected,
        initDate: this.date.selectInit,
        finDate: this.date.selectFin,
        period: this.period
      }).then(response => {
        console.log(response.body);
        this.loader.loading = false
        this.alumns = response.body.out_comisiones
        this.comissionsGroups = response.body.comissionsGroups
        this.authorizationsCrecent = response.body.calculationCrecent
      }, response => {
        console.log("Fail")
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
    }
  }
})