// Define a new component called button-counter
Vue.component('template-comission-search', {
	props: {
		loader: Object,
		dataToTable: {
			type: Array,
			required: true
		}
	},
  data: function () {
    return {
			searchData: {
				typeReport: "",
				period: "",
				campus: "",
				status: ""
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
	methods: {
		search(){
			console.log("Init Search ")
			this.myDataToTable = [1]
			this.$root.$emit('send_table', this.myDataToTable)
			console.log("Find Search")
		}
	},
	template: `
	<div class="row">
			<div class="col-md-2 col-lg-2">
					<label for="exampleInputName2">Tipo de reporte</label>
					<select id="tipo" class="form-control" v-model="searchData.typeReport">
						<option value="1">General</option>
						<option value="2">Detallado</option>
					</select>
	</div>
	<div class="col-md-2 col-lg-2">
			<label for="exampleInputName2">Periodo</label>
			<select class="form-control" v-model="searchData.period">
				<option>Periodo 1 - 1 de Enero al 15 de Enero 2017</option>
				<option>Periodo 1 - 1 de Enero al 15 de Enero 2017</option>
				<option>Periodo 1 - 1 de Enero al 15 de Enero 2017</option>
				<option>Periodo 1 - 1 de Enero al 15 de Enero 2017</option>
				<option>Periodo 1 - 1 de Enero al 15 de Enero 2017</option>
				<option>Periodo 1 - 1 de Enero al 15 de Enero 2017</option>
			</select>
</div>
<div class="col-md-2 col-lg-2">
		<label for="exampleInputName2">Campus</label>
		<select class="form-control" v-model="searchData.campus">
		 <option value=""></option>
		 <option v-for="(k, v) in campuses" v-bind:value="v">
		 	{{ k }}
	 		</option>
		</select>
</div>
<div class="col-md-2 col-lg-2">
		<label for="exampleInputName2">Estatus</label>
		<select class="form-control" v-model="searchData.status">
			<option>Por Autorizar</option>
			<option>Autorizado</option>
			<option>Rechazado</option>
		</select>
</div>
<div class="col-md-2 col-lg-2">
		<button @click="search" style="margin-top:25px;" href="" class="btn btn-block btn-sm btn-primary"><i class="fa fa-search" aria-hidden="true"></i> Buscar</button>
	</div>
	</div>
	`
})