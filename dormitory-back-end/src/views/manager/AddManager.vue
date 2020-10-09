<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-s-custom"></i> 添加管理员
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-button
            type="primary"
            icon="el-icon-insert"
            class="handle-del mr10"
            @click="addList"
        >添加管理员
        </el-button>
        <el-input v-model="user.username" placeholder="用户名" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      </div>

      <el-table :data="userList"
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
        <el-table-column label="序号" sortable="custom" prop="id"
                         align="center">
        </el-table-column>
        <el-table-column label="用户名" prop="username" align="center">
        </el-table-column>
        <el-table-column label="密码" prop="password" align="center" :formatter="passwordFormatter">
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

      <el-dialog title="添加管理员" :visible.sync="addVisible" width="40%"
                 center>
        <el-form :model="user"
                 ref="userForm"
                 label-width="100px"
                 size="mini"
                 :rules="rules">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="user.username" style="width: 84%;"/>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="user.password" style="width: 84%;"/>
          </el-form-item>

          <el-form-item style="margin: 0 50px;">
            <el-button type="primary" @click="submitForm('userForm')">提交</el-button>
            <el-button type="danger" @click="clearForm()">清空</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

    </div>
  </div>
</template>

<script>
export default {
  name: 'addManager',
  data() {
    return {
      userList: [],
      addVisible: false,
      pageInfo: {
        pageNum: 1,
        total: 10,
        pageSize: 6
      },
      user: {
        id: '',
        username: '',
        password: ''
      },
      rules: {
        name: [
          { required: true, message: '报修宿舍不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '损坏处不能为空', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    passwordFormatter(row, col, value) {
      return value.length >= 6 ? '保密' : '';
    },
    /*分页*/
    page() {
      this.$axios.get('/api/manager/page', {
        params: {
          pageNum: this.pageInfo.pageNum,
          pageSize: this.pageInfo.pageSize
        }
      }).then(resp => {
        this.userList = resp.data.list;
        this.pageInfo.total = resp.data.total;
      });

    },
    /*搜索并且分页*/
    search() {
      this.$axios.get('/api/manager/page', {
        params: {
          condition: {
            username: this.user.username
          },
          pageNum: this.pageInfo.pageNum,
          pageSize: this.pageInfo.pageSize
        }
      }).then(resp => {
            this.userList = resp.data.list;
            this.pageInfo.total = resp.data.total;
          }
      );
    },
    /*降序排列*/
    sortChange(column) {
      if (column.order === 'descending') {
        this.$axios.get('/api/manager/pageDesc', {
          params: {
            pageNum: this.pageInfo.pageNum,
            pageSize: this.pageInfo.pageSize
          }
        }).then(resp => {
              this.userList = resp.data.list;
              this.pageInfo.total = resp.data.total;
            }
        );
      } else {
        this.$axios.get('/api/manager/page', {
          params: {
            pageNum: this.pageInfo.pageNum,
            pageSize: this.pageInfo.pageSize
          }
        }).then(resp => {
          this.userList = resp.data.list;
          this.pageInfo.total = resp.data.total;
        });
      }
    },

    /*添加*/
    addList() {
      this.addVisible = true;
      this.clearForm();
    },
    /*提交修改的数据给后台*/
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) {
          return false;
        } else {
          this.$axios.post('/api/repairs/save', {
            id: this.repairs.id,
            dormitory: this.repairs.dormitory,
            message: this.repairs.message,
            repairtime: this.repairs.repairtime,
            flag: this.repairs.flag
          }).then(resp => {
            if (resp.data) {
              this.$notify({
                type: 'success',
                message: '修改成功'
              });
              this.editVisible = false;
              this.page();
            } else {
              this.$notify({
                type: 'error',
                message: '修改失败'
              });
              this.clearForm(formName);
            }
          });
        }
      });
    },
    /*清空表单内容*/
    clearForm() {
      this.$nextTick(() => {
        this.user.username = '',
            this.user.password = '';
      });
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
