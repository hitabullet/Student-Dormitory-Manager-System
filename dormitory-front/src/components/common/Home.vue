<template>
  <el-container>
    <el-header>
      <Navigation></Navigation>
    </el-header>
    <el-main>
      <transition name="move" mode="out-in">
        <keep-alive>
          <router-view></router-view>
        </keep-alive>
      </transition>
    </el-main>
    <el-footer>
         <Footer></Footer>
    </el-footer>
  </el-container>
</template>

<script>
import Navigation from '../public/Navigation'
import Footer from '../public/Footer'

export default {
  name: "Home",
  components: {
    Navigation,
    Footer
  }
}
</script>

<style lang="scss" scoped>
.el-header {
  height: 61px !important; /* 高度 */
  background: #ffffff; /* 背景颜色 */
}
.el-main {
  flex-grow:1 !important;
  background: rgb(236, 245, 255);
  height: 91vh;
}
.el-footer {
  height: 310px !important;
  background: -webkit-gradient(linear, 0 0, 0 100%, from(rgb(236, 245, 255)), to(transparent));
}
</style>
