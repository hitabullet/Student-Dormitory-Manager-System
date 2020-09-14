import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import './assets/icon/iconfont.css';
import 'element-ui/lib/theme-chalk/index.css';
import './plugins/axios';
import  VueCookies  from  'vue-cookies'
import VueRouter from "vue-router"

Vue.use(VueRouter);
Vue.use(VueCookies)
Vue.config.productionTip = false
Vue.use(ElementUI);


router.beforeEach(function (to, from, next) {
  if (to.meta.requireAuth){
    if (VueCookies.isKey("studentNo")){
      next()
    }else {
      next({name: "Login",query:{backUrl:to.fullPath}})
    }
  } else {
    next()
  }

});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
/* eslint-disable no-new */

