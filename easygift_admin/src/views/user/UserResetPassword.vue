<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userInfo.js'

const psd = ref('')
const userInfoStore = useUserInfoStore()

const userInfo = ref({ ...userInfoStore.info })
const rules = {
  nickname: [
    { required: true, message: '请输入用户昵称', trigger: 'blur' },
    {
      pattern: /^\S{2,10}$/,
      message: '昵称必须是2-10位的非空字符串',
      trigger: 'blur',
    },
  ],
  email: [
    { required: true, message: '请输入用户邮箱', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' },
  ],
}

//修改个人信息
import { userInfoUpdateService } from '@/api/user.js'
import { ElMessage } from 'element-plus'
const updateUserInfo = async () => {
  console.log(psd.value)
  userInfo.value.psd = psd.value
  //调用接口
  let result = await userInfoUpdateService(userInfo.value)
  ElMessage.success(result.msg ? result.msg : '修改成功')

  //修改pinia中的个人信息
  userInfoStore.setInfo(userInfo.value)
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>修改密码</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form
          :model="userInfo"
          :rules="rules"
          label-width="100px"
          size="large"
        >
          <el-form-item label="登录账号">
            <el-input
              v-model="userInfo.userName"
              disabled
              placeholder=""
            ></el-input>
          </el-form-item>
          <el-form-item label="用户密码" prop="psd">
            <el-input
              v-model="psd"
              type="password"
              placeholder="请输入密码"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateUserInfo"
              >提交修改</el-button
            >
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>
