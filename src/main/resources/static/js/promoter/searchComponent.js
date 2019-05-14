// Define a new component called button-counter
Vue.component('template-search', {
  data: function () {
    return {
			count: 0,
			searchData: {
				user: ""
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
			<h5 class="">Registro</h5>
	</div>
	<div class="col-lg-12">
					<div class="form-row align-items-center">
						<div class="col-auto">
							<label class="sr-only" for="inlineFormInputGroup">ID Usuario</label>
							<div class="input-group mb-2">
								<div class="input-group-prepend">
									<div class="input-group-text"><i class="fa fa-user" aria-hidden="true"></i></div>
								</div>
								<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="ID Usuario" v-model="searchData.user">
							</div>
						</div>
						<div class="col-auto">
							<button @click="association()" class="btn btn-primary mb-2">Buscar</button>
						</div>
						<div class="col-auto">
							<div style="margin-bottom: 30px;padding-left: 40px;">
								<h4 class="block orange"><i class="fa fa-user" aria-hidden="true"></i></h4>
								<h4 class="block orange" style="margin-top:30px;">MARTINEZ GARCIA RODRIGO</h4>
								<h4 class="block blue">ID: M00021214</h4>
							</div>
						</div>
					</div>
	</div>
</div>

	`
})