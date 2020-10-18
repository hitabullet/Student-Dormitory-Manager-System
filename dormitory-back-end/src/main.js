import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import './plugins/axios';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
// import './assets/css/theme-green/index.css'; // 浅绿色主题
import './assets/css/icon.css';
import './assets/icon/iconfont.css';
import './components/common/directives';
import moment from 'moment'

Vue.prototype.$moment = moment

Vue.config.productionTip = false;
Vue.use(ElementUI, {
    size: 'small'
});


new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');



