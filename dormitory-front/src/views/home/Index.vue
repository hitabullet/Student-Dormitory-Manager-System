<template>
  <div class="block">
    <el-carousel trigger="click" :interval="5000" :height="dataHeight" arrow="always">
      <el-carousel-item v-for="item in imgList" :key="item.name">
        <img :src="item.src" style="height:100%;width:100%;" alt="图片丢失了" :title="item.title"/>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  name: "Index",
  props: {
    dataHeight: {
      type: String,
      default: '655px'
    }
  },
  data() {
    return {
      imgList: [
        {
          name: "图书馆",
          src: require("../../assets/img/scenery-one.png"),
          title: "这是图书馆早晨的明媚天气"
        },
        {
          name: "宿舍",
          src: require("../../assets/img/scenery-three.webp"),
          title: "这是宿舍傍晚的晚霞"
        },
        {
          name: "学校",
          src: require("../../assets/img/scenery-four.webp"),
          title: "这是学校晚上的雨景"
        },
        {
          name: "宿舍2",
          src: require("../../assets/img/scenery-tow.jpg"),
          title: "这是宿舍"
        },
      ]
    };
  },
}

</script>

<style lang="scss" scoped>

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

</style>
