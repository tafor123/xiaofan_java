<template>
 <div style="width: 80%">
   <div style="margin-bottom: 30px">编辑借书信息</div>
   <el-form :inline="true" :rules="rules" ref="ruleForm" :model="form"  label-width="100px">
     <el-form-item label="标准码" prop="book_no">
       <el-select v-model="form.book_no" clearable filterable placeholder="请选择" @change="selBook">
         <el-option
             v-for="item in books"
             :key="item.id"
             :label="item.book_no"
             :value="item.book_no">
         </el-option>
       </el-select>
     </el-form-item>
     <el-form-item label="图书名称"  prop="book_name">
       <el-input v-model="form.book_name" disabled placeholder="请输入图书名称"></el-input>
     </el-form-item>
     <el-form-item label="所用积分" prop="score">
       <el-input v-model="form.score" disabled placeholder="请输入所用积分"></el-input>
     </el-form-item>
     <el-form-item label="图书数量" prop="nums">
       <el-input v-model="form.nums" disabled placeholder="请输入所用积分"></el-input>
     </el-form-item>
     <br />
     <el-form-item label="会员码" prop="user_no">
       <el-select v-model="form.user_no" clearable filterable placeholder="请选择" @change="selUser">
         <el-option
             v-for="item in users"
             :key="item.id"
             :label="item.username"
             :value="item.username">
         </el-option>
       </el-select>
     </el-form-item>
     <el-form-item label="用户姓名" prop="user_name">
       <el-input v-model="form.user_name" disabled ></el-input>
     </el-form-item>
     <el-form-item label="用户电话" prop="user_phone">
       <el-input v-model="form.user_phone" disabled ></el-input>
     </el-form-item>
     <el-form-item label="用户账户积分" prop="account">
       <el-input v-model="form.account" disabled ></el-input>
     </el-form-item>
     <el-form-item label="借出天数" prop="days">
       <el-input-number v-model="form.days"  :min="1" :max="30" label="所用积分"></el-input-number>
     </el-form-item>

   </el-form>

   <div style="text-align: center; margin-top: 30px">
     <el-button type="primary" @click="save" size="medium">提交</el-button>
<!--     <el-button type="danger">返回列表</el-button>-->
   </div>
 </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: "EditBorrow",
  data(){
    return {
      form: {},
      books: [],
      users:[],
      rules: {
        book_no: [
          {required: true, message: '请输入图书标准码', trigger: 'blur'},
        ],
        user_no: [
          {required: true, message: '请输入会员码', trigger: 'blur'},
        ]
      }
    }
  },
  created(){
    const id = this.$route.query.id
    request.get("/borrow/" + id).then(res => {
      this.form =res.data
    })

    request.get('/book/list').then(res =>{
      this.books=res.data
    })

    request.get('/user/list').then(res =>{
      this.users=res.data.filter(v => v.status)
    })
  },
  methods: {
    save(){
      request.put('/borrow/update',this.form).then(res => {
        if (res.code === '200'){
          this.$notify.success('更新成功')
          this.$router.push("/borrowList")
        }else{
          this.$notify.success(res.msg)
        }

      })
    },
    selBook(){
      const book = this.books.find(v =>v.book_no === this.form.book_no)
      request.get('/book/'+book.id).then(res =>{
        this.form.book_name = res.data.name
        this.form.score = res.data.score
        this.form.nums = res.data.nums
      })
    },
    selUser(){
      const user=this.users.find(v =>v.username === this.form.user_no)
      request.get('/user/'+user.id).then(res =>{
        this.form.user_name = res.data.name
        this.form.user_phone = res.data.phone
        this.form.account = res.data.account
      })
    }
  }
}

</script>

<style scoped>

</style>