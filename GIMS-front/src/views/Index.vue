<template>
  <div>
    <div style="margin-left: 200px;margin-right: 200px">
      <template>
        <el-carousel :interval="2000" type="card">
          <el-carousel-item v-for="(image,index) in images" :key="index">
            <el-image :src="image"></el-image>
          </el-carousel-item>
        </el-carousel>
      </template>
    </div>
    <h2 id="welcome">欢迎来到毕业生管理信息系统!</h2>
    <h3 id="hello">你好,{{username}}</h3>
  </div>
</template>

<script>
export default {
  name: "Index",
  data(){
    return{
      username:'',
      images:[
          "https://www.aust.edu.cn/images/201705.jpg",
          "https://www.aust.edu.cn/images/IMG_2805.jpg",
          "https://www.aust.edu.cn/images/201706.jpg",
          "https://www.aust.edu.cn/images/201721.jpg",
          "https://www.aust.edu.cn/images/201704.jpg",
          "https://www.aust.edu.cn/images/201720.jpg",
          "https://www.aust.edu.cn/images/201724.jpg",
          "https://www.aust.edu.cn/images/201725.jpg",
          "https://www.aust.edu.cn/images/IMG_7553-2.jpg",
          "https://www.aust.edu.cn/images/IMG_2827.jpg",
          "https://www.aust.edu.cn/images/xinxiaoquniaokantu.jpg"
      ]
    }
  },
  created() {
    this.getAccountInfo()
  },
  methods:{
    getAccountInfo(){
      this.$axios.get('sys/userInfo').then(res=>{
        this.username=res.data.data.username
      })
    }
  }
}
</script>

<style scoped>

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

#welcome{
  font-size: 80px;
  font-family:"华文行楷",sans-serif;
  font-weight: bold;
  color: darkorange;
  text-align: center;
  margin-top: 120px;
}

#hello{
  font-size: 40px;
  font-family:"方正舒体",sans-serif;
  font-weight: lighter;
  color: green;
  text-align: center;
  margin-top: 120px;
}

</style>