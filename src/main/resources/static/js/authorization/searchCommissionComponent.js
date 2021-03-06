Vue.component('template-comission-search', {
	props: {
		loader: Object,
		dataToTable: {
			type: Array,
			required: true
		},
		date: {
			type: Object,
			required: true
		}
	},
  data: function () {
    return {
			allCampuses: false,
			searchData: {
				typeReport: "Detallado",
				selectFin: this.date.selectFin,
				selectInit: this.date.selectInit,
				campus: "CMX",
				status: "POR_AUTORIZAR",
				typeComission: "Corrientes"
			},
			myDataToTable: this.dataToTable,
			alertModel:{
			},
			responses: {
				foundInBanner: false
			},
			campuses: []
    }
	},
	created: function (){
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
	watch: {
		'searchData.typeReport': function() {
			this.$root.$emit('send_report', this.searchData.typeReport)
		},
		'searchData.status': function(val){
			console.log(val)
			if(this.searchData.status == "POR_AUTORIZAR"){
				this.allCampuses = false
			}
			else{
				this.allCampuses = true
			}
		}
	},
	methods: {
		search(){
			console.log("Init Search ")
			this.myDataToTable = [1]
      this.loader.loading = true
			this.$http.post(`/authorization/query/searchCommisions`, this.searchData).then(response => {
        console.log(response.body);
        this.loader.loading = false
				this.$root.$emit('send_table', response.body.commissions, response.body.groups, this.searchData)
      }, response => {
        console.log("Fail")
        console.log(response)
      })
			console.log("Find Search")
		},
	},
	components: {
    DatePick: VueDatePick
	},
	template: `
	<div class="row">
			<div class="col-md-2 col-lg-2">
					<label for="exampleInputName2">Tipo de reporte</label>
					<select id="tipo" class="form-control" v-model="searchData.typeReport">
						<option value="General">General</option>
						<option value="Detallado">Detallado</option>
					</select>
	</div>
	<div class="col-lg-2">
	<label for="selectCampus">Fecha inicio</label>
	<date-pick v-model="searchData.selectInit" :months="date.months" :weekdays="date.weekDays" :format="'DD/MM/YYYY'"></date-pick>
</div>
<div class="col-lg-2">
	<label for="selectCampus">Fecha Fin</label>
	<date-pick v-model="searchData.selectFin" :months="date.months" :weekdays="date.weekDays" :format="'DD/MM/YYYY'"></date-pick>
</div>
<div class="col-md-2 col-lg-2">
		<label for="exampleInputName2">Estatus</label>
		<select class="form-control" v-model="searchData.status">
			<option value="POR_AUTORIZAR">Por Autorizar</option>
			<option value="AUTORIZADO">Autorizado</option>
			<option value="RECHAZADA">Rechazado</option>
		</select>
</div>
<div class="col-md-2 col-lg-2">
	<label for="exampleInputName2">Campus</label>
	<select class="form-control" v-model="searchData.campus">
	 <option value="TODOS" v-if="allCampuses">Todos</option>
	 <option v-for="(k, v) in campuses" v-bind:value="v">
		 {{ k }}
		 </option>
	</select>
</div>
<div class="col-md-2 col-lg-2">
		<div class="btn-group" style="margin-top: 15%">
				<button  @click="search" type="button" class="btn btn-block btn-sm btn-primary"><i class="fa fa-search" aria-hidden="true"></i> {{searchData.typeComission}}</button>
				<button type="button" class="btn btn-primary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown">
					<span class="caret"></span>
				</button>
				<div class="dropdown-menu">
					<a class="dropdown-item" @click="searchData.typeComission = 'Corrientes'">Corrientes</a>
					<a class="dropdown-item" @click="searchData.typeComission = 'Crecientes'">Crecientes</a>
				</div>
			</div>
	</div>
	</div>
	`
})