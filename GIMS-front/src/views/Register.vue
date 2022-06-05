<template>
  <div id="div1">

    <el-steps :space="200" :active="active" finish-status="success" align-center>
      <el-step title="确定用户"></el-step>
      <el-step title="设置密码"></el-step>
      <el-step title="绑定邮箱"></el-step>
      <el-step title="设置头像"></el-step>
    </el-steps>

    <el-form ref="registerForm" :model="registerForm" :rules="rules" label-width="120px">

      <div class="div2" v-show="active===0">
        <el-form-item label="角色id" prop="userId">
          <el-input v-model="userId" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="请选择账号类型" prop="typeId">
          <el-select v-model="registerForm.typeId">
            <el-option label="学生" :value="0"></el-option>
            <el-option label="教师" :value="1"></el-option>
            <el-option label="招聘者" :value="2"></el-option>
            <el-option label="管理员" :value="3"></el-option>
          </el-select>
        </el-form-item>
      </div>

      <div class="div2" v-show="active===1">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="registerForm.username" auto-complete="off"></el-input>
        </el-form-item>
      </div>

      <div class="div2" v-show="active===2">
        <el-form-item label="密码" prop="password">
          <el-input v-model="registerForm.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="rePass">
          <el-input v-model="rePass" type="password"></el-input>
        </el-form-item>
      </div>

      <div class="div2" v-show="active===3">
        <el-form-item label="绑定邮箱" prop="email">
          <el-input v-model="registerForm.email"></el-input>
        </el-form-item>
      </div>

      <div class="div2" v-show="active===4">
        <el-form-item label="" prop="avatar">
          <el-image :src="registerForm.avatar">
            <div slot="error" class="image-slot">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
          <el-input v-model="registerForm.avatar" style="margin-top: 100px"></el-input>
        </el-form-item>
      </div>


      <div class="div2" v-show="active===5">
        <el-form-item>
          <el-result icon="success" title="已成功注册" subTitle="请点击窗口外回到登录界面"></el-result>
        </el-form-item>
      </div>

      <el-form-item class="div3" v-show="active<4">
        <el-button type="primary" @click="next()">下一步</el-button>
      </el-form-item>

      <el-form-item class="div3" v-show="active===4">
        <el-button type="success" @click="next()">提交</el-button>
      </el-form-item>

    </el-form>

  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        callback();
      }
    };
    const validateRePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.rePass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      registerForm: {
        typeId: undefined,
        username: '',
        password: '',
        email: '',
        avatar: ''
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          {validator: validatePassword, trigger: 'blur'}
        ],
        rePass: [
          {validator: validateRePass, trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'}
        ]
      },
      active: 0,
      rePass: '',
      userId: undefined
    }
  },
  methods: {
    next() {
      if (this.active === 0) {
        this.checkUser()
      } else if (this.active === 1) {
        this.checkUsername()
      } else if (this.active === 2) {
        this.checkPass()
      } else if (this.active === 3) {
        this.checkEmail()
      } else if (this.active === 4) {
        this.submitForm()
      }
    },
    getInfo(user) {
      this.$axios.get('/sys/info/' + user + '/' + this.userId).then(res => {
        this.active++
      })
    },
    checkUser() {
      let user = undefined
      if (this.registerForm.typeId === 0) {
        user = 'student'
      } else if (this.registerForm.typeId === 1) {
        user = 'teacher'
      } else if (this.registerForm.typeId === 2) {
        user = 'recruiter'
      } else if (this.registerForm.typeId === 3) {
        user = 'admin'
      }
      this.getInfo(user)
    },
    checkUsername() {
      if (this.registerForm.username === '') {
        this.$message({
          showClose: true,
          message: '用户名不能为空',
          type: 'error',
          onClose: () => {
          }
        });
      } else {
        this.$axios.post('/sys/info/checkUsername', this.registerForm.username).then(res => {
          this.active++
        })
      }
    },
    checkPass() {
      if (this.registerForm.password === '' || this.checkPass === '') {
        this.$message({
          showClose: true,
          message: '密码不能为空',
          type: 'error',
          onClose: () => {
          }
        });
      } else if (this.registerForm.password !== this.rePass) {
        this.$message({
          showClose: true,
          message: '两次输入的密码不相同',
          type: 'error',
          onClose: () => {
          }
        });
      } else {
        this.active++
      }
    },
    checkEmail() {
      if (this.registerForm.email === '') {
        this.$message({
          showClose: true,
          message: '邮箱不能为空',
          type: 'error',
          onClose: () => {
          }
        });
      } else {
        this.active++
      }
    },
    submitForm() {
      this.$axios.post('/sys/register/' + this.userId, this.registerForm)
      this.active++
    }
  }
}
</script>

<style scoped>

#div1 {
  padding: 50px;
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
  background-color: #00DBDE;
  background-image: linear-gradient(90deg, #00DBDE 0%, #FC00FF 100%);
}

.div2 {
  margin-top: 200px;
}

.div3 {
  margin-top: 100px;
  text-align: center;
}

.el-steps {
  width: 100%;
}

.el-form-item {
  width: 700px;
}

.el-image {
  width: 300px;
  height: 300px;
  display: block;
  margin: 0 auto;
  text-align: center
}

</style>