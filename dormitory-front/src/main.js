import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import './assets/icon/iconfont.css';
import 'element-ui/lib/theme-chalk/index.css';
import './plugins/axios';
import VueCookies from 'vue-cookies'

Vue.use(VueCookies)
Vue.config.productionTip = false
Vue.use(ElementUI);

router.beforeEach(function (to, from, next) {
    if (to.meta.requireAuth) {
        if (VueCookies.isKey("studentNo")) {
            next()
        } else {
            Vue.prototype.$message({
                showClose: true,
                type: "error",
                message: "请登录后再访问！"
            });
            next({
                name: "Login",
                query: {backUrl: to.fullPath}
            })
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

