<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { Plus, Upload } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { ElButton, ElDrawer } from 'element-plus'
import { useTokenStore } from '@/stores/token.js'
//用户搜索时的信息
const gName = ref('')
//控制抽屉是否显示
const visibleDrawer = ref(false)
const visibleDrawer2 = ref(false)
// const imgUrl = ref()
const tokenStore = useTokenStore()
const value = ref('')

//商品分类数据模型
const categorys = ref([
  {
    categoryId: 3,
    categoryName: '',
    createTime: '2023-09-02 12:06:59',
    updateTime: '2023-09-02 12:06:59',
  },
])
const goods = ref([
  {
    goodId: 1,
    goodCategoryId: 1,
    goodImg: null,
    goodName: null,
    goodPoint: 0,
    goodDesc: null,
    goodCount: 0,
    createTime: '2023-12-14T13:11:37.000+00:00',
    updateTime: '2023-12-14T13:11:37.000+00:00',
    isDeleted: 0,
  },
])
const goodsModel = ref({
  goodId: '',
  goodCategoryId: '',
  goodImg: '',
  goodName: '',
  goodPoint: '',
  goodDesc: '',
  goodCount: '',
  createTime: '',
  updateTime: '',
  isDeleted: '',
})

const pageNum = ref(1) //当前页
const total = ref(20) //总条数
const pageSize = ref(4) //每页条数
//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  goodShowList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  goodShowList()
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
} from '@/api/article.js'
const giftCategoryList = async () => {
  let result = await giftCategoryService()
  categorys.value = result.data
  console.log(categorys)
}
//搜索事件绑定
const onSearch = async () => {
  goodShowList()
}

const goodShowList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    goodName: gName.value ? gName.value : null,
  }
  let result = await goodListService(params)
  console.log(result)
  goods.value = result.data.items
  total.value = result.data.total

  for (let i = 0; i < goods.value.length; i++) {
    let good = goods.value[i]
    for (let j = 0; j < categorys.value.length; j++) {
      if (good.goodCategoryId == categorys.value[j].categoryId) {
        good.categoryName = categorys.value[j].categoryName
        console.log(goods.categoryName)
      }
    }
  }
}
giftCategoryList()
goodShowList()

//图片修改
const updateGoodPic = async (result) => {
  let params = goodsModel.value
  let img = result.data
  console.log(img)
  goodsModel.value.goodImg = img
  //调用接口
  console.log(params)
  ElMessage.success('上传成功')
}

const add = async () => {
  let params = goodsModel.value
  console.log(goodsModel.value)
  let result = await goodAddService(params)
  console.log(result)
  ElMessage.success(result.msg ? result.msg : '添加成功')
  goodShowList()
  visibleDrawer.value = false
  clearModel()
}

//删除
const deleteGood = async (row) => {
  let result = await deleteGoodService(row.goodId)
  ElMessage.success(result.msg ? result.msg : '删除成功')
  goodShowList()
}

const clearModel = () => {
  goodsModel.value = {
    goodId: '',
    goodCategoryId: '',
    goodImg: '',
    goodName: '',
    goodPoint: '',
    goodDesc: '',
    goodCount: '',
    createTime: '',
    updateTime: '',
    isDeleted: '',
  }
  value.value = ''
}

//打开修改抽屉
const open = async (row) => {
  visibleDrawer2.value = true
  let result = await goodGetOneService(row.goodId)
  goodsModel.value = result.data
  let good = goodsModel.value
  console.log(goodsModel.value)
  for (let j = 0; j < categorys.value.length; j++) {
    if (good.goodCategoryId == categorys.value[j].categoryId) {
      good.categoryName = categorys.value[j].categoryName
      value.value = good.categoryName
      console.log(value.value)
    }
  }
  console.log(goodsModel)
}
const goodUpdate = async (params) => {
  let result = await goodUpdateService(params)
  console.log(result)
}

const update = async () => {
  //将value转换为类型id
  for (let j = 0; j < categorys.value.length; j++) {
    if (value.value == categorys.value[j].categoryName) {
      value.value = goodsModel.categoryId
      console.log(value.value)
    }
  }
  let params = goodsModel.value
  params.goodCategoryId = value.value
  let result = await goodUpdate(params)
  console.log(result)
  ElMessage.success('修改成功')
  await goodShowList()
  visibleDrawer2.value = false
  clearModel()
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>积分商品管理</span>
        <div class="extra">
          <el-button
            type="primary"
            @click="clearModel(), (visibleDrawer = true)"
            >添加商品</el-button
          >
        </div>
      </div>
    </template>
    <el-form inline>
      <el-form-item label="商品名称">
        <el-input v-model="gName" placeholder="请输入商品名称" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSearch">搜索</el-button>
        <el-button @click="gName = ''">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="goods" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"> </el-table-column>
      <el-table-column label="商品ID" prop="goodId"></el-table-column>
      <el-table-column label="商品名称" prop="goodName"></el-table-column>
      <el-table-column label="分类名称" prop="categoryName"></el-table-column>
      <el-table-column label="图片">
        <template v-slot="{ row }">
          <el-image
            style="width: 30px; height: 30px"
            :src="row.goodImg"
            :preview-src-list="[row.goodImg]"
            :key="row.goodId"
          >
            <div slot="error" class="image-slot">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image> </template
      ></el-table-column>
      <el-table-column label="所需积分" prop="goodPoint"></el-table-column>
      <el-table-column label="所剩数量" prop="goodCount"></el-table-column>
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
            @click="deleteGood(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

    <!-- 抽屉 -->
    <el-drawer v-model="visibleDrawer" direction="rtl" size="70%">
      <el-form :model="goodsModel" label-width="100px">
        <el-form-item label="商品名称">
          <el-input
            placeholder=" 请输入商品名称"
            v-model="goodsModel.goodName"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品分类：">
          <el-select
            placeholder="请选择"
            v-model="goodsModel.goodCategoryId"
            value-key="categoryName"
            ><el-option
              v-for="c in categorys"
              :key="c.categoryId"
              :label="c.categoryName"
              :value="c.categoryId"
            >
            </el-option
          ></el-select>
        </el-form-item>
        <el-form-item label="所需积分">
          <el-input
            placeholder=" 请输入所需积分"
            v-model="goodsModel.goodPoint"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品数量">
          <el-input
            placeholder=" 请输入商品数量"
            v-model="goodsModel.goodCount"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品描述">
          <el-input
            autosize
            type="textarea"
            placeholder="请输入商品描述"
            v-model="goodsModel.goodDesc"
          ></el-input>
        </el-form-item>
        <el-form-item label="文章封面">
          <!-- 
    auto-upload:设置是否自动上传
    action:设置服务器接口路径
    name:设置上传的文件字段名
    headers:设置上传的请求头
    on-success:设置上传成功的回调函数
 -->

          <el-upload
            class="goodImg-uploader"
            :auto-upload="true"
            :show-file-list="false"
            action="/api/upload"
            name="file"
            :headers="{ Authorization: tokenStore.token }"
            :on-success="updateGoodPic"
          >
            <img
              v-if="goodsModel.goodImg"
              :src="goodsModel.goodImg"
              class="goodImg"
            />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
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
      <el-form :model="goodsModel" label-width="100px">
        <el-form-item label="物品名称">
          <el-input v-model="goodsModel.goodName"></el-input>
        </el-form-item>
        <el-form-item label="商品分类：">
          <el-select
            placeholder="请选择"
            v-model="value"
            value-key="categoryName"
            ><el-option
              v-for="c in categorys"
              :key="c.categoryId"
              :label="c.categoryName"
              :value="c.categoryId"
            >
            </el-option
          ></el-select>
        </el-form-item>
        <el-form-item label="所需积分">
          <el-input
            placeholder=" 请输入所需积分"
            v-model="goodsModel.goodPoint"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品数量">
          <el-input
            placeholder=" 请输入商品数量"
            v-model="goodsModel.goodCount"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品描述">
          <el-input
            autosize
            type="textarea"
            placeholder="请输入商品描述"
            v-model="goodsModel.goodDesc"
          ></el-input>
        </el-form-item>
        <el-form-item label="文章封面">
          <el-upload
            class="goodImg-uploader"
            :auto-upload="true"
            :show-file-list="false"
            action="/api/upload"
            name="file"
            :headers="{ Authorization: tokenStore.token }"
            :on-success="updateGoodPic"
          >
            <img
              v-if="goodsModel.goodImg"
              :src="goodsModel.goodImg"
              class="goodImg"
            />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <div style="width: 80%; text-align: center">
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
