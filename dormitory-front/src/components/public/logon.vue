<template>
  <div class="room">
    <div v-show="!showName" >
      <div class="logon" @click="hrefone">注册</div>
      <div class="record" @click="hreftwo">登录</div>
    </div>
    <el-dropdown v-show="showName" class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{ showStudentNo }}
                        <i class="el-icon-caret-bottom"></i>
                    </span>

      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>
          <el-button type="text"
                     style="font-size: 13px;color: #606266">
            修改资料
          </el-button>
        </el-dropdown-item>
        <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "logon",
  data() {
    return {
      showName: false,
    }
  },
  methods: {
    hrefone() {
      this.$router.push('/Register');
    },
    hreftwo() {
      this.$router.push('/Login');
    },
    // 用户名下拉菜单选择事件
    handleCommand(command) {
      if (command == 'loginout') {

        this.$cookies.remove('studentNo')
        this.$router.push('/Login');
      }
    },
  },
  created(){
    if (this.$cookies.isKey('studentNo')) {
      this.showName = !this.showName
    }
  },
  computed: {
    showStudentNo() {
      return this.$cookies.get('studentNo')
    }
  }
}
</script>

<style lang="scss" scoped>

.logon, .record {
  cursor: pointer;
  display: inline-block;
  border: 1px solid #2f82ff;
  background-color: #2f82ff;
}

.logon {
  vertical-align: middle;
  width: 76px;
  height: 36px;
  line-height: 34px;
  border: 1px solid #0076fe;
  text-align: center;
  background-color: #0076fe;
  font-size: 14px;
  border-radius: 28px;
  color: #fff;
  box-sizing: border-box;
}

.record {
  vertical-align: middle;
  width: 76px;
  height: 36px;
  line-height: 34px;
  border: 1px solid darkgrey;
  text-align: center;
  background-color: #ffffff;
  font-size: 14px;
  border-radius: 28px;
  color: #333333;
  box-sizing: border-box;
  margin-left: 10px;
}

.record:hover {
  border: 1px solid #0076fe;
  color: #0076fe;

}

</style>
