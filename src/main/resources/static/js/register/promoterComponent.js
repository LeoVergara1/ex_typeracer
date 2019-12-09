Vue.component('template-promoter', {
	props: {
		notifyOptions: Object,
	},
  data: function () {
    return {
			count: 0,
			listAssociation: [],
			roles: {},
			campus: {},
			newCampus: "Todas",
			newRole: "Todas",
			promoterRoleId: document.getElementById("promoterRoleId").value,
			managerRoleID: document.getElementById("managerRoleID").value,
			rcreCode: "",
			filterCampus: "0",
			filterRol: "0",
			listAssociationBackout: [],
			userFilter: "",
			userModal: {
				userCampusPK: {}
			}
    }
	},
	created: function (){
		console.log("Instancia creada de componente")
		this.getAssociation()
	},
	methods: {
		getAssociation: function(){
			this.$http.get('/administration/data/registers').then(response => {
				this.someData = response.body;
				console.log(response.body);
				this.campus = response.body.campus 
				this.$root.$emit('recived_campus_list',  response.body.campus )
				this.roles = response.body.roles
				this.listAssociation = response.body.listAssociation
				this.listAssociationBackout = Object.assign(response.body.listAssociation)
				console.log(response)
				console.log("regreso")
			}, response => {
				console.log("regreso mal")
				console.log(response)
			});
		},
		setUserToEdit(register){
			this.userModal = register
			this.$bvModal.show('modal-edit')
		},
		getDescriptionToDivision: function (value){
			return this.campus[value] ? this.campus[value] : value
		},
		validateDelete: function (register) {
      this.$snotify.confirm('¿Eliminar usuario?', 'Advertencia', {
        timeout: 10000,
        showProgressBar: true,
        closeOnClick: true,
        pauseOnHover: true,
        buttons: [
          {
            text: 'Si', action: (notification) => {
              this.deleteRol(register)
              this.$snotify.remove(notification.id)
            }
          },
          { text: 'No', action: (notification) => this.$snotify.remove(notification.id) }
        ]
			})
		},
		filterRegisters: function() {
			console.log()
			this.listAssociation = Object.assign(this.listAssociationBackout)
			if(this.userFilter){
				this.listAssociation = this.listAssociation.filter(register => register.userCampusPK.userName == this.userFilter )
			}
			else if(this.filterCampus.length > 1){
				console.log("Entro")
				console.log(this.filterCampus)
				console.log(this.filterRol)
				if(this.filterRol.length > 1){
					this.listAssociation = this.listAssociation.filter(register => register.roleDescription == this.filterRol  && register.userCampusPK.campusCode == this.filterCampus)
				}
				else {
					console.log("Aqui")
					this.listAssociation = this.listAssociation.filter(register => register.userCampusPK.campusCode == this.filterCampus )
				}
			}
			else if(this.filterRol.length > 1){
				console.log("Segundo if")
					this.listAssociation = this.listAssociation.filter(register => register.roleDescription == this.filterRol )
			}
			//let rolId = this.roles.filter(rol => rol.descriptionRol == that.userModal.roleDescription)
		},
		deleteRol: function(register) {
			console.log("Deleting role")
			let rolId = this.roles.filter(rol => rol.descriptionRol == register.roleDescription)
			this.$http.post('/administration/deleteFromTable/roleAndCampus', {
				username: register.userCampusPK.userName,
				campus:  register.userCampusPK.campusCode,
				idRol: rolId
				}).then(response => {
				console.log("Response ")
				console.log(response)
				console.log(response.body.statusRole)
				this.validatingSatatusResponse("Borrado Exitoso", response.body.statusRole)
				this.getAssociation()
				}, response => {
					console.log(response)
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
		updatedRegister: function(){
			let that = this
			console.log(that.userModal.roleDescription)
			let rolId = this.roles.filter(rol => rol.descriptionRol == that.userModal.roleDescription)
			this.$http.post('/administration/updating/roleAndCampus', {
				username: this.userModal.userCampusPK.userName, 
				campus:  this.userModal.userCampusPK.campusCode,
				idRol: rolId,
				newCampus: this.newCampus,
				newRole: this.newRole,
				rcreCode: this.rcreCode
			} ).then(response => {
				console.log("Response ")
				console.log(response)
				console.log(response.body.result)
				if(response.body.result == 200){
					this.$snotify.info("Actualizado Exitoso", this.notifyOptions)
					this.$bvModal.hide('modal-edit')
					this.getAssociation()
				}
				else{
					this.$snotify.error("Hubo Un error en el proceso", this.notifyOptions)
					this.$bvModal.hide('modal-edit')
				}
				//this.validatingSatatusResponse("Borrado Exitoso", response.body.statusRole)
				}, response => {
					this.$bvModal.hide('modal-edit')
					this.$snotify.error("Hubo Un error en el proceso", this.notifyOptions)
			})
		}
	},
	mounted(){
		this.$root.$on('update_table',() => {
			this.getAssociation()
		})
	},
	filters: {
		getDescriptionToRol: function (value){
			let map = {
				"Jefe_Promoción_LI": " Jefe de Promoción",
				"Coord_Mercadotecnia_LI": "Coordinador de Mercadotecnía",
				"Jefe_Mercadotecnia_LI": "Jefe de Mercadotecnía",
				"Dir_División_LI": "Director de división",
				"Administrador_SICOSS": "Administrador Sicoss",
				"Coord_inteligencia_merc": "Coordinador de inteligencia",
				"Dir_Campus_LI": "Director de Campus"
			}
			return map[value] ? map[value] : value
		},
	},
	template: `
	<div>
			<b-modal id="modal-edit" title="Eiditar usuario">
				<div class="row">
					<div class="col">
						<h4>Username: {{userModal.userCampusPK.userName }}</h4>
					</div>
				</div>
				<div class="row">
						<div class="col">
								<label for="selectCampus" class="font-weight-bold">Campus</label>
								<div id="filter-campus"><select class="form-control" v-model="newCampus">
										<option value="0"> Todas </option>
										<option v-for="(k, v) in campus" v-bind:value="v">
											{{ k }}
										</option>
									</select>
								</div>
							</div>
							<div class="col"><label for="selectRoles" class="font-weight-bold">Rol</label>
								<div id="filter-roles"><select class="form-control filtersRolAndCampus"  v-model="newRole">
										<option value="0"> Todas </option>
										<option v-for="rol in roles" v-bind:value="rol.nidRol">
												{{ rol.descriptionRol | getDescriptionToRol }}
											</option>
									</select>
								</div>
							</div>
							<div class="col" id="recrCodeDiv" v-if="newRole == promoterRoleId || newRole == managerRoleID">
									<label for="recrCodeInput">Cod. Promotor</label>
									<input type="text" class="form-control" id="recrCode" style="text-transform:uppercase" maxlength="4" v-model="rcreCode">
							</div>
				</div>
				<template slot="modal-footer" class="w-100">
							<b-button variant="warning" size="sm" class="float-right" @click="updatedRegister">
								Actualizar
								</b-button>
							<b-button variant="primary" size="sm" class="float-right" @click="$bvModal.hide('modal-edit')">
									Cerrar
								</b-button>
					</template>
			</b-modal>
		<div class="row">
			<div class="col-lg-12">
				<h3 class="blue margin0">Búsqueda
				</h3>
			</div>
		</div>
		<div class="row">
			<div id="recrCodeDiv" class="col-lg-2">
				<label for="recrCodeInput" class="font-weight-bold">Usuario</label>
				<input type="text" id="usernameId" placeholder="Usuario" class="form-control" v-model="userFilter">
			</div>
			<div class="col-lg-2">
				<label for="selectCampus" class="font-weight-bold">Campus</label>
				<div id="filter-campus"><select class="form-control" v-model="filterCampus">
						<option value="0"> Todas </option>
						<option v-for="(k, v) in campus" v-bind:value="v">
							{{ k }}
						</option>
					</select>
				</div>
			</div>
			<div class="col-lg-2"><label for="selectRoles" class="font-weight-bold">Rol</label>
				<div id="filter-roles"><select class="form-control filtersRolAndCampus" v-model="filterRol">
						<option value="0"> Todas </option>
						<option v-for="rol in roles" v-bind:value="rol.descriptionRol">
								{{ rol.descriptionRol | getDescriptionToRol }}
							</option>
					</select>
				</div>
			</div>
			<div class="col-auto"><button class="btn btn-primary " style="margin-top: 32px;" @click="filterRegisters()"><i aria-hidden="true"
						class="fa fa-search"></i>
					Buscar</button>
				</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-lg-12">
				<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col">Nombre</th>
							<th scope="col">Usuario</th>
							<th scope="col">Campus</th>
							<th scope="col">Rol</th>
							<th scope="col"></th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="register in listAssociation">
							<th scope="row">{{register.nameLong}}</th>
							<td>{{register.userCampusPK.userName}}</td>
							<td>{{getDescriptionToDivision(register.userCampusPK.campusCode)}}</td>
							<td>{{register.roleDescription | getDescriptionToRol}}</td>
							<th>
									<div class="btn-group" role="group" aria-label="Basic example">
											<button class="btn btn-success btn-xs" @click="setUserToEdit(register)"><i class="fa fa-minus" aria-hidden="true"></i> Editar</button>
											<button class="btn btn-danger btn-xs" @click="validateDelete(register)" ><i class="fa fa-plus" aria-hidden="true"></i> Eliminar</button>
									</div>
							</th>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	`
})