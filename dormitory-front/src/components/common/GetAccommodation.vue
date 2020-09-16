<template>
  <div class="GetAcc">
    <div class="breadcrumb">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>宿舍入住</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="chamber">
      <div class="handle-box">
        <el-input v-model="chamberForm.chamber" placeholder="宿舍" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      </div>
      <el-table :data="chamberList"
                style="width: 100%"
                border
                @sort-change="sortChange"
                :sort-orders="['ascending', 'descending']"
                :default-sort="{prop: 'id', order: 'ascending'}"
      >
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column label="宿舍" sortable="custom" prop="chamber"
                         align="center">
        </el-table-column>
        <el-table-column label="已住人数" align="center" prop="people">
        </el-table-column>
        <el-table-column label="容纳人数" prop="peoples" align="center">
        </el-table-column>
        <el-table-column label="操作" align="center" >
          <template slot-scope="scope" v-if="btnChangeEnable">
            <el-button size="mini"
                       v-if="scope.row.people < 6"
                       @click="choose(scope.row)"
                       style="background: #409EFF;color: white">
              选择
            </el-button>
            <el-button v-else="scope.row.people === 6"  type="primary" size="mini"
                       disabled>已满</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          style="float: right;margin-top: 10px"
          background
          layout="prev,pager,next"
          :page-size="pageInfo.pageSize"
          :total="pageInfo.total"
          :current-page.sync="pageInfo.pageNum"
          @current-change="page">
      </el-pagination>
    </div>
    <div class="prompt">
      <div class="marquee-block">
        <div class="marquee">您已完成宿舍入住</div>
        <div class="marqueeT">您已完成宿舍入住</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "GetAccommodation",
  inject: ['reload'],   //注入App里的reload方法
  data() {
    return {
      btnChangeEnable: false,
      pageInfo: {
        pageNum: 1,
        total: 10,
        pageSize: 6
      },
      chamberList: [],
      home: {
        chamber: '',
        people: '',
        peoples: '',
      },
      chamberForm: {
        chamber: '',
      },
    }
  },
  methods: {
    /*分页*/
    page() {
      this.$axios.get("/api/GetChamber/page", {
        params: {
          pageNum: this.pageInfo.pageNum,
          pageSize: this.pageInfo.pageSize
        }
      }).then(resp => {
        this.chamberList = resp.data.list;
        this.pageInfo.total = resp.data.total;
      })
    },
    /*搜索并且分页*/
    search() {
      this.$axios.get("/api/GetChamber/page", {
        params: {
          condition: {
            chamber: this.chamberForm.chamber,
          },
          pageNum: this.pageInfo.pageNum,
          pageSize: this.pageInfo.pageSize
        }
      }).then(resp => {
            this.chamberList = resp.data.list;
            this.pageInfo.total = resp.data.total;
          }
      )
    },
    /*降序排列*/
    sortChange(column) {
      if (column.order === 'descending') {
        this.$axios.get("/api/GetChamber/pageDesc", {
          params: {
            pageNum: this.pageInfo.pageNum,
            pageSize: this.pageInfo.pageSize
          }
        }).then(resp => {
              this.chamberList = resp.data.list;
              this.pageInfo.total = resp.data.total;
            }
        )
      } else {
        this.page();
      }
    },
    /*删除*/
    choose(row) {
      this.$confirm('确定选择入住该宿舍？该操作无法回退！', '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post('/api/GetChamber/choose/' + row.id).then(resp => {
          if (resp.data) {
            this.$message({
              type: 'success',
              message: '入住成功！'
            });
            this.page();
          }
        });
        this.$axios.post('/api/GetChamber/update', {
          studentNo: this.showStudentNo,
          chamber: row.chamber
        }).then();
        this.reload(); //页面刷新
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消入住'
        });
      });
    }
  },
  created() {
    this.page();
  },
  computed: {
    showStudentNo() {
      return this.$cookies.get('studentNo')
    },
  },
  mounted() {
    this.$axios.get("/api/GetChamber/valid/" + this.showStudentNo).then(resp => {
      if (resp.data === false) {
        /*document.querySelector('.prompt').style.display = 'block';*/
        document.querySelector('.marquee').innerHTML = '您还未完成宿舍入住,请你尽快完成宿舍入住';
        document.querySelector('.marqueeT').innerHTML = '您还未完成宿舍入住,请你尽快完成宿舍入住';
        this.btnChangeEnable = !this.btnChangeEnable;
      }
    });
  }

}
</script>

<style lang="scss" scoped>
.GetAcc {
  display: flex;
  position: relative;
}

.breadcrumb {
  flex-grow: 1;
  position: absolute;
  top: 6vh;
  left: 8vw;
}

.chamber {
  flex-grow: 2;
  width: 600px;
  border-radius: 5px;
  position: absolute;
  top: 350px;
  left: 51vw;
  transform: translate(-50%, -50%);
  padding: 25px 25px 5px 25px;
  border: 1px solid #2f82ff;
  background-color: white;
  height: 500px;
}

.prompt {
  flex-grow: 1;
  /*  display: none;*/
  max-width: 100%;
  white-space: nowrap;
  overflow: hidden;
  position: relative;
  height: 20px;
  line-height: 20px;
}

.prompt:hover {
  animation-play-state: paused;
}

.prompt span {
  margin: 0 10px;
  position: absolute;
  left: 0;
  animation: marquee1 15s linear 8;
}

@keyframes marquee {
  0% {
    left: 0;
  }
  100% {
    left: -100%;
  }
}

.prompt {
  height: 30px;
  line-height: 30px;
  background-color: rgb(140, 197, 255);;
  color: #ffffff;
  position: relative;
  overflow: hidden;
  text-align: center;
  width: 100%;
  padding-right: 20px;
  z-index: 100;
}

.marquee-block {
  display: inline-block;
  width: 100%;
  height: 100%;
  vertical-align: middle;
  overflow: hidden;
  box-sizing: border-box;
  padding-left: 15px;
  position: relative;
}

.marquee {
  animation: marquee 10s linear infinite;
  white-space: nowrap;
  position: absolute;
}

.marqueeT {
  animation: marqueeT 10s linear infinite;
  white-space: nowrap;
  position: absolute;
}

@keyframes marquee {

  0% {
    left: 0;
  }

  100% {
    left: -105%;
  }

}

@keyframes marqueeT {

  0% {
    left: 105%;
  }

  100% {
    left: 0;
  }

}

.handle-box {
  margin-bottom: 20px;
}

.handle-input {
  width: 150px;
  display: inline-block;
}

.mr10 {
  margin-right: 10px;
}

</style>
