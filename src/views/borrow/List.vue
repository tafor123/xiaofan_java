<template>
  <div>
    <!--  搜索表单  -->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="请输入图书名称" v-model="params.book_name"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="请输入图书标准码" v-model="params.book_no"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="请输入用户姓名" v-model="params.user_name"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"> <i class="el-icon-search"></i>搜索</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"> <i class="el-icon-refresh"></i>重置</el-button>
    </div>

    <el-table :data="tableDate" stripe row-key="id"  default-expand-all>
      <el-table-column prop="id" label="编号" width="80"></el-table-column>
      <el-table-column prop="book_name" label="图书" width="100"></el-table-column>
      <el-table-column prop="book_no" label="标准码" width="100"></el-table-column>
      <el-table-column prop="user_no" label="会员名" width="100"></el-table-column>
      <el-table-column prop="user_name" label="用户姓名" width="100"></el-table-column>
      <el-table-column prop="user_phone" label="用户电话" width="100"></el-table-column>
      <el-table-column prop="score" label="所用积分" width="100"></el-table-column>
      <el-table-column prop="createtime" label="借出时间" width="100"></el-table-column>
      <el-table-column prop="status" label="借出状态" width="100"></el-table-column>
      <el-table-column prop="days" label="借出天数" width="100"></el-table-column>
      <el-table-column prop="return_date" label="归还日期" width="100"></el-table-column>
      <el-table-column prop="note" label="过期提醒" width="100">
        <template v-slot="scope">
          <el-tag type="success" v-if="scope.row.note === '正常' ">{{scope.row.note}}</el-tag>
          <el-tag type="primary" v-if="scope.row.note === '即将到期' ">{{scope.row.note}}</el-tag>
          <el-tag type="warning" v-if="scope.row.note === '已到期' ">{{scope.row.note}}</el-tag>
          <el-tag type="danger" v-if="scope.row.note === '已过期' ">{{scope.row.note}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column  label="管理">
        <template v-slot="scope">
          <el-button type="primary" @click="returnBooks(scope.row)" v-if="scope.row.status === '已借出'">还书</el-button>
        </template>
      </el-table-column>
<!--      <el-table-column prop="updatetime" label="更新时间" width="100"></el-table-column>-->
      <el-table-column label="操作">
        <template v-slot="scope">
<!--          scope.row就是当前行-->
<!--          <el-button type="success" @click="$router.push('/editBorrow?id=' + scope.row.id)">编辑</el-button>-->
          <el-popconfirm
              style="margin-left: 4px"
              title="你确定删除这行数据吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>

    </el-table>

    <!--  分页  -->
    <div style="margin-top: 20px">
      <el-pagination
          background
          :current-page="params.pageNum"
          :page-size="params.pageSize"
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
          :total="total">
      </el-pagination>
    </div>

  </div>
</template>


<script>
import request from "@/utils/request";
import Cookies from 'js-cookie'
export default {
  name: 'BorrowList',
  data(){
    return{
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      tableDate: [],
      total: 0,
      params:{
        pageNum: 1,
        pageSize: 10,
        book_name: '',
        book_no: '',
        user_name: ''
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load(){
      // fetch('http://localhost:9090/book/list').then(res => res.json()).then(res =>{
      //   console.log(res)
      //   this.tableDate=res
      // })

      request.get('/borrow/page',{
        params: this.params
      }).then(res =>{
        if(res.code === '200'){
          this.tableDate = res.data.list
          this.total = res.data.total
        }
      })
    },
    reset(){
      this.params={
        pageNum: 1,
        pageSize: 10,
        book_name: '',
        book_no: '',
        user_name: ''
      }
      this.load()
    },
    handleCurrentChange(pageNum){
      //点击分页按钮触发
      this.params.pageNum=pageNum
      this.load()
    },
    del(id){
      request.delete("/borrow/delete/" +id).then(res =>{
        if(res.code === '200'){
          this.$notify.success("删除成功")
          this.load()
        }else {
          this.$notify.error(res.msg)
        }
      })
    },
    returnBooks(row){
      request.post("/borrow/saveRetur" , row).then(res =>{
        if(res.code === '200'){
          this.$notify.success("还书成功")
          this.load()
        }else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>