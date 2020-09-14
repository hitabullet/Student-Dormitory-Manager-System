<template>
  <div id="app">
    <router-view v-if="isRouterAlive"></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  provide () {    //父组件中通过provide来提供变量，在子组件中通过inject来注入变量。
    return {
      reload: this.reload
    }
  },
  data() {
    return{
      isRouterAlive: true                    //控制视图是否显示的变量
    }
  },
  methods: {
    reload () {
      this.isRouterAlive = false;            //先关闭，
      this.$nextTick(function () {
        this.isRouterAlive = true;         //再打开
      })
    }
  },
}
</script>

<style>
#app {
  font-family: "PingFang SC", "Microsoft Yahei", sans-serif;
  box-sizing: border-box;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;

}

html,
body,
#app {
  width: 100%;
  height: 100%;

}

body {
  overflow-x: hidden;
}

.el-main, .el-header, .el-footer {
  width: 100%;
  padding: 0 !important;
}

.el-main {
  overflow: hidden !important;
}
</style>
