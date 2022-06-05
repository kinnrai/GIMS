<template>
  <div>
    <el-form :model="passForm" status-icon :rules="rules" ref="passForm" label-width="100px">
      <el-form-item label="旧密码" prop="oldPass">
        <el-input type="password" v-model="passForm.oldPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPass">
        <el-input type="password" v-model="passForm.newPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input type="password" v-model="passForm.checkPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('passForm')">提交</el-button>
        <el-button @click="resetForm('passForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "UserSecurity",
  data() {
    const validateOldPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入旧密码'));
      } else {
        callback();
      }
    };
    const validateNewPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入新密码'));
      } else {
        callback();
      }
    };
    const validateCheckPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.passForm.newPass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      passForm: {
        oldPass: '',
        newPass: '',
        checkPass: ''
      },
      userInfo: {
        id: '',
        username: '',
        avatar: ''
      },
      rules: {
        oldPass: [
          {validator: validateOldPass, trigger: 'blur'}
        ],
        newPass: [
          {validator: validateNewPass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validateCheckPass, trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    getUserInfo() {
      this.$axios.get("/sys/userInfo").then(res => {
        this.userInfo = res.data.data
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("/sys/updatePass", this.passForm).then(res => {
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
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created() {
    this.getUserInfo()
  }
}
</script>

<style scoped>
.el-form{
  width: 420px;
  margin: 50px auto;
}
</style>