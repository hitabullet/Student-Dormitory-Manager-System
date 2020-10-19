import Vue from 'vue'
import Router from 'vue-router';


import Home from "@/components/common/Home";

Vue.use(Router);

const routes = [
    {
        path: '/',
        redirect: '/Home'
    },
    {
        path: 'Home',
        component: Home,
        children: [
            {
                path: '/Home',
                component: () => import('../views/home/Index'),
                meta: {title: '用户首页'}
            },
            {
                path: '/InformationEntry',
                name: 'InformationEntry',
                component: () => (import('../components/common/InformationEntry')),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/GetAccommodation',
                name: 'GetAccommodation',
                component: () => (import('../components/common/GetAccommodation')),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/DormitoryRepair',
                name: 'DormitoryRepair',
                component: () => (import('../components/common/DormitoryRepair')),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登录的
                },
            }

        ]
    },
    {
        path: '/Login',
        name: 'Login',
        component: () => (import('../components/common/Login')),
    },
    {
        path: '/Register',
        name: 'Register',
        component: () => (import('../components/common/Register')),
    },
    {
        path: '/Forget',
        name: 'Forget',
        component: () => (import('../components/common/Forget')),
    },
]

const router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

/*路由覆盖导致重定向出现错误*/
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}




export default router
