// Define a new component called button-counter
Vue.component('template-promoter', {
	props: {
			campus: Object
	},
  data: function () {
    return {
			count: 0,
			listAssociation: []
    }
	},
	created: function (){
		console.log("Instancia creada de componente")
		this.$http.get('/administration/data/association').then(response => {
			// get body data
			this.someData = response.body;
			console.log(response.body);
			this.campus.list = response.body.campus
			this.listAssociation = response.body.listAssociation
			console.log(response)
			console.log("regreso")
		}, response => {
			console.log("regreso mal")
			console.log(response)
			// error callback
		});
	},
	methods: {
		getAssociation: function(){

		}
	},
	template: `
	<div class="row">
			<div class="col-lg-12">
					<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">Nombre Promotor</th>
									<th scope="col">ID Promotor</th>
									<th scope="col">Puesto</th>
									<th scope="col">Clave de empleado</th>
									<th scope="col">Campus</th>
									<th scope="col">ID Coordinador</th>
									<th scope="col">Nombre Coordinador</th>
									<th scope="col">Clave de empleado</th>
									<th scope="col">Habilitado</th>
									<th scope="col"></th>
								</tr>
							</thead>
							<tbody>
								<tr v-for="association in listAssociation">
									<th scope="row">{{association.promoterName}}</th>
									<td>{{association.idPromoter}}</td>
									<td>{{association.jobPromoter}}</td>
									<td>{{association.clavePromoter}}</td>
									<td>{{association.campusDesc}}</td>
									<td>{{association.idCoordinater}}</td>
									<td>{{association.coordinaterName}}</td>
									<td>{{association.claveCoordinater}}</td>
									<td>{{association.relationActive}}</td>
									<td><a name="" id="" class="btn btn-primary" href="#" role="button">Actualizar</a></td>
								</tr>
							</tbody>
						</table>
			</div>
	</div>
	`
})