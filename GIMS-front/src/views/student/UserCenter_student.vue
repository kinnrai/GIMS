<template>
  <div>
    <el-form>
      <el-form :model="editForm" :rules="rules" ref="editForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="姓名" prop="studentName" label-width="100px">
          <el-input v-model="editForm.studentName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex" label-width="100px">
          <el-radio-group v-model="editForm.sex">
            <el-radio :label="0">男</el-radio>
            <el-radio :label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker type="date" placeholder="选择日期" v-model="editForm.birthday" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="老师" prop="teacherName" label-width="100px">
          <el-input v-model="editForm.teacherName" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="班级" prop="className" label-width="100px">
          <el-input v-model="editForm.className" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="学校" prop="schoolName" label-width="100px">
          <el-input v-model="editForm.schoolName" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="majorName" label-width="100px">
          <el-input v-model="editForm.majorName" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="学分" prop="credit" label-width="100px">
          <el-input v-model="editForm.credit" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="简历" prop="resume" label-width="100px">
          <el-input v-model="editForm.resume" auto-complete="off"></el-input>
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
  name: "UserCenter_student",
  data() {
    return {
      editForm: {
        studentId: undefined,
        studentName: '',
        sex:undefined,
        birthday:undefined,
        teacherName:'',
        className:'',
        schoolName:'',
        majorName:'',
        credit:undefined,
        resume:''
      },
      rules: {
        studentName: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ],
        sex:[
          {required:true,message:'请选择性别',trigger:'change'}
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
      this.$axios.get('/student/detail/' + this.accountId).then(res => {
        this.editForm = res.data.data
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/student/modify', this.editForm).then(res => {
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