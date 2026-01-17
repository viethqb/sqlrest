<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
/* Global styles - DBAPI style */
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

li {
  list-style: none;
}

a {
  color: #666;
  text-decoration: none;
}

img {
  border: 0;
  vertical-align: middle;
}

#app {
  font-family: 'Consolas', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 100%;
  height: 100%;
  margin: 0;
}

i {
  font-size: 14px;
}

h2 {
  margin-bottom: 25px;
  text-align: center;
}

h4 {
  margin: 10px 0;
}

.el-button--primary.is-active,
.el-button--primary:active {
  background: #20B2AA;
  border-color: #20B2AA;
  color: #fff;
}

.gap {
  margin: 0 10px 10px 0;
}

body {
  background-color: #f3f3f321;
  color: #606266;
}
</style>
