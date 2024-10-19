<script setup>
import { Edit, Delete, Upload } from '@element-plus/icons-vue'
import { ref } from 'vue'

//用户搜索时的信息
const OrderId = ref('')
//商品模型
const goodsModel = ref([
  {
    goodId: 1,
    goodName: null,
  },
])

//订单模型
const goodOrders = ref([
  {
    orderId: 1,
    state: 3,
    receiverId: 1,
    goodId: 1,
    shoppingId: 1,
    payment: 11.0,
    sendTime: null,
    endTime: null,
    createTime: '2023-12-15T10:21:38',
    updateTime: '2023-12-15T10:22:19',
    isDeleted: 0,
  },
])

const goodSt = ref({ orderId: '1', state: '4' })

const pageNum = ref(1) //当前页
const total = ref(20) //总条数
const pageSize = ref(4) //每页条数
//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  goodOrderShowList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  goodOrderShowList()
}

import {
  goodListService,
  goodInfoService,
  goodOrderListService,
  goodSendService,
  getGoodByIdService,
} from '@/api/article.js'
import { ElMessage } from 'element-plus'

//回显物品名称
const getGoodById = async () => {
  let result = await getGoodByIdService(1)
  goodsModel.value = result.data
  console.log(goodsModel)
}
//搜索事件绑定
const onSearch = async () => {
  goodOrderShowList()
}
//订单发货
const goodSend = async (row) => {
  if (row.state === -1 || row.state === 5) {
    ElMessage.error('该订单不可发货')
    return
  } else if (row.state === 4) {
    ElMessage.error('该订单已发货')
    return
  }

  goodSt.value.orderId = row.orderId
  goodSt.value.state = 4
  console.log(goodSt.value)
  ElMessage.success('发货成功')
  let result = await goodSendService(goodSt.value)
  console.log(result)
  goodOrderShowList()
}

//订单取消
const deleteOrder = async (row) => {
  if (row.state === -1 || row.state === 5) {
    ElMessage.error('该订单不可取消')
    return
  }
  goodSt.value.orderId = row.orderId
  goodSt.value.state = -1
  ElMessage.success('订单已取消')
  let result = await goodSendService(goodSt.value)
  console.log(result)
  goodOrderShowList()
}

const goodOrderShowList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    orderId: OrderId.value ? OrderId.value : null,
  }
  let result = await goodOrderListService(params)

  goodOrders.value = result.data.items
  total.value = result.data.total
  //创建一个新的发货地址变量 物品名称变量
  for (let i = 0; i < goodOrders.value.length; i++) {
    let goodorder = goodOrders.value[i]
    for (let j = 0; j < goodsModel.value.length; j++) {
      if (goodorder.goodId == goodsModel.value[j].goodId) {
        goodorder.goodName = goodsModel.value[j].goodName
      }
    }
  }
}
getGoodById()
goodOrderShowList()
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>商品订单管理</span>
      </div>
    </template>
    <el-form inline>
      <el-form-item label="订单ID">
        <el-input v-model="OrderId" placeholder="请输入订单ID" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSearch">搜索</el-button>
        <el-button @click="OrderId = ''">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="goodOrders" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"> </el-table-column>
      <el-table-column label="订单ID" prop="orderId"></el-table-column>
      <el-table-column label="商品名称" prop="goodName"></el-table-column>
      <el-table-column label="消耗积分" prop="payment"></el-table-column>
      <el-table-column label="发货时间" prop="sendTime"></el-table-column>
      <el-table-column label="结束时间" prop="endTime"></el-table-column>
      <el-table-column label="订单状态" prop="state"
        ><template v-slot="{ row }">
          <span>
            {{ row.state == 3 ? '待发货' : '' }}
            {{ row.state == 4 ? '已发货' : '' }}
            {{ row.state == 5 ? '已完成' : '' }}
            {{ row.state == -1 ? '已取消' : '' }}
          </span>
        </template></el-table-column
      >
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button circle plain type="primary" @click="goodSend(row)"
            >发货</el-button
          >
          <el-button circle plain type="danger" @click="deleteOrder(row)"
            >取消</el-button
          >
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
