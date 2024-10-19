<script setup>
import {
  Management,
  Promotion,
  UserFilled,
  User,
  Crop,
  EditPen,
  SwitchButton,
  CaretBottom,
  Setting,
  HomeFilled,
  Files,
  ShoppingTrolley,
  Goods,
  Tickets,
  Collection,
  DocumentChecked,
  FolderChecked,
  Document,
  DataBoard,
} from '@element-plus/icons-vue'
// import avatar from '../assets/default.png'

import { userInfoService } from '@/api/user.js'
import useUserInfoStore from '@/stores/userInfo.js'
import { useTokenStore } from '@/stores/token.js'
const tokenStore = useTokenStore()
const userInfoStore = useUserInfoStore()
//调用函数, 获取用户详细信息
const getUserInfo = async () => {
  //调用接口
  let result = await userInfoService()
  //数据存储到pinia中
  console.log(result.data)
  userInfoStore.setInfo(result.data)
  console.log(userInfoStore.info)
}

getUserInfo()
//条目被点击后, 调用的函数
import { useRouter } from 'vue-router'
const router = useRouter()
import { ElMessage, ElMessageBox } from 'element-plus'
const handleCommand = (command) => {
  //判断指令
  if (command === 'logout') {
    //退出登录
    ElMessageBox.confirm('您确认要退出吗?', '温馨提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    })
      .then(async () => {
        //退出登录
        //1.清空pinia中存储的token以及个人信息
        tokenStore.removeToken()
        userInfoStore.removeInfo()

        //2.跳转到登录页面
        router.push('/login')
        ElMessage({
          type: 'success',
          message: '退出登录成功',
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '用户取消了退出登录',
        })
      })
  } else {
    //路由
    router.push('/user/' + command)
  }
}
</script>

<template>
  <!-- element-plus中的容器 -->
  <el-container class="layout-container">
    <!-- 左侧菜单 -->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <!-- element-plus的菜单标签 -->
      <el-menu
        :unique-opened="true"
        active-text-color="#ffd04b"
        background-color="#333744"
        text-color="#fff"
        router
      >
        <el-menu-item index="/">
          <el-icon>
            <Management />
          </el-icon>
          <span>易赠后台</span>
        </el-menu-item>

        <el-menu-item index="/gift">
          <el-icon>
            <DocumentChecked />
          </el-icon>
          <span>物品审核</span>
        </el-menu-item>

        <el-sub-menu index="2">
          <template #title>
            <el-icon>
              <DataBoard />
            </el-icon>
            <span>转赠平台管理</span>
          </template>
          <el-menu-item index="/gift/detail">
            <el-icon>
              <Crop />
            </el-icon>
            <span>二手物品管理</span>
          </el-menu-item>
          <el-menu-item index="/gift/order">
            <el-icon>
              <Collection />
            </el-icon>
            <span>交易记录管理</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="3">
          <template #title>
            <el-icon>
              <ShoppingTrolley />
            </el-icon>
            <span>积分商城管理</span>
          </template>
          <el-menu-item index="/good">
            <el-icon>
              <Goods />
            </el-icon>
            <span>商品管理</span>
          </el-menu-item>
          <el-menu-item index="/good/order">
            <el-icon>
              <Tickets />
            </el-icon>
            <span>订单管理</span>
          </el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/user">
          <el-icon>
            <User />
          </el-icon>
          <span>用户管理</span>
        </el-menu-item>
        <el-sub-menu index="5">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>基本管理</span>
          </template>
          <el-menu-item index="/category">
            <el-icon>
              <Files />
            </el-icon>
            <span>物品类型管理</span>
          </el-menu-item>
          <el-menu-item index="/community">
            <el-icon><HomeFilled /></el-icon>
            <span>小区管理</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <!-- 右侧主区域 -->
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <div>
          <strong>{{ userInfoStore.info.nickname }}</strong>
        </div>
        <!-- 下拉菜单 -->
        <!-- command: 条目被点击后会触发,在事件函数上可以声明一个参数,接收条目对应的指令 -->
        <el-dropdown placement="bottom-end" @command="handleCommand">
          <span class="el-dropdown__box">
            <el-avatar
              :src="
                userInfoStore.info.userPic ? userInfoStore.info.userPic : null
              "
            />
            <el-icon>
              <CaretBottom />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="avatar" :icon="Crop"
                >更换头像</el-dropdown-item
              >
              <el-dropdown-item command="resetPassword" :icon="EditPen"
                >重置密码</el-dropdown-item
              >
              <el-dropdown-item command="logout" :icon="SwitchButton"
                >退出登录</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!-- 中间区域 -->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #333744;

    &__logo {
      height: 120px;
      background: url('@/assets/eglogo.png') no-repeat center / 120px auto;
    }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }
}
</style>
