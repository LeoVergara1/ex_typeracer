// Define a new component called button-counter
Vue.component('template-comission-search', {
	props: {
		loader: Object
	},
  data: function () {
    return {
			searchData: {
				user: ""
			},
			alertModel:{
			},
			responses: {
				foundInBanner: false
			}
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

	},
	template: `
	<div class="row">
			<div class="col-md-2 col-lg-2">
					<label for="exampleInputName2">Tipo de reporte</label>
					<select id="tipo" class="form-control">
					 <option value="0"></option>
						<option value="1">General</option>
						<option value="2">Detallado</option>
					</select>
	</div>
	<div class="col-md-2 col-lg-2">
			<label for="exampleInputName2">Periodo</label>
			<select class="form-control">
			 <option value=""></option>
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
		<select class="form-control">
		 <option value=""></option>
			<option>CMX</option>
			<option>TOL</option>
			<option>MER</option>
			<option>LEO</option>
			<option>PCH</option>
			<option>CHI</option>
		</select>
</div>
<div class="col-md-2 col-lg-2">
		<label for="exampleInputName2">Estatus</label>
		<select class="form-control">
		 <option value=""></option>
			<option>Autorizado</option>
			<option>Denegado</option>
		</select>
</div>
<div class="col-md-2 col-lg-2">
		<a style="margin-top:25px;" href="" class="btn btn-block btn-sm btn-primary"><i class="fa fa-search" aria-hidden="true"></i> Buscar</a>
	</div>
	</div>
	`
})