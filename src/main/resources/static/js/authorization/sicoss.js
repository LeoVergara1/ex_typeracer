var app = new Vue({
	el: '#app',
	data: {
    date:{
      selectInit: new Date().toLocaleString('es-ES', {year: 'numeric', month: '2-digit', day: 'numeric'}),
      selectFin: new Date().toLocaleString('es-ES', {year: 'numeric', month: '2-digit', day: 'numeric'}),
      months: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"],
			weekDays: ["Lun", "Mar", "Mier", "Jue", "Vie", "Sab", "Dom"],
			campusSelected: "CMX"
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
		campuses: Object,
		person: Object,
		groups: {}
	},
	created: function(){
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
	methods: {
		getSicoss() {
			this.loader.loading = true
			this.$http.post('/authorization/sicoss', this.date).then(response => {
				console.log(response.body);
				this.groups = response.body.groups
				this.loader.loading = false
			}, response => {
				console.log("Fail")
				console.log(response)
			})
		},
		generateExcel(k){
			console.log("Inicia Construcción Excel")
      const ws_data = [
        ["CLAVE EMPLEADO", "TIPO DE NÓMINA", "CLAVE NÓMINA",	"CONCEPTO",	"FECHA DE MOVIMIENTO",	"DEPARTAMENTO",	"REFERENCA 2",	"DATO",	"IMPORTE",	"SALDO",	"NÓMINA ABIERTA"],
			];
			var wb = XLSX.utils.book_new()
			const workSheet = XLSX.utils.json_to_sheet(this.builtDataToExcel(this.groups[k]), {
        origin: "A2",
        Header: ['Column 1', 'Column 2', 'Column 3'],
        SkpHeader: true // skip the title line above
			})
			XLSX.utils.sheet_add_aoa(workSheet, ws_data, {
        Origin: 'A1'// Add content from A1
      });
      XLSX.utils.book_append_sheet(wb, workSheet, 'comisiones')
      XLSX.writeFile(wb, 'registroPagos.xlsx')
		},
		generatePDF(k){

		},
		builtDataToExcel(array){
      let dataToExcel = []
      array.forEach(element => {
       json = {
        "TR_NUMERO": element.pronoter ? element.pronoter.clavePromoter : 0,
        "NOMI_TIPONOMINA": 1,
        "NOMI_CLAVE": 0,
        "NOMI_CONCEPTO": 422,
        "NOMI_FECHA": element.authorization.fechaDePago,
        "NOMI_REFERENCIA": "Nose",
        "NOMI_REFERENCIA2": 0,
        "ID Coordinador": element.idCoordinador,
        "NOMI_DATO": element.nombreCoordinador,
        "NOMI_IMPORTE": element.authorization.pagoInicial,
				"NOMI_SALDO": 0,
				"NOMINA_ABIERTA": 1
       }
      dataToExcel.push(json)
      });
      return dataToExcel
		}
	},
	components: {
    RingLoader: VueSpinner.RingLoader,
    DatePick: VueDatePick
  },
})