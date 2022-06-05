<template>
  <div>
    <el-container>
      <el-aside width="200px">
        <SideMenu/>
      </el-aside>
      <el-container>
        <el-header>
          <HeaderMenu/>
        </el-header>
        <el-main>
          <Tabs/>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import SideMenu from "@/views/inc/SideBar";
import HeaderMenu from "@/views/inc/HeaderBar";
import Tabs from "@/views/inc/Tabs";

export default {
  name: "Home",
  components: {
    SideMenu,
    HeaderMenu,
    Tabs
  }
}
</script>

<style scoped>
.el-container {
  padding: 0;
  margin: 0;
  height: 100vh;
}

.el-header {
  background-color: #85FFBD;
  background-image: linear-gradient(45deg, #85FFBD 38%, #FFFB7D 100%);
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: left;
  line-height: 200px;
}

.el-main{
  padding: 0;
}

</style>