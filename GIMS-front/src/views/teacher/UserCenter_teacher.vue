<template>
  <div>
    <el-form>
      <el-form :model="editForm" :rules="rules" ref="editForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="姓名" prop="teacherName" label-width="100px">
          <el-input v-model="editForm.teacherName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="学校" prop="schoolName" label-width="100px">
          <el-input v-model="editForm.schoolName" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('editForm')">提交</el-button>
        </el-form-item>
      </el-form>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "UserCenter_teacher",
  data() {
    return {
      editForm: {
        teacherId: undefined,
        teacherName: '',
        schoolName: ''
      },
      rules: {
        teacherName: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ]
      },
      accountId: undefined
    }
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() {
      this.$axios.get("/sys/userInfo").then(res => {
        this.accountId = res.data.data.accountId
        this.getAccountInfo()
      })
    },
    getAccountInfo() {
      this.$axios.get('/teacher/detail/' + this.accountId).then(res => {
        this.editForm = res.data.data
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/teacher/modify', this.editForm).then(res => {
            this.$alert(res.data.msg, '提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.$refs[formName].resetFields();
              }
            })
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>
.el-form {
  width: 420px;
  margin: 50px auto;
}
</style>