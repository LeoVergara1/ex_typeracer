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
			promoterRoleId: document.getElementById("promoterRoleId").value,
			managerRoleID: document.getElementById("managerRoleID").value,
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
	mounted(){
		this.$root.$on('set_false_register',(valid) => {
			this.response.register = valid
		})
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
				400: () => {this.$snotify.info("Hubo Un error en el proceso", this.notifyOptions); this.user.person.userName = null},
				404: () => {this.$snotify.error("Hubo Un error en el proceso", this.notifyOptions); this.user.person.userName = null},
				401: () => {this.$snotify.error("El usuario no cumple la precondición en Banner para este rol", this.notifyOptions); this.user.person.userName = null}
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
	},
	template: `
	<div class="row" v-if="alertData.user && !alertData.show && !response.register">
		<div class="col">
			<b-alert variant="warning" show>El usuario no esta dado de alta en la aplicación</b-alert>
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
						<!--

							<button class="btn btn-danger btn-xs btn-block buttonTable" @click="deleteRol()" id="buttonTable"><i class="fa fa-times" aria-hidden="true"></i> Eliminar</button>
						-->
						<div class="btn-group" role="group" aria-label="Basic example">
								<a class="btn btn-success btn-xs" :href="'association/	' + user.person.userName" v-if="user.person.profiles[0].id == managerRoleID"><i class="fa fa-plus" aria-hidden="true"></i>Asociar</a>
						</div>
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