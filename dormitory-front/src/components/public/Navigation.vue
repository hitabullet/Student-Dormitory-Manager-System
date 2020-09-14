<template>
  <nav>
    <div class="logo">
      <img src="../../assets/img/chamber.png" alt="" width="250px" height="250px" title="学校是我家">
    </div>
    <el-menu mode="horizontal" class="nav-menu"
             :default-active="$route.path"
             router
             text-color="#333"
             active-text-color="#2f82ff">
      <el-menu-item index="/Home">首页</el-menu-item>
      <el-menu-item index="/InformationEntry">信息录入</el-menu-item>
      <el-menu-item index="/GetAccommodation">宿舍入住</el-menu-item>
      <el-menu-item index="/DormitoryRepair">宿舍报修</el-menu-item>
      <el-menu-item index="5"><a href="https://www.ncc.edu.cn">我的学校</a></el-menu-item>
    </el-menu>
    <logon></logon>
    <div class="burger">
      <div class="top-line"></div>
      <div class="middle-line"></div>
      <div class="bottom-line"></div>
    </div>
  </nav>
</template>

<script>
import {mokuai} from '.././common/index';
import logon from "./logon";

export default {
  name: "navigation",
  methods: {
    _mokuai() {
      mokuai();
    },
  },
  components: {
    logon
  },
  mounted() {
    this._mokuai();
  }
}
</script>

<style src="../../assets/css/style.css"></style>

<style lang="scss" scoped>
.el-menu-item.is-active {
  background-color: transparent !important;
  color: #333;

  span {
    color: #333 !important;
  }
}


.el-menu {
  margin-left: -78px;
}
.el-menu--horizontal > .el-menu-item {

  border-bottom: none;

}

.el-menu--horizontal > .el-menu-item.is-active {
  border-bottom: none;
}

a {
  text-decoration: none;
}


</style>
