<template>
    <div class="header">
        <!-- 折叠按钮 -->
        <div class="collapse-btn" @click="collapseChage">
            <i v-if="!collapse" class="el-icon-s-fold"></i>
            <i v-else class="el-icon-s-unfold"></i>
        </div>
        <div class="logo">后台管理系统</div>
        <div class="header-right">
            <div class="header-user-con">
                <!-- 搜索 -->
                <div class="search" title="搜索">
                    <i class="el-icon-search" @click="showButton()"></i>
                    <input v-show="flag" placeholder="Search"></input>
                </div>
                <!-- 全屏显示 -->
                <div class="btn-fullscreen" @click="handleFullScreen">
                    <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                        <i class="el-icon-rank"></i>
                    </el-tooltip>
                </div>

                <!-- 用户头像 -->
                <div class="user-avator" style="margin: 0">
                    <el-avatar
                            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
                </div>
                <!-- 用户名下拉菜单 -->
                <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{showUserName}}
                        <i class="el-icon-caret-bottom"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item>
                          <a href="https://github.com/hitabullet/Student-Dormitory-Manager-System">项目地址</a>
                          </el-dropdown-item>
                        <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
                        <el-dropdown-item divided command="email">联系我们</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>

    </div>
</template>
<script>
    import bus from '../common/bus';
    import Cookies from 'js-cookie';

    export default {
        data() {
            let validName= (rule,value,callback)=> {
                if (value === '') {
                    callback(new Error('用户名不能为空'));
                } else {
                    callback();
                }
            };
            return {
                collapse: false,
                fullscreen: false,
                flag: false,
                message: 2,
                userForm:{
                    username:''
                },
                rules: {
                    name: [
                        {required:true,message:'用户名不能为空',trigger:'blur'},
                        {validator:validName,trigger:'blur'}
                    ],
                }
            }
        },
        methods: {
            //输入框的显示和掩藏
            showButton() {
                this.flag = !this.flag
            },
            // 用户名下拉菜单选择事件
            handleCommand(command) {
                if (command == 'loginout') {
                    localStorage.removeItem('username');
                    this.$router.push('/');
                }
                 if (command == 'email') {
                    localStorage.removeItem('username');
                    this.$router.push('/');
                }
            },
            // 侧边栏折叠
            collapseChage() {
                this.collapse = !this.collapse;
                bus.$emit('collapse', this.collapse);
            },
            // 全屏事件
            handleFullScreen() {
                let element = document.documentElement;
                if (this.fullscreen) {
                    if (document.exitFullscreen) {
                        document.exitFullscreen();
                    } else if (document.webkitCancelFullScreen) {
                        document.webkitCancelFullScreen();
                    } else if (document.mozCancelFullScreen) {
                        document.mozCancelFullScreen();
                    } else if (document.msExitFullscreen) {
                        document.msExitFullscreen();
                    }
                } else {
                    if (element.requestFullscreen) {
                        element.requestFullscreen();
                    } else if (element.webkitRequestFullScreen) {
                        element.webkitRequestFullScreen();
                    } else if (element.mozRequestFullScreen) {
                        element.mozRequestFullScreen();
                    } else if (element.msRequestFullscreen) {
                        // IE11
                        element.msRequestFullscreen();
                    }
                }
                this.fullscreen = !this.fullscreen;
            },

        },
        computed: {
            showUserName() {
                return Cookies.get('username')
            },
        },
        mounted() {
            if (document.body.clientWidth < 1500) {
                this.collapseChage();
            }
        }
    };
</script>
<style scoped>
    .header {
        position: relative;
        box-sizing: border-box;
        width: 100%;
        height: 70px;
        font-size: 22px;
        color: #fff;
    }

    .collapse-btn {
        float: left;
        padding: 0 21px;
        cursor: pointer;
        line-height: 70px;
    }

    .header .logo {
        float: left;
        width: 250px;
        line-height: 70px;
    }

    .header-right {
        float: right;
        padding-right: 50px;
    }

    .header-user-con {
        display: flex;
        height: 70px;
        align-items: center;
        cursor: pointer;
    }

    .btn-fullscreen {
        transform: rotate(45deg);
        margin-right: 5px;
        font-size: 24px;
    }

    .
    .user-name {
        margin-left: 10px;
    }

    .user-avator {
        margin-left: 20px;
    }

    .user-avator img {
        display: block;
        width: 40px;
        height: 40px;
        border-radius: 50%;
    }

    .el-dropdown-link {
        color: #fff;
        cursor: pointer;
    }

    .el-dropdown-menu__item {
        text-align: center;
    }

    .search {
        font-size: 20px;
        display: inline-block;
        margin-right: 16px;
    }

    .el-icon-search {
        position: relative;
    }

    .search input {
        border: 0;
        vertical-align: middle;
        width: 185px;
        background-color: #242f42;
        caret-color: white;
        border-bottom: 1px solid gray;
        color: white;
        text-align: start;
    }

    .search input:focus {
        outline: none;
    }

    .search input::-webkit-input-placeholder {
        color: #C0C0C0;
        font-size: 14px;
    }

    .user-avator .el-avatar {
        width: 30px;
        height: 30px;
        margin: 4px 20px;
    }
</style>
