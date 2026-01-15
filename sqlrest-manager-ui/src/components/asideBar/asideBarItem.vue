<template>
  <div class="asideBarItem-container">
    <!-- If hasOwnProperty detects children, recursively display them -->
    <el-submenu :index="router.path" v-if="hasChildrenAndShow(router)">
      <template slot="title">
      <i :class="router.icon"></i>
      <span slot="title">{{router.name}}</span>
      </template>
      <!-- Recursively display child navigation -->
      <asideBarItem v-for="(child, childKey) in router.children" :key="child.path" :router="child"></asideBarItem>
    </el-submenu>
    <!-- If no children, display first-level navigation -->
    <el-menu-item v-else :key="router.path" :index="router.path" @click="saveActivePath(router.path)">
       <i :class="router.icon"></i>
       <span>{{router.name}}</span>
    </el-menu-item>
  </div>
</template>

<script>
// hasOwnProperty can be used to detect whether an object has a specific own property
export default {
  name: "asideBarItem",
  props: {
    router: {
      type: Object
    },
  },
  components: {},
  data() {
    return {
    };
  },
  computed: {
    // router () {
    //   return this.$router.options.routes
    // }
  },
  watch: {},
  methods: {
    hasChildrenAndShow(router){
      if(router.hidden){
        return false
      }

      return router.hasOwnProperty('children');
    },
    saveActivePath(path) {
      //alert(path);
      this.$emit('setActivePath',path);
    },
  },
  created() {
  },
  mounted() {}
};
</script>

<style scoped>
.el-menu-item.is-active {
  background-color: #1890ff !important;
}

/* Hide text */
.el-menu--collapse .asideBarItem-container span{
  display: none;
}
/* Hide > */
.el-menu--collapse .asideBarItem-container .el-submenu__title .el-submenu__icon-arrow{
  display: none;
}

</style>
