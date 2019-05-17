// Define a new component called button-counter
Vue.component('template-search', {
	props:  ['notifyOptions'],
  data: function () {
    return {
			searchData: {
				user: ""
			},
			user: {
				lastName: "",
				firstName: "",
				pidm: "",
				adminId: "",
				middleName: "",
				userName: ""
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
			this.$http.post('/administration/search/association', this.searchData ).then(response => {
				// get body data
				this.user = response.body.person;
				console.log(response.body);
				if(response.body.userName){
					this.responses.foundInBanner = true
				}
				else {
					this.responses.foundInBanner = false
					this.$snotify.warning('Usuario no encontrado', 'Advertencia', this.notifyOptions);
				}
			}, response => {
				console.log("Fail")
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
						<div class="col-auto" v-if="responses.foundInBanner">
							<div style="margin-bottom: 30px;padding-left: 40px;">
								<h4 class="block orange"><i class="fa fa-user" aria-hidden="true"></i></h4>
								<h4 class="block orange" style="margin-top:30px;">{{user.lastName}} {{user.firstName}}</h4>
								<h4 class="block blue">ID: {{user.enrollment}}</h4>
							</div>
						</div>
					</div>
	</div>
</div>

	`
})