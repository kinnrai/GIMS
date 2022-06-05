<template>
  <div>
    <strong>毕业生管理信息系统</strong>
    <div class="header-avatar">
      <el-avatar :src="userInfo.avatar" size="small"></el-avatar>
      <el-dropdown>
      <span class="el-dropdown-link">
        {{ userInfo.username }}<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <router-link to="/sys/userSecurity">
              账号安全
            </router-link>
          </el-dropdown-item>
          <el-dropdown-item @click.native="logout">
            退出
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "HeaderMenu",
  methods: {
    getUserInfo() {
      this.$axios.get("/sys/userInfo").then(res => {
        this.userInfo = res.data.data
      })
    },
    logout(){
      this.$axios.post("/logout").then(res=>{
        localStorage.clear()
        sessionStorage.clear()
        this.$store.commit("resetState")
        this.$router.push("/login")
      })
    }
  },
  data() {
    return {
      userInfo: {
        id: '',
        username: '',
        avatar: ''
      }
    }
  },
  created() {
    this.getUserInfo()
  }
}
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
}

.el-icon-arrow-down {
  font-size: 12px;
}

.header-avatar {
  float: right;
  width: 130px;
  display: flex;
  align-items: center;
  justify-content: space-around;
}

a {
  text-decoration: none;
}
a:link {
  font-size: 12px;
  color: #000000;
}
a:visited {
  font-size: 12px;
  color: #000000;
}
a:hover {
  font-size: 12px;
  color: #999999;
}

.router-link-active {
  text-decoration: none;
}

</style>