var app = new Vue({
	el: '#app',
	data: {
    date:{
      selectInit: new Date().toLocaleString('es-ES', {year: 'numeric', month: '2-digit', day: 'numeric'}),
      selectFin: new Date().toLocaleString('es-ES', {year: 'numeric', month: '2-digit', day: 'numeric'}),
      months: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"],
			weekDays: ["Lun", "Mar", "Mier", "Jue", "Vie", "Sab", "Dom"],
			campusSelected: "CMX"
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
		campuses: Object,
		person: Object
	},
	created: function(){
		this.loader.loading = true
    this.$http.get('/authorization/campueses').then(response => {
      console.log(response.body);
      this.campuses = response.body.campus
      this.person = response.body.person.person
      this.loader.loading = false
    }, response => {
      console.log("Fail")
      console.log(response)
    })
	},
	methods: {
		getSicoss() {
			this.loader.loading = true
			this.$http.post('/authorization/sicoss', this.date).then(response => {
				console.log(response.body);
				this.loader.loading = false
			}, response => {
				console.log("Fail")
				console.log(response)
			})
		}
	},
	components: {
    RingLoader: VueSpinner.RingLoader,
    DatePick: VueDatePick
  },
})