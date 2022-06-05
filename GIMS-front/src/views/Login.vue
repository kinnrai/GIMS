<template>
  <div>
    <div id="background">
      <el-row type="flex" class="row-bg" justify="center">
        <el-col :span="6">
          <div class="grid-content bg-purple">
            <h2>欢迎来到毕业生管理信息系统</h2>
            <el-image :src="require('../assets/icon.png')" style="height: 180px;width: 180px"></el-image>
          </div>
        </el-col>

        <el-col :span="1">
          <div class="grid-content bg-purple-light">
            <el-divider direction="vertical"></el-divider>
          </div>
        </el-col>

        <el-col :span="6">
          <div class="grid-content bg-purple">
            <el-form ref="loginForm" :model="loginForm" :rules="rules" label-width="80px">
              <el-form-item label="账号" prop="username">
                <el-input v-model="loginForm.username"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input v-model="loginForm.password" type="password"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
                <el-button @click="resetForm('loginForm')">重置</el-button>
              </el-form-item>
            </el-form>
          </div>

          <a href="javascript:void (0)" id="register" @click="drawer=true">没有账号?点击注册</a>

        </el-col>
      </el-row>
    </div>

    <el-drawer
        title="注册"
        :visible.sync="drawer"
        :direction="direction"
        :with-header="false"
        size="49%"
        :before-close="handleClose">
        <Register/>
    </el-drawer>
  </div>
</template>

<script>
import qs from "qs";
import Register from "@/views/Register";

export default {
  name: "Login",
  components: {Register},
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
        token: ''
      },
      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ]
      },
      drawer: false,
      direction: 'rtl',
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
            if (valid) {
              this.$axios.post('/login?' + qs.stringify(this.loginForm)).then(res => {
                const jwt = res.headers['authorization']
                this.$store.commit('SET_TOKEN', jwt)
                this.$router.push("/index")
              })
            } else {
              alert("提交格式错误!");
            }
          }
      )
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    }
  }
}
</script>

<style scoped>

.el-row {
  height: 100vh;
  display: flex;
  align-items: center;
  text-align: center;
}

.el-divider {
  height: 200px;
}

.el-form-item {
  width: 380px;
}

#background {
  background: url("../assets/LoginBackground.jpg");
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
}

#register {
  font-size: 15px;
  color: #42b983;
}

</style>