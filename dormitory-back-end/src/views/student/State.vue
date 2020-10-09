<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-reading"></i> 学生管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <transition>
        <div class="handle-box">
          <el-button
              type="primary"
              icon="el-icon-insert"
              class="handle-del mr10"
              @click="insertList"
          >添加学生
          </el-button>
          <el-input v-model="searchForm.name" placeholder="姓名" class="handle-input mr10"></el-input>
          <el-select v-model="searchForm.sex" placeholder="性别" class="handle-select mr10">
            <el-option label="全部" value="null"></el-option>
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="2"></el-option>
          </el-select>
          <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        </div>
      </transition>
      <el-table :data="studentList"
                style="width: 100%"
                border
                @sort-change="sortChange"
                :sort-orders="['ascending', 'descending']"
                :default-sort="{prop: 'studentNo', order: 'ascending'}"
      >
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column label="学号" sortable="custom" prop="studentNo" align="center">
        </el-table-column>
        <el-table-column label="姓名" prop="name" align="center">
        </el-table-column>
        <el-table-column label="性别" prop="sex" align="center" :formatter="sexFormatter">
        </el-table-column>
        <el-table-column label="寝室" prop="chamber" align="center">
        </el-table-column>
        <el-table-column label="居住城市" prop="address" align="center">
        </el-table-column>
        <el-table-column label="专业" prop="className" :formatter="classNameFormatter" align="center">
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
                size="mini" @click="edit(scope.row)" style="background: #409EFF;color: white">编辑
            </el-button>
            <el-button size="mini" type="danger" @click="deleteId(scope.row)">删除</el-button>
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
            @current-change="handleCurrentPage"
            @size-change="handleSizeChange"
        >
        </el-pagination>
      </div>

      <el-dialog title="修改资料" :visible.sync="editVisible" width="40%"
                 center>
        <el-form :model="student"
                 ref="form"
                 label-width="100px"
                 size="mini"
                 :rules="rules">
          <el-form-item label="学号" prop='studentNo'>
            <el-input v-model="student.studentNo"/>
          </el-form-item>
          <el-form-item label="姓名" prop='name'>
            <el-input v-model="student.name"/>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-radio v-model="student.sex" label="1" value="1" key="1">男</el-radio>
            <el-radio v-model="student.sex" label="2" value="2" key="2">女</el-radio>
          </el-form-item>
          <el-form-item label="手机号码" prop='number'>
            <el-input v-model="student.number"/>
          </el-form-item>
          <el-form-item label="寝室" prop='chamber'>
            <el-input v-model="student.chamber"/>
          </el-form-item>
          <el-form-item label="居住城市" prop="address">
            <el-select v-model="student.address" placeholder="请选择">
              <el-option
                  v-for="item in cities"
                  :key="item.label"
                  :label="item.label"
                  :value="item.label">
                <span style="float: left">{{ item.label }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="专业班级" prop='className'>
            <el-input v-model="student.className"/>
          </el-form-item>
          <el-form-item style="margin-left: 108px;">
            <el-button type="primary" @click="submitForm('form')">提交</el-button>
            <el-button type="danger" @click="clearForm('form')">清空</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <el-dialog title="添加学生" :visible.sync="addVisible" width="40%"
                 center>
        <el-form :model="student"
                 ref="studentForm"
                 label-width="100px"
                 size="mini"
                 :rules="rules">
          <el-form-item label="学号" prop="studentNo">
            <el-input v-model="student.studentNo"/>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="student.name"/>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-radio v-model="student.sex" label="1">男</el-radio>
            <el-radio v-model="student.sex" label="2">女</el-radio>
          </el-form-item>
          <el-form-item label="电话号码" prop="number">
            <el-input v-model="student.number"/>
          </el-form-item>
          <el-form-item label="寝室" prop="chamber" placeholder="例:C233">
            <el-input v-model="student.chamber"/>
          </el-form-item>
          <el-form-item label="居住城市" prop="address">
            <el-select v-model="student.address" placeholder="请选择">
              <el-option
                  v-for="item in cities"
                  :key="item.label"
                  :label="item.label"
                  :value="item.label">
                <span style="float: left">{{ item.label }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="专业班级" prop="className" placeholder="例:软件技术">
            <el-input v-model="student.className"/>
          </el-form-item>
          <el-form-item style="margin: 0 128px;">
            <el-button type="primary" @click="insertForm('studentForm')">提交</el-button>
            <el-button type="danger" @click="resetForm('studentForm')">清空</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

    </div>
  </div>
</template>

<script>
export default {
  name: 'state',
  inject: ['reload'],   //注入App里的reload方法
  data() {
    let validStudentNo = (rule, value, callback) => {
      if (value === this.origin) {
        return callback();
      }
      this.$axios.get('/api/student/validStudentNo/' + value).then(resp => {
        if (resp.data) {
          callback(new Error('学号重复,请重新输入'));
        } else {
          callback();
        }
      });
    };
    let validName = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error('姓名不能为空'));
      }
      let pattern = /[^\u4e00-\u9fa5]/g;
      if (pattern.test(value)) {
        callback(new Error('姓名格式错误,请输入汉字,例如:周树人'));
      } else {
        callback();
      }
    };
    let validNumber = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error('手机号码不能为空'));
      }
      let pattern = /^1[3|4|5|8|7|9][0-9]\d{8}$/;
      if (!pattern.test(value)) {
        callback(new Error('手机号格式错误'));
      } else {
        callback();
      }
    };
    let validChamber = (rule, value, callback) => {
      if (value === this.originC) {
        return callback();
      }
      if (value === '') {
        return callback(new Error('寝室号不能为空'));
      }
      this.$axios.get('/api/student/validChamber/' + value).then(resp => {
        if (resp.data) {
          callback(new Error('该宿舍已住满！请重新填写'));
        } else {
          callback();
        }
      });
    };
    return {
      studentList: [],
      pageInfo: {
        pageNum: 1,
        total: 10,
        pageSize: 6
      },
      student: {
        studentNo: '',
        name: '',
        sex: 'null',
        chamber: '',
        address: '',
        className: '',
        number: ''
      },
      searchForm: {
        name: '',
        sex: ''
      },
      cities: [{
        value: 'Beijing',
        label: '北京'
      }, {
        value: 'Nanjing',
        label: '南京'
      }, {
        value: 'ChangSha',
        label: '长沙'
      }],
      rules: {
        studentNo: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { validator: validStudentNo, trigger: 'blur' }
        ],
        sex: [
          { required: true, trigger: 'blur' }
        ],
        name: [
          { required: true, validator: validName, trigger: 'blur' }
        ],
        number: [
          { required: true, validator: validNumber, trigger: 'blur' }
        ],
        chamber: [
          { required: true, message: '寝室号不能为空', trigger: 'blur' },
          { validator: validChamber, trigger: 'blur' }
        ],
        address: [
          { required: true, message: '家庭住址不能为空', trigger: 'blur' }
        ],
        className: [
          { required: true, message: '专业不能为空', trigger: 'blur' }
        ]
      },
      addVisible: false,
      editVisible: false,
      origin: '',
      originC: ''
    };
  },
  methods: {
    /*截取专业词*/
    classNameFormatter(row, col, value) {
      return value.replace(value.substr(-2, 2), '');
    },
    /*分页并查询*/
    page() {
      this.$axios.get('/api/student/page', {
        params: {
          condition :{
            name: this.searchForm.name,
            sex: this.searchForm.sex
          },
          pageNum: this.pageInfo.pageNum,
          pageSize: this.pageInfo.pageSize
        }
      }).then(resp => {
        this.studentList = resp.data.list;
        this.pageInfo.total = resp.data.total;
      });

    },
    /*性别格式*/
    sexFormatter(row, col, value) {
      return value === 1 ? '男' : '女';
    },
    /*修改*/
    edit(row) {
      this.editVisible = true;
      this.student.id = row.id;
      this.student.studentNo = row.studentNo;
      this.student.name = row.name;
      this.student.sex = row.sex;
      this.student.chamber = row.chamber;
      this.student.address = row.address;
      this.student.number = row.number;
      this.student.className = row.className;
      this.origin = row.studentNo;
      this.originC = row.chamber;
    },
    /*修改学生数据*/
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) {
          return false;
        } else {
          this.$axios.post('/api/student/save', {
            id: this.student.id,
            studentNo: this.student.studentNo,
            name: this.student.name,
            sex: this.student.sex,
            number: this.student.number,
            chamber: this.student.chamber,
            address: this.student.address,
            className: this.student.className,
            flag: this.student.flag
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
    /*删除学生数据*/
    deleteId(row) {
      this.$confirm('确定删除该学生信息？该操作无法回退！', '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post('/api/student/delete',
            {
              id: row.id,
              chamber: row.chamber
            }).then(resp => {
          if (resp.data) {
            this.$message({
              type: 'success',
              message: '删除成功！'
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
    /*降序排列*/
    sortChange(column) {
      if (column.order === 'descending') {
        this.$axios.get('/api/student/pageDesc', {
          params: {
            pageNum: this.pageInfo.pageNum,
            pageSize: this.pageInfo.pageSize
          }
        }).then(resp => {
              this.studentList = resp.data.list;
              this.pageInfo.total = resp.data.total;
            }
        );
      } else {
        this.page();
      }
    },
    handleCurrentPage(num) {
      this.pageInfo.pageNum = num;
      this.page();
    },
    handleSizeChange(limit){
      this.pageInfo.pageSize = limit;
      this.page();
    },
    /*搜索并且分页*/
    search() {
     this.pageInfo.pageNum=1;
     this.page();
    },
    /*添加*/
    insertList() {
      this.addVisible = true;
      this.resetForm();
    },
    /*添加学生*/
    insertForm(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) {
          return false;
        } else {
          this.$axios.post('/api/student/addList', {
            id: this.student.id,
            studentNo: this.student.studentNo,
            name: this.student.name,
            sex: this.student.sex,
            number: this.student.number,
            chamber: this.student.chamber,
            address: this.student.address,
            className: this.student.className,
            flag: this.student.flag
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
              this.resetForm();
            }
          });
        }
      });
    },
    /*清空表单内容*/
    clearForm(form) {
      this.$refs[form].resetFields();
    },
    resetForm() {
      this.$nextTick(() => {
        this.student.studentNo = '',
            this.student.sex = '',
            this.student.name = '',
            this.student.className = '',
            this.student.address = '',
            this.student.number = '',
            this.student.chamber = '';
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
</style>
