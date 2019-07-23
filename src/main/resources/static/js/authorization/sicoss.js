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
		groups: {},
		dataToTable: [],
		specialElementHandlers: {
			'.no-export': function(element, renderer) {
				return true;
			}
		}
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
		sleep: (milliseconds) => {
			return new Promise(resolve => setTimeout(resolve, milliseconds))
		},
		generatePDF(k){
			this.dataToTable = this.groups[k]
			console.log(this.groups[k])
			this.sleep(1).then(() => { //Because render in html is liitle more low
				//do stuff
				let doc = new jsPDF({
					orientation: 'landscape'
				});
				doc.autoTable({html: '#tableToPdf'});
				let margins = {
					top: 5,
					bottom: 5,
					left: 3,
					width: 600
				};
				doc.save('comisiones.pdf');
			})
		},
		builtDataToExcel(array){
      let dataToExcel = []
      array.forEach(element => {
       json = {
        "TR_NUMERO": element.promoter ? element.promoter.clavePromoter : 0,
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
	filters: {
		removeExtendTime(time) {
			return time.replace(/T+(\w|:|.)+/, "")
		},
		parserDate(time){
			let options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' , hour12: true, hourCycle: 'h12', hour: 'numeric'};
			time = new Date(time)
			return time.toLocaleDateString('es-ES', options)
		}
	},
	components: {
    RingLoader: VueSpinner.RingLoader,
    DatePick: VueDatePick
  },
})