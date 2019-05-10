// Define a new component called button-counter
Vue.component('template-search', {
  data: function () {
    return {
			count: 0
    }
	},
	created: function (){

	},
	methods: {

	},
	template: `
	<div class="row">
	<div class="col-lg-12">
			<h5 class="">Selecciona los parametros de búsqueda</h5>
	</div>
	<div class="col-lg-12">
			<form>
					<div class="form-row align-items-center">
						<div class="col-auto">
							<label class="sr-only" for="inlineFormInputGroup">ID Cordinador</label>
							<div class="input-group mb-2">
								<div class="input-group-prepend">
									<div class="input-group-text"><i class="fa fa-user" aria-hidden="true"></i></div>
								</div>
								<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="ID Cordinador">
							</div>
						</div>
						<div class="col-auto">
							<label class="sr-only" for="inlineFormInputGroup">ID Promotor</label>
							<div class="input-group mb-2">
								<div class="input-group-prepend">
									<div class="input-group-text"><i class="fa fa-user" aria-hidden="true"></i></div>
								</div>
								<input type="text" class="form-control" id="inlineFormInputGroup" placeholder="ID Promotor">
							</div>
						</div>
						<div class="col-auto">
							<button type="submit" class="btn btn-primary mb-2">Buscar</button>
						</div>
					</div>
				</form>
	</div>
</div>
	`
})