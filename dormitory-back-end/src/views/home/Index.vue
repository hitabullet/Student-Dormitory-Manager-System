<template>
  <div class="show" ref="recordFrom">
    <div class="left-card">
      <div class="user-avator">
        <el-avatar
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png">
        </el-avatar>
        <ul class="show-info">
          <li><p>{{ showUserName }}</p></li>
          <li><p>管理员</p></li>
        </ul>
      </div>
      <div class="left-card-content">
        <p>当前学生总数: {{ checkSum + uncheckSum }}人</p>
        <p>当前床位总数: {{ sums }}张</p>
      </div>
    </div>
    <div class="right-card">
      <div class="right-card-content-one">
        <div class="right-card-left-content-one">
          <i class="el-icon-s-custom" style="background-color: #409EFF"></i>
          <div class="content-num">
            <p>{{ checkSum }}</p>
            <p>住校总人数</p>
          </div>
        </div>
      </div>
      <div class="right-card-content-tow">
        <div class="right-card-left-content-three">
          <i class="el-icon-s-flag" style="background-color: #F56C6C"></i>
          <div class="content-num">
            <p>{{ uncheckSum }}</p>
            <p>未住校总人数</p>
          </div>
        </div>
      </div>
      <div class="right-card-content-three">
        <div class="right-card-left-content-tow">
          <i class="el-icon-aim" style="background-color: #67C23A"></i>
          <div class="content-num">
            <p>{{ repairSum }}</p>
            <p>待处理的保修单</p>
          </div>
        </div>
      </div>
      <div class="right-card-content-four">
        <div class="right-card-left-content-four">
          <i class="el-icon-s-grid" style="background-color: #E6A23C"></i>
          <div class="content-num">
            <p>{{ sums - sum }}</p>
            <p>空床位</p>
          </div>
        </div>
      </div>
    </div>
    <v-chart></v-chart>
  </div>
</template>

<script>
import Cookies from 'js-cookie';
import Chart from '../../components/common/chart';

export default {
  name: 'index',

  data() {
    return {
      checkSum: '',
      exitCheckSum: '',
      uncheckSum: '',
      sum: '',
      sums: '',
      repairSum: ''
    };
  },
  methods: {
    getData() {
      //宿舍床位
      this.$axios.get('/api/record/people', {
        params: {}
      }).then(response => {
        this.sum = response.data.id;
        this.sums = response.data.flag;
      }).catch(error => {
      });
      //学生在校、未住校、离校人数
      this.$axios.get('/api/record/leave', {
        params: {}
      }).then(response => {
        this.checkSum = response.data.checkSum;
        this.exitCheckSum = response.data.exitCheckSum;
        this.uncheckSum = response.data.uncheckSum;
      }).catch(error => {
      });
      //报修单数量
      this.$axios.get('/api/record/maintian', {
        params: {}
      }).then(response => {
        this.repairSum = response.data.id;
      }).catch(error => {
      });
    }
  },
  computed: {
    showUserName() {
      return Cookies.get('username');
    }
  },
  mounted() {
    this.getData();
  },
  components: {
    'v-chart': Chart
  }

};

</script>

<style lang="scss" scoped>
.show {
  position: relative;
}

.left-card {
  width: 35%;
  height: 148px;
  font-size: 12px;
  color: #666;
  background: #fff;
  box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
  border-color: rgba(0, 0, 0, .05);
  border-radius: 6px;
  position: absolute;
  top: 10px;
  left: 26px;
}

.user-avator {
  margin: 14px 28px;
}

.el-avatar {
  width: 70px;
  height: 70px;

}

.show-info {
  display: inline-block;
  font-size: 18px;
  text-align: center;
  position: relative;
  top: -10px;
  left: 20px;
  color: black;
}

.show-info li:last-child {
  font-size: 14px;
  color: #8c939d;
}

.left-card-content {
  margin: 5px 20px;
}

.right-card {
  width: 50%;
  position: relative;
  left: 45%;
  top: 8px;
  height: 150px;
  display: inline-block;
}

.right-card-content-one, .right-card-content-tow, .right-card-content-three, .right-card-content-four {
  width: 46%;
  float: left;

}

.right-card-left-content-one, .right-card-left-content-tow, .right-card-left-content-three, .right-card-left-content-four {
  display: flex;
  color: #666;
  background: #fff;
  box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
  border-color: rgba(0, 0, 0, .05);
  border-radius: 6px;
  height: 75px;
  margin: 4px;
}

.el-icon-s-custom, .el-icon-aim, .el-icon-s-flag, .el-icon-s-grid {
  flex: 1;
  align-items: center;
  text-align: center;
  font-size: 26px;
  color: white;
  padding-top: 22px;
}

.content-num {
  flex: 2;
}

.content-num p {
  margin-top: 12px;
}

.content-num p:first-child {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
}

.content-num p:last-child {
  font-size: 12px;
  text-align: center;
  font-weight: lighter;
  color: gray;
}

.cleaxbox {
  clear: both;
  line-height: 0;
  font-size: 0;
}

.v-chart {
  box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
}
</style>
