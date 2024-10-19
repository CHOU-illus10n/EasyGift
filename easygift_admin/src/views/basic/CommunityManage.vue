<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { Plus, Upload } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { ElButton, ElDrawer } from 'element-plus'
import { useTokenStore } from '@/stores/token.js'
//用户搜索时的信息
const cName = ref('')
//控制抽屉是否显示
const visibleDrawer = ref(false)
const visibleDrawer2 = ref(false)
// const imgUrl = ref()
const tokenStore = useTokenStore()

const community = ref([
  {
    communityId: 1,
    communityName: '白云社区',
    communityAddress: '复兴路1号',
    createTime: '2023-12-17T16:14:32',
    updateTime: '2023-12-17T16:14:32',
    isDeleted: 0,
  },
])

const communityModel = ref({
  communityId: 1,
  communityName: '',
  communityAddress: '',
  createTime: '2023-12-17T16:14:32',
  updateTime: '2023-12-17T16:14:32',
  isDeleted: 0,
})

const pageNum = ref(1) //当前页
const total = ref(20) //总条数
const pageSize = ref(4) //每页条数
//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  communityList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  communityList()
}

//回显文章分类
import {
  categoryAddService,
  CategoryDeleteService,
  CategoryUpdateService,
  CategoryDetailService,
  communityListService,
  communityAddService,
  communityUpdateService,
  communityGetOneService,
  communityDeleteService,
} from '@/api/article.js'
const communityList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    communityName: cName.value ? cName.value : null,
  }
  console.log(cName.value)
  let result = await communityListService(params)
  community.value = result.data.items
  total.value = result.data.total
  console.log(community)
}
//搜索事件绑定
const onSearch = async () => {
  communityList()
}
communityList()

const add = async () => {
  let params = communityModel.value
  console.log(communityModel.value)
  let result = await communityAddService(params)
  console.log(result)
  ElMessage.success(result.msg ? result.msg : '添加成功')
  communityList()
  visibleDrawer.value = false
  clearModel()
}

const clearModel = () => {
  communityModel.value = {
    communityId: 0,
    communityName: '',
    communityAddress: '',
    createTime: '',
    updateTime: '',
    isDeleted: 0,
  }
}

//删除
const deleteCommunity = async (row) => {
  let result = await communityDeleteService(row.communityId)
  ElMessage.success(result.msg ? result.msg : '删除成功')
  communityList()
}
//获取分类信息展示
const communityGetOne = async (params) => {
  let result = await communityGetOneService(params)
  communityModel.value = result.data
  console.log(communityModel.value)
}

//打开修改抽屉
const open = (row) => {
  visibleDrawer2.value = true
  communityModel.value = communityGetOne(row.communityId)
  console.log(communityModel.value)
}

const communityUpdate = async (params) => {
  let result = await communityUpdateService(params)
  console.log(result)
}

const update = async () => {
  let params = communityModel.value
  let result = await communityUpdate(params)
  console.log(result)
  ElMessage.success('修改成功')
  await communityList()
  visibleDrawer2.value = false
  clearModel()
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>小区管理</span>
        <div class="extra">
          <el-button
            type="primary"
            @click="clearModel(), (visibleDrawer = true)"
            >添加小区</el-button
          >
        </div>
      </div>
    </template>
    <el-form inline>
      <el-form-item label="小区名称">
        <el-input v-model="cName" placeholder="请输入类型名称" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSearch">搜索</el-button>
        <el-button @click="cName = ''">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="community" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"> </el-table-column>
      <el-table-column label="小区名称" prop="communityName"></el-table-column>
      <el-table-column
        label="小区地址"
        prop="communityAddress"
      ></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="clearModel(), open(row)"
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteCommunity(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

    <!-- 抽屉 -->
    <el-drawer v-model="visibleDrawer" direction="rtl" size="70%">
      <el-form :model="communityModel" label-width="100px">
        <el-form-item label="小区名称">
          <el-input
            placeholder=" 请输入小区名称"
            v-model="communityModel.communityName"
          ></el-input>
        </el-form-item>
        <el-form-item label="小区地址">
          <el-input
            placeholder=" 请输入小区地址"
            v-model="communityModel.communityAddress"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <div style="width: 80%; text-align: center">
            <el-button type="primary" @click="add()">添加</el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-drawer>

    <!-- 修改抽屉 -->
    <el-drawer v-model="visibleDrawer2" direction="rtl" size="70%">
      <el-form :model="communityModel" label-width="100px">
        <el-form-item label="小区名称">
          <el-input v-model="communityModel.communityName"></el-input>
        </el-form-item>
        <el-form-item label="小区地址">
          <el-input v-model="communityModel.communityAddress"></el-input>
        </el-form-item>
        <el-form-item>
          <div style="width: 80%; text-align: left">
            <el-button type="primary" @click="update()">修改</el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-drawer>
    <!-- 分页条 -->
    <el-pagination
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      :page-sizes="[3, 5, 10, 15]"
      layout="jumper, total, sizes, prev, pager, next"
      background
      :total="total"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px; justify-content: flex-end"
    />
  </el-card>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
