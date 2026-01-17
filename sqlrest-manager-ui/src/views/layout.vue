<template>
  <div class="home">
    <div>
      <home-header></home-header>
    </div>
    <div class="main">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import homeHeader from '@/components/header/homeHeader.vue'

export default {
  name: "home",
  components: {
    homeHeader
  },
  data () {
    return {};
  },
  computed: {},
  watch: {},
  methods: {},
  created () {
  },
  mounted () { }
};
</script>

<style scoped>
.home {
  display: flex;
  flex-direction: column;
  height: 100vh;
  width: 100%;
}

.main {
  flex: 1;
  overflow-y: auto;
  background-color: #f3f3f321;
}
</style>
