Vue.component('template-search', {
	props: {
		notifyOptions: Object,
		user: Object,
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

	},
	methods: {
		association: function(){
			this.$root.$emit('set_false_register', false)
			this.loader.loading = true
			this.$http.post('/administration/search/association', this.searchData ).then(response => {
				this.loader.loading = false
				this.user.person = response.body.person;
				this.user.managerRoleId = response.body.managerRoleId;
				this.user.mapRol = response.body.mapRol;
				this.notifyOptions.helperNotificationCycle = true
				console.log(response.body);
				if(response.body.person.userName){
					this.responses.foundInBanner = true
				}
				else {
					this.responses.foundInBanner = false
					this.$snotify.warning('Usuario inexistente', 'Advertencia', this.notifyOptions);
				}
			}, response => {
				console.log("Fail")
				console.log(response)
			})
		},
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
						<div class="col-auto" v-if="responses.foundInBanner">
							<div style="margin-bottom: 30px;padding-left: 40px;">
								<h4 class="block orange"><i class="fa fa-user" aria-hidden="true"></i></h4>
								<h4 class="block orange" style="margin-top:30px;">{{user.person.lastName}} {{user.person.firstName}}</h4>
								<h4 class="block blue">ID: {{user.person.enrollment}}</h4>
							</div>
						</div>
					</div>
	</div>
</div>
	`
})