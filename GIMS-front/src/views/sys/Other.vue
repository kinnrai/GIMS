<template>
  <div>
    <el-form :model="editForm" status-icon ref="editForm" label-width="100px">
      <el-form-item>
        <el-avatar shape="square" :size="300" :src="editForm.avatar"></el-avatar>
      </el-form-item>
      <el-form-item label="头像URL" prop="avatar" label-width="100px">
        <el-input v-model="editForm.avatar" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱地址" prop="email" label-width="100px">
        <el-input v-model="editForm.email" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('editForm')">提交</el-button>
        <el-button @click="resetForm('editForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Other",
  data() {
    return {
      editForm: {
        avatar: undefined,
        email: undefined
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
      this.$axios.get('/account/info/' + this.accountId).then(res => {
        this.editForm = res.data.data
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/account/update', this.editForm).then(res => {
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
  }
}
</script>

<style scoped>
.el-form {
  width: 420px;
  margin: 50px auto;
}

.el-form-item {
  margin-top: 10px;
}
</style>