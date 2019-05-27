// Define a new component called button-counter
Vue.component('template-register', {
	props:  {
		user: Object,
		campus: Object,
		notifyOptions: Object,
		loader: Object
	},
  data: function () {
    return {
			campusSelected: {},
			roleCode: 557,
			alert: {
				message: "",
				user: false,
				show: false
			},
			register: {
				campus: "",
				roleCode: "",
				rcreCode: ""
			},
			response: {
				register: false
			}
    }
	},
	created: function (){
		console.log("Instancia creada de componente registro")
		console.log(this.campus)
	},
	watch: {
		'user.person.username': 	function(){
			console.log("Hola...")
		}
	},
	computed: {
		disabledRegister: function(){
			if(this.register.campus && this.register.roleCode){
				return false
			}
			else {
				return true
			}
		},
		alertData: function() {
			if(this.user.person.profiles.length>0 && this.user.person.campuses.length > 0){
				this.helperToNotificationInWatch("El usuario ya fue registrado con los permisos")
				this.notifyOptions.helperNotificationCycle = false;
				return {message: "El usuario ya fue registrado con los permisos", user: false, 	show: false}
			}
			else if(this.user.person.profiles.length == 0 && this.user.person.campuses.length > 0){
				return {message: "", user: true, 	show: false}
			}
			else if(this.user.person.profiles.length > 0 && this.user.person.campuses.length == 0){
				return {message: "", user: true, 	show: false}
			}
			else {
				return {message: "", user: true, 	show: false}
			}
		}
	},
	methods: {
		deleteRol: function() {
			console.log("Deleting role")
			this.loader.loading = true
			this.$http.post('/administration/delete/roleAndCampus', this.user ).then(response => {
				console.log("Response ")
				console.log(response)
				console.log(response.body.statusRole)
				this.validatingSatatusResponse("Borrado Exitoso", response.body.statusRole)
				this.loader.loading = false
				this.user.person.userName = null
				}, response => {
			})
			return false
		},
		validatingSatatusResponse: function(message, response) {
			let map = {
				200: () => {this.$snotify.info(message, this.notifyOptions)},
				201: () => {this.$snotify.info(message, this.notifyOptions)},
				400: () => {this.$snotify.info("Hubo Un error en el proceso", this.notifyOptions)},
				404: () => {this.$snotify.error("Hubo Un error en el proceso", this.notifyOptions)}
			}
			map[response]()
		},
		helperToNotificationInWatch : function(message, cycle){
			if(this.notifyOptions.helperNotificationCycle){
				this.$snotify.warning(message, this.notifyOptions);
			}
		},
		saveRole: function () {
			let objectToSend = Object.assign(this.user, this.register)
			console.log(objectToSend)
			this.loader.loading = true
			this.$http.post('/administration/saveRolToPerson', objectToSend).then(response => {
				console.log("Response ")
				console.log(response)
				this.validatingSatatusResponse("Guardado Exitoso", response.body.result.statusRole)
				this.loader.loading = false
				this.response.register = true
				}, response => {
			})
		}
	},
	template: `
	<div class="row" v-if="alertData.user && !alertData.show && !response.register">
		<div class="col-lg-2">
				<label for="selectCampus">Campus</label>
				<div id="filter-campus">
					<select v-model="register.campus" class="form-control">
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
			<select class="form-control filtersRolAndCampus" v-model="register.roleCode">
				<option disabled="disabled">Seleccione un rol</option>
				<option value="555">Administrador de Personal</option>
				<option value="556">Administrador-Comisiones</option>
				<option value="554">Autorizador Divisional</option>
				<option value="557">Jefe de Programa CE</option>
				<option value="558">Promotor-Comisiones</option>
			</select>
		</div>
		</div>
		<div class="col-lg-2" id="recrCodeDiv" v-if="register.roleCode == 557 || register.roleCode == 558">
			<label for="recrCodeInput">Código de Promotor</label>
			<input type="text" class="form-control" id="recrCode" style="text-transform:uppercase" maxlength="4" v-model="register.rcreCode">
		</div>
		<div class="col-md-2 col-lg-2">
			<button id="saveRoleButton" style="margin-top:35px;" class="btn btn-sm btn-success btn-block" :disabled="disabledRegister" @click="saveRole()"><i class="fa fa-user-plus" aria-hidden="true"></i> Registrar</button>
		</div>
	</div>
	<div class="row" v-else-if="!alertData.user && !alertData.show">
		<div class="col-lg-12">
		<table class="table">
			<thead>
				<tr>
						<th>Usuario</th>
						<th>Nombre</th>
						<th>Campus</th>
						<th>Rol</th>
						<th></th>
						<th></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td scope="row">{{user.person.userName}}</td>
					<td>{{user.person.firstName}} {{user.person.lastName}}</td>
					<td>{{user.person.campuses[0].description}}</td>
					<td>{{user.person.profiles[0].description}}</td>
					<td></td>
					<td>
							<button class="btn btn-danger btn-xs btn-block buttonTable" @click="deleteRol()" id="buttonTable"><i class="fa fa-times" aria-hidden="true"></i> Eliminar</button>
					</td>
				</tr>
			</tbody>
		</table>

		</div>
	</div>
	<div class="row" v-else>
		<div class="col-lg-12">
			<b-alert variant="warning" show>El usuario ha quedado con el rol guardado</b-alert>
		</div>
	</div>
	`
})