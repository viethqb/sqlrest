<template>
  <div class="viewer-container">
    <router-view></router-view>
  </div>
</template>
 
<script>
export default {
  name: "viewer",
  components: {},
  data () {
    return {};
  },
  computed: {},
  watch: {},
  methods: {},
  created () { },
  mounted () { }
};
</script>
 
<style scoped>
.viewer-container {
  padding: 20px;
  min-height: calc(100vh - 60px);
  background-color: #f3f3f321;
}
</style>