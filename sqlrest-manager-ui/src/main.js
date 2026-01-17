// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from './assets/axios.js';
import ElementUI from 'element-ui';
import locale from 'element-ui/lib/locale/lang/en'
import './assets/iconfont/iconfont.css'
import './assets/dbicon/iconfont.css'
import './assets/sysicon/iconfont.css'
import './theme/index.css'
import 'element-ui/lib/theme-chalk/index.css';
import * as echarts from 'echarts'
import VueCodeMirror from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import JsonViewer from 'vue-json-viewer'

Vue.use(VueCodeMirror)
Vue.use(axios)
Vue.use(ElementUI, { locale })
Vue.use(JsonViewer) 

Vue.prototype.$http = axios
Vue.config.productionTip = false
Vue.prototype.$echarts = echarts


// HTTP request interceptor
axios.interceptors.request.use(config => {

  // Add Authorization header to request to pass token value
  let token = sessionStorage.getItem('token');
  if (token) {
    config.headers.Authorization = 'Bearer ' + token;
  }

  return config;
}, function (error) {
  // Handle request error
  return Promise.reject(error)
})

// Response status handling (add response interceptor)
axios.interceptors.response.use(res => {
  // Process response data
  if (res.data && (res.data.code === 401 || res.data.code === 403 || res.data.code === 404)) {
    router.push({
      path: "/login"
    })
  }

  return res
}, error => {
  // Return error information from response
  //console.log(error);
  return Promise.reject(error.response)
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
