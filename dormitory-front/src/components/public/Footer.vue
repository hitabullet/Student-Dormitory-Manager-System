<template>
  <div class="footer">
    <div class="logo"><img src="../../assets/img/chamber.png" width="250px" height="250px" title="卫生靠大家"></div>
    <div class="copyright">
      地址：银河系地球007号 <br>

      邮政编码：12138 <br>

      电话：18994081376 <br>

      电子信箱：1715607986@qq.com<br>

      银河警备00000001号-1<br>

      站长统计 | 今日浏览量[]<br>
    </div>
    <div class="icon">
      <i class="icon iconfont icon-weixin"></i>
      <i class="icon iconfont icon-qq"></i>
      <img src="../../assets/img/qq.png" alt="">
    </div>
    <el-backtop></el-backtop>
  </div>
</template>

<script>
export default {
  name: "Footer"
}
</script>

<style lang="scss" scoped>
.footer {
  margin-top: 100px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 5vw;
}

.logo {
  flex-grow: 1;
}

.copyright {
  flex-grow: 2;
  text-align: left;
  margin: auto auto;
  padding-left: 250px;
}

.icon {

}

.icon i {
  font-size: 36px;
  margin-left: 20px;
  color: #2f82ff;
  cursor: pointer;
}

.icon i:first-child:hover + .icon img:hover {
  display: block;
}

.icon img {
  width: 100%;
  height: auto;
  padding: 2rem 0;
  display: none;
  z-index: 9999;
}
</style>
