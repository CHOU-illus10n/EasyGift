<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
//文章分类数据模型
const gifts = ref([
  {
    giftId: '2',
    userId: '1',
    giftName: null,
    description: null,
    createTime: '2023-12-13T16:06:55',
    state: null,
    dealTime: '',
  },
])

const orders = ref([
  {
    giftOrderId: '1',
    giftOrderStatus: 3,
    giftId: '1',
    giftName: '鼠标',
    senderId: '1',
    receiverId: '2',
    incrPoint: 10,
    createTime: '2023-12-14T13:55:21',
    updateTime: '2023-12-14T13:55:21',
    isDeleted: 1,
  },
])

const pageNum = ref(1) //当前页
const total = ref(20) //总条数
const pageSize = ref(4) //每页条数
//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  OrderList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  OrderList()
}

//回显礼物列表
import {
  giftGetService,
  giftOrderService,
  giftOrderDeleteService,
} from '@/api/article.js'
const giftList = async () => {
  let result = await giftGetService()
  gifts.value = result.data
  console.log(gifts)
}

//声明一个异步的函数
const OrderList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
  }
  let result = await giftOrderService(params)
  console.log(result)
  orders.value = result.data.items
  total.value = result.data.total
  for (let i = 0; i < orders.value.length; i++) {
    let order = orders.value[i]
    for (let j = 0; j < gifts.value.length; j++) {
      if (order.giftId == gifts.value[j].giftId) {
        order.giftName = gifts.value[j].giftName
        order.dealTime = gifts.value[j].dealTime
      }
    }
  }
  console.log(orders)
}
giftList()
OrderList()

//删除礼物
const deleteOrder = async (row) => {
  console.log(row)
  let result = await giftOrderDeleteService(row.giftOrderId)
  OrderList()
  ElMessage.success('删除成功')
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>交易记录管理</span>
      </div>
    </template>
    <el-table :data="orders" style="width: 100%" sort>
      <el-table-column label="序号" width="100" type="index"> </el-table-column>
      <el-table-column label="物品名称" prop="giftName"></el-table-column>
      <el-table-column label="创建人ID" prop="senderId"></el-table-column>
      <el-table-column label="接收人ID" prop="senderId"></el-table-column>
      <el-table-column label="增加积分" prop="incrPoint"></el-table-column>
      <el-table-column
        label="交易时间"
        prop="dealTime"
        sortable
      ></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteOrder(row)"
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
