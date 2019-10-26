var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!',
    notifyOptions: {
      timeout: 9000,
      showProgressBar: true,
      closeOnClick: false,
      pauseOnHover: true,
      position: 'rightTop',
      helperNotificationCycle: true
    },
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
    authorizationComissionCorrientes: [],
    period_banner: 20,
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
    authorizationToDenegate: {
      authorization: {},
      send: {},
      indexList: ""
    },
    authorizationsCrecentGroups: []
  },
  computed: {
    totalCommission() {
      let cost = 0
      this.alumns.forEach(element => {
         cost = Number(element.comision) + cost
      });
      return cost.toFixed(2)
    },
    totalCommissionCrecent(){
      let cost = 0
      this.authorizationsCrecent.forEach(element => {
         cost = Number(element.comision) + cost
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
    calculateComissionByPromoter (commissions, type){
      console.log(commissions)
      if (type == "corriente") {
        this.alumns = commissions
      }
      else {
        this.authorizationsCrecent = commissions
      }
      let cost = 0
      commissions.forEach(element => {
         cost = Number(element.comision) + cost
      });
      this.totalCommissionPromoter =  cost.toFixed(2)
    },
    showAllCommissions(type){
      this.alumns = (type == 'corriente') ? (this.authorizationComissionCorrientes) : this.authorizationsCrecent
    },
    setAuthorization(alumno, { target }){
      (target.checked) ? alumno.statusRector = true : alumno.statusRector = false
      console.log(target.checked)
    },
    setAuthorizationCrecent(alumno, { target }){
      (target.checked) ? alumno.statusRector = true: alumno.statusRector = false
      console.log(target.checked)
    },
    showModalTooDenegate(authorization, functionSend, index){
      this.authorizationToDenegate.authorization = authorization
      this.authorizationToDenegate.send = functionSend
      this.authorizationToDenegate.indexList = index
      this.$bvModal.show("modal-denegate")
    },
    executeDenegateFromModal(){
      this.authorizationToDenegate.send(this.authorizationToDenegate.authorization)
      this.$bvModal.hide("modal-denegate")
      if ( this.authorizationToDenegate.send == this.denegateComissions ){
        this.alumns.splice(this.authorizationToDenegate.indexList, 1)
      }
      else {
        this.authorizationsCrecent.splice(this.authorizationToDenegate.indexList, 1)
      }
    },
    denegateAuthorizationCrecent(alumno){
      console.log(alumno)
      this.loader.loading = true
      alumno.autorizadoDirector = "RECHAZADA"
      this.$http.post(`/authorization/denegateCrecent`, alumno).then(response => {
        console.log(response.body);
        this.loader.loading = false
      }, response => {
        console.log(response)
      })
    },
    sendAuthorizationRector(){
      let listTosend = this.alumns.filter(alumno => alumno.statusRector == true)
      console.log(listTosend)
      this.loader.loading = true
      this.$http.post(`/authorization/sendAuthorizationRector`, listTosend).then(response => {
        console.log(response.body);
        this.alumns = this.alumns.filter(alumno => alumno.statusRector  != true)
        this.resetChecksBoxes()
        this.loader.loading = false
      }, response => {
        console.log("Fail")
        console.log(response)
      })
    },
    sendAuthorizationCrecentRector(){
      let listTosend = this.authorizationsCrecent.filter(alumno => alumno.statusRector == true)
      console.log(listTosend)
      this.loader.loading = true
      this.$http.post(`/authorization/sendAuthorizationCrecentRector`, listTosend).then(response => {
        console.log(response.body);
        this.authorizationsCrecent = this.authorizationsCrecent.filter(alumno => alumno.statusRector != true)
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
    denegateComissions(authorizationComission){
      this.loader.loading = true
      authorizationComission.autorizadoDirector = "RECHAZADA"
      this.$http.post(`/authorization/denegateComissions`, authorizationComission).then(response => {
        console.log(response.body);
        this.loader.loading = false
      }, response => {
        console.log("Fail")
        console.log(response)
      })
    },
    getCalculationRector() {
      this.loader.loading = true
      this.$http.post(`/authorization/getCalculationRector`, {
        campus: this.campusSelected,
        initDate: this.date.selectInit,
        finDate: this.date.selectFin,
        period: this.period_banner
      }).then(response => {
        console.log(response.body);
        this.loader.loading = false
        this.alumns = response.body.out_comisiones
        this.authorizationComissionCorrientes = response.body.out_comisiones
        this.comissionsGroups = response.body.comissionsGroups
        this.authorizationsCrecent = response.body.calculationCrecent
        this.authorizationsCrecentGroups = response.body.groupsCalculations
        if(response.body.withoutTrimester) { this.$snotify.warning("La busqueda no tiene trimestres", this.notifyOptions) }
        if(response.body.moreThanTwo) { this.$snotify.warning("La busqueda incluye más de un trimestre", this.notifyOptions) }
      }, response => {
        console.log("Fail")
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
    validPercent(percent){
      if(!percent){
        return "-"
      }
      return percent
    },
    removeDecimal(number){
      return Number(number).toFixed(2)
    },
    removeExtendTime(time) {
      if(!time)
        return time
			return time.replace(/T+(\w|:|.)+/, "")
    },
  }
})