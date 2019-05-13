// Define a new component called button-counter
Vue.component('template-search', {
  data: function () {
    return {
			count: 0,
			searchData: {
				promoter: "",
				coordinater: ""
			}
    }
	},
	created: function (){

	},
	methods: {
		association: function(){
			this.$http.post('/administration/search/association', this.searchData ).then(response => {
				// get body data
				this.someData = response.body;
				console.log(response.body);
				this.listAssociation = response.body.listAssociation
				console.log(response)
				console.log("regreso")
			}, response => {
				console.log("regreso mal")
				console.log(response)
				// error callback
			})
		}
	},
	template: `
	<div class="row">
	<div class="col-lg-12">
			<h5 class="">Selecciona los parametros de búsqueda</h5>
	</div>
	<div class="col-lg-12">
					<div class="form-row align-items-center">
						<div class="col-auto">
							<label class="sr-only" for="inlineFormInputGroup">ID Cordinador</label>
							<div class="input-group mb-2">
								<div class="input-group-prepend">
									<div class="input-group-text"><i class="fa fa-user" aria-hidden="true"></i></div>
								</div>
								<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="ID Cordinador" v-model="searchData.coordinater">
							</div>
						</div>
						<div class="col-auto">
							<label class="sr-only" for="inlineFormInputGroup">ID Promotor</label>
							<div class="input-group mb-2">
								<div class="input-group-prepend">
									<div class="input-group-text"><i class="fa fa-user" aria-hidden="true"></i></div>
								</div>
								<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="ID Promotor" v-model="searchData.promoter">
							</div>
						</div>
						<div class="col-auto">
							<button @click="association()" class="btn btn-primary mb-2">Buscar</button>
						</div>
					</div>
	</div>
</div>
	`
})