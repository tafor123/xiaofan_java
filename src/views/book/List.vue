<template>
  <div>
    <!--  搜索表单  -->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="请输入图书名称" v-model="params.name"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="请输入图书标准码" v-model="params.book_no"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"> <i class="el-icon-search"></i>搜索</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"> <i class="el-icon-refresh"></i>重置</el-button>
    </div>

    <el-table :data="tableDate" stripe row-key="id"  default-expand-all>
      <el-table-column prop="id" label="编号" width="80"></el-table-column>
      <el-table-column prop="name" label="图书" width="100"></el-table-column>
      <el-table-column prop="description" label="描述" width="200"></el-table-column>
      <el-table-column prop="publish_date" label="出版时间" width="100"></el-table-column>
      <el-table-column prop="author" label="作者" width="50"></el-table-column>
      <el-table-column prop="publish" label="出版社" width="100"></el-table-column>
      <el-table-column prop="category" label="分类" width="100"></el-table-column>
      <el-table-column prop="book_no" label="标准码" width="100"></el-table-column>
      <el-table-column prop="nums" label="数量" width="40"></el-table-column>
      <el-table-column prop="score" label="积分" width="40"></el-table-column>
      <el-table-column prop="cover" label="封面" width="100">
        <template v-slot="scope">
          <el-image :src="scope.row.cover" :preview-src-list="[scope.row.cover]"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="createtime" label="创建时间" width="100"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间" width="100"></el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
<!--          scope.row就是当前行-->
          <el-button type="success" @click="$router.push('/editBook?id=' + scope.row.id)">编辑</el-button>
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
  name: 'BookList',
  data(){
    return{
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      tableDate: [],
      total: 0,
      params:{
        pageNum: 1,
        pageSize: 10,
        name: '',
        book_no: ''
      },
      rules: {
        name:[
          {required: true, message: '请输入新的图书名称', trigger: 'blur'},
          {min: 3, max: 10, message:'长度在3到10个字符', trigger: 'blur'}
        ]
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

      request.get('/book/page',{
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
        name: '',
        book_no: ''
      }
      this.load()
    },
    handleCurrentChange(pageNum){
      //点击分页按钮触发
      this.params.pageNum=pageNum
      this.load()
    },
    del(id){
      request.delete("/book/delete/" +id).then(res =>{
        if(res.code === '200'){
          this.$notify.success("删除成功")
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