const app = new Vue({
  el: '#app',
  data: {
		message: 'Hello Vue!',
		typeReport: "General",
    username: "",
    person: Object,
    headerBgVariant: "white",
    headerTextVariant: 'dark',
    promoters: Array,
    campuses: Object,
    campusSelected: "CMX",
    alumns: [],
    listPromoterToUser: [],
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
		commissionsToTable: [],
		dataToTable: [],
    groups: [],
    searchData: {}
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
    // eslint-disable-next-line no-undef
    console.log(XLSX)
  },
  methods:{
    onexport() {
      this.loader.loading = true
      const ws_data = [
        [" ", " ", " ", "Administración de Comisiones ", " ", " ", " "],
        [" ", " ", " ", " ", " ", " ", "Fecha de Actualización: ", "12 junio 2019"],
        ["Campus", this.searchData.campus],
        ["Fecha de inicio: ", this.searchData.selectInit],
        ["Fecha Fin", this.searchData.selectFin]
      ];
      const workSheetTitle = XLSX.utils.aoa_to_sheet(ws_data)
      //var animalWS = XLSX.utils.json_to_sheet(this.commissionsToTable)
      //var wb = XLSX.utils.book_new() // make Workbook of Excel
      var tbl = document.getElementById('tableCommissions');
      //var wb = XLSX.utils.table_to_book(tbl);
      var wb = XLSX.utils.book_new() // make Workbook of Excel
      //const workSheet = XLSX.utils.table_to_sheet(tbl, ws_data,{Origin:'A10'});
      //add Worksheet to Workbook
      //Workbook contains one or more worksheets
      //XLSX.utils.book_append_sheet(wb, animalWS, 'animals')
      let lisToExcel = (this.searchData.typeReport == 'General') ? this.prepareJSONtoExcel(this.commissionsToTable) : this.prepareJSONtoExcelGroup(this.groups)
      const workSheet = XLSX.utils.json_to_sheet(lisToExcel, {
        origin: "A10",
        Header: ['Column 1', 'Column 2', 'Column 3'],
        SkpHeader: true // skip the title line above
      })
      XLSX.utils.sheet_add_aoa(workSheet, ws_data, {
        Origin: 'A1'// Add content from A1
      });
      XLSX.utils.book_append_sheet(wb, workSheet, 'comisiones')
      XLSX.writeFile(wb, 'comissiones.xlsx')
      this.loader.loading = false
    },
    prepareJSONtoExcel(array) {
      let dataToExcel = []
      array.forEach(element => {
      let json = {
        "ID Promotor": element.idPromotor,
        "Nombre del Promotor": element.nombrePromotor,
        "Puesto": element.puesto,
        "ID Alumno": element.idAlumno,
        "Nombre": element.nombreAlumno,
        "Pago Inicial": element.pagoInicial,
        "Comisión": element.comision,
        "ID Coordinador": element.idCoordinador,
        "Nombre Coordinador": element.nombreCoordinador,
        "Período": element.periodo,
        "Fecha Pago": element.fechaDePago
       }
      dataToExcel.push(json)
      });
      return dataToExcel
    },
    prepareJSONtoExcelGroup(array) {
      let dataToExcel = []
      array.forEach(element => {
      let json = {
        "Puesto": element.job,
        "ID": element.idPromoter,
        "Nombre": element.namePromoter,
        "Periodo": `${this.searchData.selectInit} - ${this.searchData.selectFin}`,
        "Total inscritos": element.numberStudents,
        "Comisión": element.comission,
       }
      dataToExcel.push(json)
      });
      return dataToExcel
    }
  },
  mounted() {
    this.$root.$on('bv::modal::hide', (bvEvent, modalId) => {
      this.loader.loading = true
      this.getCoordinators()
		})
		this.$root.$on('send_table',(table, groups, searchData) => {
			this.commissionsToTable = table
      this.groups = groups
      this.searchData = searchData
		})
		this.$root.$on('send_report',(report) => {
			this.typeReport = report
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