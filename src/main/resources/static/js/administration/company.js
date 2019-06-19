var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello Vue!',
    person: Object,
    headerBgVariant: "white",
    headerTextVariant: 'dark',
    promoters: Array,
    listPromoterToUser: [],
    loader:{
      color: '#0b93d1',
      height: '15px',
      width: '5px',
      margin: '2px',
      radius: '2px',
      loading: false,
      size: "95px",
    }
  },
  computed: {
  },
  created: function() {
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
  methods:{

  },
  mounted() {
    this.$root.$on('bv::modal::hide', (bvEvent, modalId) => {
      this.loader.loading = true
      this.getCoordinators()
    })
  },
  components: {
    RingLoader: VueSpinner.RingLoader
  }
})