<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
//文章分类数据模型
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
const gifts = ref([
  {
    giftId: '1',
    userId: '1',
    giftName: '1',
    description: '1',
    categoryId: '1',
    giftOriginPrice: 11.1,
    purchaseTime: null,
    dealTime: null,
    dealAddress: null,
    giftQuality: 0,
    state: 1,
    failureTypeId: null,
    createTime: '2023-12-13T02:52:43.000+00:00',
    updateTime: '2023-12-13T09:08:45.000+00:00',
    isDeleted: 0,
  },
])

const pageNum = ref(1) //当前页
const total = ref(20) //总条数
const pageSize = ref(4) //每页条数
//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  giftShowList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  giftShowList()
}

//改变字体颜色和样式
const getColorByState = (state) => {
  switch (state) {
    case 1:
      return 'blue' // 进行中，蓝色
    case 3:
      return 'blue' // 进行中，蓝色
    case 4:
      return 'green' // 完成，绿色
    default:
      return 'black' // 默认颜色
  }
}

const getTextByState = (state) => {
  switch (state) {
    case 1:
      return '待开始'
    case 3:
      return '进行中'
    case 4:
      return '赠送完成'
    default:
      return '无效'
  }
}

//回显文章分类
import {
  giftCategoryService,
  giftListService,
  giftDeleteService,
} from '@/api/article.js'
const giftCategoryList = async () => {
  let result = await giftCategoryService()
  categorys.value = result.data
  console.log(categorys)
}

//声明一个异步的函数
import { giftListAllService } from '@/api/article.js'
const giftShowList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
  }
  let result = await giftListAllService(params)
  console.log(result)
  gifts.value = result.data.items
  total.value = result.data.total
  for (let i = 0; i < gifts.value.length; i++) {
    let gift = gifts.value[i]
    for (let j = 0; j < categorys.value.length; j++) {
      if (gift.categoryId == categorys.value[j].categoryId) {
        gift.categoryName = categorys.value[j].categoryName
      }
    }
  }
}

const deleteGift = async (row) => {
  console.log(row)
  let result = await giftDeleteService(row.giftId)
  giftShowList()
  console.log(result)
  ElMessage.success('删除成功')
}

giftCategoryList()
giftShowList()
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>二手物品管理</span>
      </div>
    </template>
    <el-table :data="gifts" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"> </el-table-column>
      <el-table-column label="物品名称" prop="giftName"></el-table-column>
      <el-table-column label="创建人id" prop="userId"></el-table-column>
      <el-table-column label="分类名称" prop="categoryName"></el-table-column>
      <el-table-column
        label="创建时间"
        prop="createTime"
        sortable
      ></el-table-column>
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
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteGift(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

    <!-- 添加分类弹窗 -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%">
      <el-form
        :model="categoryModel"
        :rules="rules"
        label-width="100px"
        style="padding-right: 30px"
      >
        <el-form-item label="物品名称" prop="categoryName">
          <el-input
            v-model="categoryModel.categoryName"
            minlength="1"
            maxlength="10"
          ></el-input>
        </el-form-item>
        <el-form-item label="分类别名" prop="categoryAlias">
          <el-input
            v-model="categoryModel.categoryAlias"
            minlength="1"
            maxlength="15"
          ></el-input>
        </el-form-item>
        <el-form-item label="分类别名" prop="categoryAlias">
          <el-input
            v-model="categoryModel.categoryAlias"
            minlength="1"
            maxlength="15"
          ></el-input>
        </el-form-item>
        <el-form-item label="分类别名" prop="categoryAlias">
          <el-input
            v-model="categoryModel.categoryAlias"
            minlength="1"
            maxlength="15"
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button
            type="primary"
            @click="title == '添加分类' ? addCategory() : updateCategory()"
          >
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>
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
</style>
