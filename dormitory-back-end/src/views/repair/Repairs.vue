<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-s-order"></i> 维修清单
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">

      <div class="handle-box">
        <el-button
            type="primary"
            icon="el-icon-insert"
            class="handle-del mr10"
            @click="insertList"
        >添加报修单
        </el-button>
        <el-input v-model="repairsForm.chamber" placeholder="宿舍" class="handle-input mr10"></el-input>
        <el-date-picker
            v-model="repairsForm.repairtime"
            type="date"
            value-format=" yyyy-MM-dd "
            format=" yyyy-MM-dd "
            placeholder="选择日期"
            class="mr10">
        </el-date-picker>
        <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      </div>

      <el-table :data="repairsList"
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
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
                size="mini" @click="edit(scope.row)" style="background: #409EFF;color: white">编辑
            </el-button>
            <el-button size="mini" @click="deleteId(scope.row)">已修</el-button>
          </template>
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

      <el-dialog title="添加报修单" :visible.sync="addVisible" width="30%"
                 center>
        <el-form :model="repairs"
                 ref="repairsForm"
                 label-width="100px"
                 size="mini"
                 :rules="rules">
          <el-form-item label="报修宿舍" prop="chamber">
            <el-select v-model="repairs.chamber" placeholder="请选择" style="width: 84%;">
              <el-option
                  v-for="index in options"
                  :label="index.label"
                  :value="index.label"
                  :key="index.label">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="损坏处" prop="message">
            <el-input v-model="repairs.message" style="width: 84%;"/>
          </el-form-item>
          <el-form-item label="报修时间" prop="repairtime">
            <el-date-picker
                v-model="repairs.repairtime"
                type="date"
                value-format=" yyyy-MM-dd "
                format=" yyyy-MM-dd "
                placeholder="选择日期"
                style="width: 84%;">
            </el-date-picker>
          </el-form-item>
          <el-form-item style="margin: 0 50px;">
            <el-button type="primary" @click="insertForm('repairsForm')">提交</el-button>
            <el-button type="danger" @click="clearForm()">清空</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <el-dialog title="修改资料" :visible.sync="editVisible" width="30%"
                 center>
        <el-form :model="repairs"
                 ref="repairsForm"
                 label-width="100px"
                 size="mini"
                 :rules="rules">
          <el-form-item label="报修宿舍" prop="chamber">
            <el-select v-model="repairs.chamber" placeholder="请选择" style="width: 84%;">
              <el-option
                  v-for="item in options"
                  :label="item.label"
                  :value="item.label"
                  :key="item.label">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="损坏处" prop="message">
            <el-input v-model="repairs.message" style="width: 84%;"/>
          </el-form-item>
          <el-form-item label="报修时间" prop="repairtime">
            <el-date-picker
                v-model="repairs.repairtime"
                type="date"
                value-format="yyyy-MM-dd"
                format="yyyy-MM-dd"
                placeholder="选择日期"
                style="width: 84%;">
            </el-date-picker>
          </el-form-item>
          <el-form-item style="margin:0 50px;">
            <el-button type="primary" @click="submitForm('repairsForm')">提交</el-button>
            <el-button type="danger" @click="clearForm()">清空</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: 'repairs',
  data() {
    return {
      repairsList: [],
      addVisible: false,
      editVisible: false,
      pageInfo: {
        pageNum: 1,
        total: 10,
        pageSize: 6
      },
      repairs: {
        chamber: '',
        message: '',
        repairtime: null,
        flag: ''
      },
      repairsForm: {
        chamber: '',
        repairtime: null,
        flag: ''
      },
      options: [{
        label: 'A232'
      }, {
        label: 'C233'
      }, {
        label: 'B234'
      }, {
        label: 'D235'
      }],
      rules: {
        chamber: [
          { required: true, message: '报修宿舍不能为空', trigger: 'blur' }
        ],
        message: [
          { required: true, message: '损坏处不能为空', trigger: 'blur' }
        ],
        repairtime: [
          { required: true, message: '报修时间不能为空', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    flagFormatter(row, col, value) {
      return value === 0 ? '未修' : '';
    },
    /*分页*/
    page() {
      this.$axios.get('/api/repairs/page', {
        params: {
          pageNum: this.pageInfo.pageNum,
          pageSize: this.pageInfo.pageSize
        }
      }).then(resp => {
        this.repairsList = resp.data.list;
        this.pageInfo.total = resp.data.total;
      });

    },
    /*搜索并且分页*/
    search() {
      if (this.repairsForm.repairtime == null) {
        alert("时间不能为空哦")
      }
      this.$axios.get('/api/repairs/page', {
        params: {
          condition: {
            chamber: this.repairsForm.chamber,
            repairtime: this.repairsForm.repairtime
          },
          pageNum: this.pageInfo.pageNum,
          pageSize: this.pageInfo.pageSize
        }
      }).then(resp => {
            this.repairsList = resp.data.list;
            this.pageInfo.total = resp.data.total;
          }
      );
    },
    /*降序排列*/
    sortChange(column) {
      if (column.order === 'descending') {
        this.$axios.get('/api/repairs/pageDesc', {
          params: {
            pageNum: this.pageInfo.pageNum,
            pageSize: this.pageInfo.pageSize
          }
        }).then(resp => {
              this.repairsList = resp.data.list;
              this.pageInfo.total = resp.data.total;
            }
        );
      } else {
        this.page();
      }
    },
    /*添加*/
    insertList() {
      this.addVisible = true;
      this.clearForm();
    },
    /*添加需维修宿舍*/
    insertForm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) {
          return false;
        } else {
          this.$axios.post('/api/repairs/repairsList', {
            id: this.repairs.id,
            chamber: this.repairs.chamber,
            message: this.repairs.message,
            repairtime: this.repairs.repairtime,
            flag: this.repairs.flag
          }).then(resp => {
            if (resp.data) {
              this.$notify({
                type: 'success',
                message: '添加成功'
              });
              this.addVisible = false;
              this.page();
            } else {
              this.$notify({
                type: 'error',
                message: '添加失败'
              });
              this.clearForm();
            }
          });
        }
      });
    },
    /*修改*/
    edit(row) {
      this.editVisible = true;
      this.repairs.id = row.id;
      this.repairs.chamber = row.chamber;
      this.repairs.message = row.message;
      this.repairs.repairtime = row.repairtime;
    },
    /*修改维修宿舍信息*/
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) {
          return false;
        } else {
          this.$axios.post('/api/repairs/save', {
            id: this.repairs.id,
            chamber: this.repairs.chamber,
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
            }
          });
        }
      });
    },
    /*维修宿舍已修好*/
    deleteId(row) {
      this.$confirm('确定该宿舍损坏处已被修好？该操作无法回退！', '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('/api/repairs/delete/' + row.id).then(resp => {
          if (resp.data) {
            this.$message({
              type: 'success',
              message: '操作成功！'
            });
            this.page();
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    /*清空表单内容*/
    clearForm() {
      this.$nextTick(() => {
        this.repairs.chamber = '',
            this.repairs.message = '',
            this.repairs.repairtime = '';
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
