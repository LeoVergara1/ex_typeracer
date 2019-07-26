var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!',
    username: document.getElementsByName("_username")[0].content,
    campuses: Object,
    campusSelected: "CMX",
    query:{
      selectInit: new Date().toLocaleString('es-ES', {year: 'numeric', month: '2-digit', day: 'numeric'}),
      selectFin: new Date().toLocaleString('es-ES', {year: 'numeric', month: '2-digit', day: 'numeric'}),
    },
    date:{
      months: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"],
      weekDays: ["Lun", "Mar", "Mier", "Jue", "Vie", "Sab", "Dom"]
    },
    notifyOptions: {
      timeout: 9000,
      showProgressBar: true,
      closeOnClick: false,
      pauseOnHover: true,
      position: 'rightTop',
      helperNotificationCycle: true
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
    listSicoss: [],
    waitingExcel: false,
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
  },
  methods:{
    getCommissionsToEmployee(){
      this.$http.get(`/sicoss/employee/${this.query.clave}/`, {params: {initDate: this.query.selectInit, endDate: this.query.selectFin}}).then(response => {
        console.log(response.body);
        this.loader.loading = false
        this.listSicoss = response.body
        if(this.listSicoss.length == 0){
          this.$snotify.warning("No se encontro infromación", this.notifyOptions)
        }
        else{
          this.$bvModal.show('modal-download')
        }
      }, response => {
        console.log("Fail")
        console.log(response)
      }) 
    },
    generateExcel(){
      this.waitingExcel = true
			console.log("Inicia Construcción Excel")
      const ws_data = [
        ["CLAVE EMPLEADO", "TIPO DE NÓMINA", "CLAVE NÓMINA",	"CONCEPTO",	"FECHA DE MOVIMIENTO",	"DEPARTAMENTO",	"REFERENCA 2",	"DATO",	"IMPORTE",	"SALDO",	"NÓMINA ABIERTA"],
			];
			var wb = XLSX.utils.book_new()
			const workSheet = XLSX.utils.json_to_sheet(this.builtDataToExcel(this.listSicoss), {
        origin: "A2",
        Header: ['Column 1', 'Column 2', 'Column 3'],
        SkpHeader: true // skip the title line above
			})
			XLSX.utils.sheet_add_aoa(workSheet, ws_data, {
        Origin: 'A1'// Add content from A1
      });
      XLSX.utils.book_append_sheet(wb, workSheet, 'comisiones')
      XLSX.writeFile(wb, 'sicoss.xlsx')
      this.waitingExcel = false
      console.log("Termina Construcción Excel")
    },
    builtDataToExcel(array){
      let dataToExcel = []
      array.forEach(element => {
       json = {
        "TR_NUMERO": element.claveEmployee ? element.claveEmployee : 0,
        "NOMI_TIPONOMINA": element.typePaysheet,
        "NOMI_CLAVE": element.clavePaysheet,
        "NOMI_CONCEPTO": element.concept,
        "NOMI_FECHA": element.dateMovenment,
        "NOMI_REFERENCIA": element.reference1,
        "NOMI_REFERENCIA2": element.reference2,
        "NOMI_DATO": element.dataPayhseet,
        "NOMI_IMPORTE": element.importe,
				"NOMI_SALDO": element.salary,
				"NOMINA_ABIERTA": element.typePaysheet
       }
      dataToExcel.push(json)
      });
      return dataToExcel
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
    }
  }
})