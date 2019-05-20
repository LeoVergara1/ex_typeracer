// Define a new component called button-counter
Vue.component('template-register', {
	props:  {
		user: Object,
		campus: Object
	},
  data: function () {
    return {
			campusSelected: {},
    }
	},
	created: function (){
		console.log("Instancia creada de componente registro")
		console.log(this.campus)
	},
	methods: {

	},
	template: `
	<div class="row">
		<div class="col-lg-2">
				<label for="selectCampus">Campus</label>
				<div id="filter-campus">
					<select v-model="campusSelected" class="form-control">
						<!-- inline object literal -->
						<option v-for="(k, v) in campus.list" v-bind:value="v">
							{{ k }}
						</option>
					</select>
			</div>
		</div>
		<div class="col-lg-2">
			<label for="selectRoles">Rol</label>
			<div id="filter-roles">
			<select class="form-control filtersRolAndCampus" id="roleCode">
				<option disabled="disabled">Seleccione un rol</option>
				<option value="555">Administrador de Personal</option>
				<option value="556">Administrador-Comisiones</option>
				<option value="554">Autorizador Divisional</option>
				<option value="557">Jefe de Programa CE</option>
				<option value="558">Promotor-Comisiones</option>
			</select>
		</div>
		</div>
	</div>
	`
})