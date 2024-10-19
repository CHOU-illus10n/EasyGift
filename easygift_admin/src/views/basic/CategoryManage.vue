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

//商品分类数据模型
const categorys = ref([
  {
    categoryId: 3,
    categoryName: '',
    createTime: '2023-09-02 12:06:59',
    updateTime: '2023-09-02 12:06:59',
  },
])

const categoryModel = ref({
  categoryId: '',
  categoryName: '',
  createTime: '2023-09-02 12:06:59',
  updateTime: '2023-09-02 12:06:59',
})

const pageNum = ref(1) //当前页
const total = ref(20) //总条数
const pageSize = ref(4) //每页条数
//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  giftCategoryList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  giftCategoryList()
}

//回显文章分类
import {
  giftCategoryService,
  goodListService,
  goodInfoService,
  goodAddService,
  deleteGoodService,
  getGoodByIdService,
  goodGetOneService,
  goodUpdateService,
  CategoryListService,
  categoryAddService,
  CategoryDeleteService,
  CategoryUpdateService,
  CategoryDetailService,
} from '@/api/article.js'
const giftCategoryList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    categoryName: cName.value ? cName.value : null,
  }
  let result = await CategoryListService(params)
  categorys.value = result.data.items
  total.value = result.data.total
  console.log(categorys)
}
//搜索事件绑定
const onSearch = async () => {
  giftCategoryList()
}
giftCategoryList()

const add = async () => {
  let params = categoryModel.value
  console.log(categoryModel.value)
  let result = await categoryAddService(params)
  console.log(result)
  ElMessage.success(result.msg ? result.msg : '添加成功')
  giftCategoryList()
  visibleDrawer.value = false
  clearModel()
}

const clearModel = () => {
  categoryModel.value = {
    categoryId: '',
    categoryName: '',
    createTime: '',
    updateTime: '',
  }
}

//删除
const deleteCategory = async (row) => {
  let result = await CategoryDeleteService(row.categoryId)
  ElMessage.success(result.msg ? result.msg : '删除成功')
  giftCategoryList()
}
//获取分类信息展示
const categoryListOne = async (params) => {
  let result = await CategoryDetailService(params)
  categoryModel.value = result.data
  console.log(categoryModel)
}

//打开修改抽屉
const open = (row) => {
  visibleDrawer2.value = true
  categoryModel.value = categoryListOne(row.categoryId)
  console.log(categoryModel.value)
}

const categoryUpdate = async (params) => {
  let result = await CategoryUpdateService(params)
  console.log(result)
}

const update = async () => {
  let params = categoryModel.value
  let result = await categoryUpdate(params)
  console.log(result)
  ElMessage.success('修改成功')
  await giftCategoryList()
  visibleDrawer2.value = false
  clearModel()
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>类型管理</span>
        <div class="extra">
          <el-button
            type="primary"
            @click="clearModel(), (visibleDrawer = true)"
            >添加类型</el-button
          >
        </div>
      </div>
    </template>
    <el-form inline>
      <el-form-item label="类型名称">
        <el-input v-model="cName" placeholder="请输入类型名称" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSearch">搜索</el-button>
        <el-button @click="cName = ''">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="categorys" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"> </el-table-column>
      <el-table-column label="类型ID" prop="categoryId"></el-table-column>
      <el-table-column label="类型名称" prop="categoryName"></el-table-column>
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
            @click="deleteCategory(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

    <!-- 抽屉 -->
    <el-drawer v-model="visibleDrawer" direction="rtl" size="70%">
      <el-form :model="categoryModel" label-width="100px">
        <el-form-item label="类型名称">
          <el-input
            placeholder=" 请输入类型名称"
            v-model="categoryModel.categoryName"
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
      <el-form :model="categoryModel" label-width="100px">
        <el-form-item label="类型名称">
          <el-input v-model="categoryModel.categoryName"></el-input>
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
