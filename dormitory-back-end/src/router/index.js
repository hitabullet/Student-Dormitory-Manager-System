import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            name: 'login',
            component: () => import('../views/login'),
        },
        {
            path: 'home',
            redirect: '/home'
        },
        {
            path: 'home',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/home',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../views/home/Index'),
                    meta: { title: '用户首页' }
                },
                {
                    path: '/dorms',
                    component: () => import(/* webpackChunkName: "icon" */ '../views/dorm/Dorms.vue'),
                    meta: { title: '宿舍管理' }
                },
                {
                    path: '/state',
                    component: () => import(/* webpackChunkName: "icon" */ '../views/student/State.vue'),
                    meta: { title: '学生管理' }
                },
                {
                    path: '/repairs',
                    component: () => import(/* webpackChunkName: "icon" */ '../views/repair/Repairs'),
                    meta: { title: '维修清单' }
                },
                {
                    path: '/record',
                    component: () => import(/* webpackChunkName: "icon" */ '../views/repair/Record'),
                    meta: { title: '维修记录' }
                },
                {
                    path: '/addManager',
                    component: () => import(/* webpackChunkName: "icon" */ '../views/manager/AddManager'),
                    meta: { title: '添加管理员' }
                },
            ]
        }
    ]
});
