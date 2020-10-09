<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-office-building"></i> 宿舍管理
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
                >添加宿舍
                </el-button>
                <el-input v-model="chamberForm.chamberNo" placeholder="宿舍号" class="handle-input mr10"></el-input>
                <el-input v-model="chamberForm.chamberName" placeholder="楼栋" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            </div>

            <el-table :data="chamberList"
                      style="width: 100%"
                      border
                      @sort-change="sortChange"
                      :sort-orders="['ascending', 'descending']"
                      :default-sort="{prop: 'chamberNo', order: 'ascending'}"
            >
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column label="宿舍号" sortable="custom" prop="chamberNo"
                                 align="center">
                </el-table-column>
                <el-table-column label="楼栋" prop="chamberName" align="center">
                </el-table-column>
                <el-table-column label="已住人数" prop="people" align="center">
                </el-table-column>
                <el-table-column label="容纳人数" prop="peoples" align="center">
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button
                                size="mini" @click="editChamber(scope.row)" style="background: #409EFF;color: white">编辑
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
                        @current-change="page">
                </el-pagination>
            </div>

            <el-dialog title="修改资料" :visible.sync="editVisible" width="40%" center>
                <el-form :model="home"
                         ref="form"
                         label-width="100px"
                         size="mini"
                         :rules="rules">
                    <el-form-item label="楼栋名" prop="chamberName">
                        <el-select v-model="home.chamberName" placeholder="请选择">
                            <el-option
                                    v-for="item in options"
                                    :label="item.label"
                                    :value="item.label"
                                    :key="item.label"
                            >
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="宿舍号" prop='chamberNo'>
                        <el-input v-model="home.chamberNo"/>
                    </el-form-item>
                    <el-form-item label="已住人数" prop="people">
                        <el-input v-model="home.people" :disabled="true"/>
                    </el-form-item>
                    <el-form-item label="容纳人数" prop="peoples">
                        <el-input v-model="home.peoples"/>
                    </el-form-item>
                    <el-form-item style="margin-left: 108px;">
                        <el-button type="primary" @click="submitForm('form')">提交</el-button>
                        <el-button type="danger" @click="clearForm()">清空</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>

            <el-dialog title="添加宿舍" :visible.sync="addVisible" width="40%"
                       center>
                <el-form :model="home"
                         ref="chamberForm"
                         label-width="100px"
                         size="mini"
                         :rules="rules">
                    <el-form-item label="楼栋名" prop="chamberName">
                        <el-select v-model="home.chamberName" placeholder="请选择">
                            <el-option
                                    v-for="item in options"
                                    :label="item.label"
                                    :value="item.label"
                                    :key="item.label">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="宿舍号" prop="chamberNo">
                        <el-input v-model="home.chamberNo"/>
                    </el-form-item>
                    <el-form-item label="已住人数" prop="people">
                        <el-input v-model="home.people"/>
                    </el-form-item>
                    <el-form-item label="容纳人数" prop="peoples">
                        <el-input v-model="home.peoples"/>
                    </el-form-item>
                    <el-form-item style="margin: 0 128px;">
                        <el-button type="primary" @click="addForm('chamberForm')">提交</el-button>
                        <el-button type="danger" @click="clearForm()">清空</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>

        </div>

    </div>
</template>

<script>
    export default {
        name: 'Dorms',
        data() {
            let validChamberNo = (rule, value, callback) => {
                if (value === this.origin) {
                    return callback();
                }
                this.$axios.get("/api/chamber/validChamberNo/" + value).then(resp => {
                    if (resp.data) {
                        callback(new Error("该宿舍已存在,请重新输入"));
                    } else {
                        callback();
                    }
                });
            };
            let validPeoles = (rule,value,callback)=> {
                if (value === '') {
                    return callback(new Error('容纳人数不能为空'));
                }
                if (value > 8) {
                    callback(new Error('容纳人数不能超过8人'));
                } else if (value < 4){
                    callback(new Error('容纳人数不能少于4人'));
                }else {
                    callback();
                }
            };
            return {
                chamberList: [],
                pageInfo: {
                    pageNum: 1,
                    total: 10,
                    pageSize: 6
                },
                editVisible: false,
                addVisible: false,
                home: {
                    chamberNo: '',
                    chamberName: '',
                    people: '',
                    peoples: '',
                    chamber: ''
                },
                chamberForm: {
                    chamberNo: '',
                    chamberName: '',
                },
                options: [{
                    label: 'A'
                }, {
                    label: 'B'
                }, {
                    label: 'C'
                }, {
                    label: 'D'
                }],
                rules: {
                    chamberNo: [
                        {required: true, message: '用户名不能为空', trigger: 'blur'},
                        {validator: validChamberNo, trigger: 'blur'}
                    ],
                    chamberName: [
                        {required: true, message: '楼栋名不能为空', trigger: 'blur'},
                    ],
                    people: [
                        {required: true, trigger: 'blur'},
                    ],
                    peoples: [
                        {validator:validPeoles,trigger:'blur'}
                    ],
                    chamber: [
                        {required: true, trigger: 'blur'},
                    ]
                },
                origin: '',
            }
        },
        methods: {
            /*分页*/
            page() {
                this.$axios.get("/api/chamber/page", {
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
                this.$axios.get("/api/chamber/page", {
                    params: {
                        condition: {
                            chamberNo: this.chamberForm.chamberNo,
                            chamberName: this.chamberForm.chamberName
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
                    this.$axios.get("/api/chamber/pageDesc", {
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
            /*修改宿舍信息*/
            editChamber(row) {
                this.editVisible = true;
                this.home.id = row.id;
                this.home.chamberNo = row.chamberNo;
                this.home.chamberName = row.chamberName;
                this.home.people = row.people;
                this.home.peoples = row.peoples;
                this.home.chamber = row.chamber;
                this.origin = row.chamberNo;
            },
            /*删除宿舍*/
            deleteId(row) {
                this.$confirm('确定删除该宿舍信息？该操作无法回退！', '提示', {
                    distinguishCancelAndClose: true,
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.delete('/api/chamber/delete/' + row.id).then(resp => {
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
            /*修改宿舍数据*/
            submitForm(formName) {
                this.$refs[formName].validate(valid => {
                    if (!valid) {
                        return false;
                    } else {
                        this.$axios.post('/api/chamber/save', {
                            id: this.home.id,
                            chamberNo: this.home.chamberNo,
                            chamberName: this.home.chamberName,
                            people: this.home.people,
                            peoples: this.home.peoples,
                            chamber: this.home.chamberName+this.home.chamberNo,
                            flag: this.home.flag
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
                                this.clearForm();
                            }
                        });
                    }
                })
            },
            /*清空表单内容*/
            clearForm() {
                this.$nextTick(() => {
                    this.home.chamberNo = "";
                        this.home.chamberName = "";
                        this.home.peoples = "";
                        this.home.chamber = ""
                })
            },
            /*添加弹出框*/
            addList() {
                this.addVisible = true;
                this.clearForm();
            },
            /*添加宿舍*/
            addForm(formName) {
                this.$refs[formName].validate(valid => {
                    if (!valid) {
                        return false;
                    } else {
                        this.$axios.post('/api/chamber/chamberList', {
                            id: this.home.id,
                            chamberNo: this.home.chamberNo,
                            chamberName: this.home.chamberName,
                            people: this.home.people,
                            peoples: this.home.peoples,
                            chamber: this.home.chamberName+this.home.chamberNo,
                            flag: this.home.flag
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
                                    message: '修改失败'
                                });
                            }
                        });
                    }
                })
            },

        },
        created() {
            this.page();
        }
    }
</script>


<style lang="scss" scoped>
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
