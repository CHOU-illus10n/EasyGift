import { createRouter, createWebHistory } from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'

import ArticleManageVue from '@/views/article/ArticleManage.vue'
import UserAvatarVue from '@/views/user/UserAvatar.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'
import GiftManageVue from '@/views/gift/GiftManage.vue'
import StartVue from '@/views/Start.vue'
import GiftShowVue from '@/views/gift/GiftShow.vue'
import OrderVue from '@/views/gift/Order.vue'
import GoodManageVue from '@/views/mall/GoodManage.vue'
import GoodOrderVue from '@/views/mall/GoodOrder.vue'
import UserManageVue from '@/views/user/UserManage.vue'
import CategoryManageVue from '@/views/basic/CategoryManage.vue'
import CommunityManageVue from '@/views/basic/CommunityManage.vue';
//定义路由关系
const routes = [
    { path: '/login', component: LoginVue },
    {
        path: '/', component: LayoutVue, redirect:'/start',children: [
            { path:'/start',component:StartVue},
            { path:'/gift',component:GiftManageVue },
            { path:'/gift/detail',component:GiftShowVue},
            { path:'/gift/order',component:OrderVue},
            { path:'/good',component:GoodManageVue},
            { path:'/good/order',component:GoodOrderVue},
            { path:'/user',component:UserManageVue},
            { path:'/category',component:CategoryManageVue},
            {path:'/community',component:CommunityManageVue},
            // { path: '/article/category', component: ArticleCategoryVue },
            // { path: '/article/manage', component: ArticleManageVue },
            { path: '/user/info', component: UserInfoVue },
            { path: '/user/avatar', component: UserAvatarVue },
            { path: '/user/resetPassword', component: UserResetPasswordVue }
        ]
    }
]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

//导出路由
export default router
