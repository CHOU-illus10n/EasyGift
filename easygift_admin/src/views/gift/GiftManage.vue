<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import { QuillEditor } from '@vueup/vue-quill'

import { Plus } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElButton, ElDrawer } from 'element-plus'

import { computed } from 'vue'
import {
  giftCategoryService,
  giftListService,
  giftInfoService,
  giftImgsService,
  giftChangeStService,
} from '@/api/article.js'
//商品分类数据模型
const categorys = ref([
  {
    categoryId: 3,
    categoryName: '',
    createTime: '2023-09-02 12:06:59',
    updateTime: '2023-09-02 12:06:59',
  },
  {
    categoryId: 4,
    categoryName: '美食',
    createTime: '2023-09-02 12:06:59',
    updateTime: '2023-09-02 12:06:59',
  },
])

const urls = ref([
  {
    giftImgId: 1,
    giftId: '1',
    userId: ' ',
    giftImgUrl: 'https://easygift.oss-cn-beijing.aliyuncs.com/OIP-C.jpg',
    isDeleted: 0,
  },
])

//文章列表数据模型
const gifts = ref([
  {
    giftId: '2',
    userId: '1',
    giftName: null,
    description: null,
    createTime: '2023-12-13 16:06:55',
    state: null,
  },
])
//控制抽屉是否显示
const visibleDrawer = ref(false)
//用户搜索时选中的分类id
const categoryId = ref('')

//用户搜索时选中的发布状态
const state = ref('')

//分页条数据模型
const pageNum = ref(1) //当前页
const total = ref(20) //总条数
const pageSize = ref(6) //每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  giftList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  giftList()
}
import { ElMessage } from 'element-plus'
//不通过
const reject = () => {
  ElMessage.fail('审核通过')
  visibleDrawer.value = false
}

//回显文章分类
const giftCategoryList = async () => {
  let result = await giftCategoryService()
  categorys.value = result.data
  console.log(categorys)
}
//获取物品图片
const giftImgsList = async (id) => {
  let result = await giftImgsService(id)
  urls.value = result.data
  console.log(urls)
}

const giftList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    categoryId: categoryId.value,
    state: state.value,
  }
  let result = await giftListService(params)

  //渲染视图 赋值给数据模型
  total.value = result.data.total
  gifts.value = result.data.items

  //处理数据,给数据模型扩展一个属性categoryName,分类名称
  for (let i = 0; i < gifts.value.length; i++) {
    let gift = gifts.value[i]
    for (let j = 0; j < categorys.value.length; j++) {
      if (gift.categoryId == categorys.value[j].id) {
        gift.categoryName = categorys.value[j].categoryName
      }
    }
  }
}
//调用
giftCategoryList()
giftList()

//搜索
const onSearch = async () => {
  giftList()
}

const giftsModel = ref([
  {
    giftId: '2',
    userId: '1',
    giftName: null,
    description: null,
    createTime: '2023-12-13T16:06:55',
    state: null,
  },
])

const giftListOne = async (params) => {
  let result = await giftInfoService(params)
  giftsModel.value = result.data
  console.log(giftsModel)
}

//打开抽屉
const open = (row) => {
  visibleDrawer.value = true
  let params = row.giftId
  giftListOne(params)
  giftImgsList(params)
}

const changeState = async (params) => {
  let result = await giftChangeStService(params)
  console.log(result)
}

//通过
const pass = async () => {
  console.log(giftsModel.value.state)
  if (giftsModel.value.state == 2 || giftsModel.value.state == 1) {
    ElMessage.error('不可更改此状态')
    visibleDrawer.value = false
    return
  }
  const params = {
    giftId: giftsModel.value.giftId,
    state: 1,
  }
  await changeState(params)
  giftList()
  ElMessage.success('审核通过')
  visibleDrawer.value = false
}

//不通过
const rej = async () => {
  if (giftsModel.value.state == 1 || giftsModel.value.state == 2) {
    ElMessage.error('不可更改此状态')
    visibleDrawer.value = false
    return
  }
  const params = {
    giftId: giftsModel.value.giftId,
    state: 2,
  }
  await changeState(params)
  giftList()
  ElMessage.error('审核不通过')
  visibleDrawer.value = false
}
//改变字体颜色和样式
const getColorByState = (state) => {
  switch (state) {
    case 0:
      return 'blue' // 未审核，蓝色
    case 1:
      return 'green' // 审核通过，绿色
    case 2:
      return 'red' // 审核不通过，红色
    default:
      return 'green' // 默认颜色
  }
}

const getTextByState = (state) => {
  switch (state) {
    case 0:
      return '待审核'
    case 1:
      return '已通过'
    case 2:
      return '未通过'
    default:
      return '已通过'
  }
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>物品审核</span>
      </div>
    </template>
    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="物品分类：">
        <el-select
          placeholder="请选择"
          v-model="categoryId"
          value-key="categoryName"
        >
          <el-option
            v-for="c in categorys"
            :key="c.categoryId"
            :label="c.categoryName"
            :value="c.categoryId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态：">
        <el-select placeholder="请选择" v-model="state">
          <el-option label="待审核" :value="0"></el-option>
          <el-option label="已通过" :value="1"></el-option>
          <el-option label="未通过" :value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSearch">搜索</el-button>
        <el-button @click=";(categoryId = ''), (state = '')">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 文章列表 -->
    <el-table :data="gifts" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"> </el-table-column>
      <el-table-column label="发布人ID" prop="userId"></el-table-column>
      <el-table-column label="发表时间" prop="createTime"> </el-table-column>
      <el-table-column label="状态" prop="state"
        ><template v-slot="{ row }">
          <span :style="{ color: getColorByState(row.state) }">
            {{ getTextByState(row.state) }}
          </span>
        </template></el-table-column
      >
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="open(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
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

    <!-- 抽屉 -->
    <el-drawer v-model="visibleDrawer" direction="rtl" size="70%">
      <el-form :model="giftsModel" label-width="100px">
        <el-form-item label="物品名称">
          <el-input
            disabled
            placeholder=" "
            v-model="giftsModel.giftName"
          ></el-input>
        </el-form-item>
        <el-form-item label="物品描述">
          <el-input
            autosize
            type="textarea"
            disabled
            placeholder=" "
            v-model="giftsModel.description"
          ></el-input>
        </el-form-item>
        <el-form-item label="物品图片"> </el-form-item>
        <div class="block text-center" m="t-4">
          <el-carousel trigger="click" height="300px">
            <el-carousel-item v-for="url in urls" :key="url">
              <el-image
                :src="url.giftImgUrl"
                style="width: 100%; height: 100%"
                :fit="contain"
              />
            </el-carousel-item>
          </el-carousel>
        </div>
        <h1></h1>
        <el-form-item>
          <div style="width: 80%; text-align: center">
            <el-button type="primary" @click="pass()">通过</el-button
            ><el-button type="info" @click="rej()">不通过</el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-drawer>
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

/* 抽屉样式 */
.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}

.editor {
  width: 100%;

  :deep(.ql-editor) {
    min-height: 200px;
  }
}

.el-descriptions {
  margin-top: 20px;
}
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
