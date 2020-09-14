<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-s-claim"></i> 维修记录
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="handle-box">
        <el-input v-model="recordForm.chamber" placeholder="宿舍" class="handle-input mr10"></el-input>
        <el-date-picker
            v-model="recordForm.repairtime"
            type="date"
            value-format=" yyyy-MM-dd "
            format=" yyyy-MM-dd "
            placeholder="选择日期"
            class="mr10">
        </el-date-picker>
        <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      </div>

      <el-table :data="recordList"
                style="width: 100%"
                border
                @sort-change="sortChange"
                :sort-orders="['ascending', 'descending']"
                :default-sort="{prop: 'repairtime', order: 'ascending'}"
      >
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column label="报修时间" sortable="custom" prop="repairtime"
                         align="center">
        </el-table-column>
        <el-table-column label="报修宿舍" prop="chamber" align="center">
        </el-table-column>
        <el-table-column label="损坏处" prop="message" align="center">
        </el-table-column>
        <el-table-column label="状态" prop="flag" align="center" :formatter="flagFormatter">
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            style="float: right;padding: 0"
            background
            layout="prev,pager,next"
            :page-size="pageInfo.pageSize"
            :total="pageInfo.total"
            :current-page.sync="pageInfo.pageNum"
            @current-change="page">
        </el-pagination>
      </div>

    </div>
  </div>
</template>

<script>

export default {
  name: 'Record',
  data() {
    return {
      recordList: [],
      pageInfo: {
        pageNum: 1,
        total: 10,
        pageSize: 6
      },
      record: {
        chamber: '',
        message: '',
        repairtime: '',
        flag: ''
      },
      recordForm: {
        chamber: '',
        repairtime: '',
        flag: ''
      }
    };
  },
  methods: {

    flagFormatter(row, col, value) {
      return value === 1 ? '已修' : '';
    },
    /*分页*/
    page() {
      this.$axios.get('/api/repairsRecord/page', {
        params: {
          pageNum: this.pageInfo.pageNum,
          pageSize: this.pageInfo.pageSize
        }
      }).then(resp => {
        this.recordList = resp.data.list;
        this.pageInfo.total = resp.data.total;
      });
    },
    /*搜索并且分页*/
    search() {
      this.$axios.get('/api/repairsRecord/page', {
        params: {
          condition: {
            chamber: this.recordForm.chamber,
            repairtime: this.recordForm.repairtime
          },
          pageNum: this.pageInfo.pageNum,
          pageSize: this.pageInfo.pageSize
        }
      }).then(resp => {
            this.recordList = resp.data.list;
            this.pageInfo.total = resp.data.total;
          }
      );
    },
    /*降序排列*/
    sortChange(column) {
      if (column.order === 'descending') {
        this.$axios.get('/api/repairsRecord/pageDesc', {
          params: {
            pageNum: this.pageInfo.pageNum,
            pageSize: this.pageInfo.pageSize
          }
        }).then(resp => {
              this.recordList = resp.data.list;
              this.pageInfo.total = resp.data.total;
            }
        );
      }
    }
  },
  created() {

    this.page();
  }
};
</script>


<style lang="scss" scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 150px;
  display: inline-block;
}

.mr10 {
  margin-right: 10px;
}

.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 40px;
}

.el-button--small, .el-button--small.is-round {
  padding: 9px 13px;
  margin: 0 10px;
}
</style>
