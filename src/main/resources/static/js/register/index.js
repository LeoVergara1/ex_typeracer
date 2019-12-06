var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!',
    user: {
      person: {},
      managerRoleId: 0,
      mapRol: {}
    },
    campus: {
      list: {}
    },
    listAssociation: [],
    notifyOptions: {
      timeout: 9000,
      showProgressBar: true,
      closeOnClick: false,
      pauseOnHover: true,
      position: 'rightTop',
      helperNotificationCycle: true
    },
    loader:{
      color: '#0b93d1',
      height: '15px',
      width: '5px',
      margin: '2px',
      radius: '2px',
      loading: false,
      size: "95px",
    },
    helperNotificationCycle: true
	},
	created: function (){

	},
  methods:{
    update: function (numero) {
      console.log(numero)
    }
  },
  mounted() {
    this.$root.$on('recived_campus_list',(campus) => {
			this.campus.list = campus
		})
  },
  components: {
    PulseLoader: VueSpinner.PulseLoader
  }
})