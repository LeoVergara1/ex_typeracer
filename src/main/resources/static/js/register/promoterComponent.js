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
			userModal: {
				userCampusPK: {}
			}
    }
	},
	created: function (){
		console.log("Instancia creada de componente")
		this.$http.get('/administration/data/registers').then(response => {
			this.someData = response.body;
			console.log(response.body);
			this.campus = response.body.campus 
			this.$root.$emit('recived_campus_list',  response.body.campus )
			this.roles = response.body.roles
			this.listAssociation = response.body.listAssociation
			console.log(response)
			console.log("regreso")
		}, response => {
			console.log("regreso mal")
			console.log(response)
		});
	},
	methods: {
		getAssociation: function(){

		},
		setUserToEdit(register){
			this.userModal = register
			this.$bvModal.show('modal-edit')
		},
		getDescriptionToDivision: function (value){
			return this.campus[value] ? this.campus[value] : value
		},
		updatedRegister: function(){
			let that = this
			console.log(that.userModal.roleDescription)
			let rolId = this.roles.filter(rol => rol.descriptionRol == that.userModal.roleDescription)
			this.$http.post('/administration/updating/roleAndCampus', {username: this.userModal.userCampusPK.userName, 
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
										<option value=""> Todas </option>
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
				<input type="text" id="usernameId" placeholder="Usuario" class="form-control">
			</div>
			<div class="col-lg-2">
				<label for="selectCampus" class="font-weight-bold">Campus</label>
				<div id="filter-campus"><select class="form-control">
						<option value=""> Todas </option>
						<option v-for="(k, v) in campus" v-bind:value="v">
							{{ k }}
						</option>
					</select>
				</div>
			</div>
			<div class="col-lg-2"><label for="selectRoles" class="font-weight-bold">Rol</label>
				<div id="filter-roles"><select class="form-control filtersRolAndCampus">
						<option value="0"> Todas </option>
						<option v-for="rol in roles" v-bind:value="rol.nidRol">
								{{ rol.descriptionRol | getDescriptionToRol }}
							</option>
					</select>
				</div>
			</div>
			<div class="col-auto"><button class="btn btn-primary " style="margin-top: 32px;"><i aria-hidden="true"
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
							<td>{{register.roleDescription}}</td>
							<th>
									<div class="btn-group" role="group" aria-label="Basic example">
											<button class="btn btn-success btn-xs" @click="setUserToEdit(register)"><i class="fa fa-minus" aria-hidden="true"></i> Editar</button>
											<button class="btn btn-danger btn-xs" ><i class="fa fa-plus" aria-hidden="true"></i> Eliminar</button>
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