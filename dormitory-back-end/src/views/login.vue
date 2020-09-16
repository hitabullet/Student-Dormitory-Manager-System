<template>
  <div class="login">
    <el-form :model="loginForm" status-icon :rules="rules" ref="ruleForm" class="login-form">
      <h3>{{ title }}</h3>
      <!--<div class="box-con">
        <span>
          <el-button type="text" @click="logon">注册</el-button>
        </span>
      </div>-->
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" prefix-icon="el-icon-user-solid"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" show-password v-model="loginForm.password" autocomplete="off"
                  prefix-icon="el-icon-lock"></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-row :span="24">
          <el-col :span="12" class="codeInput">
            <el-input v-model="loginForm.code" prefix-icon="iconfont icon-yanzhengma" style="width: 130%;"
                      auto-complete="off" placeholder="请输入验证码"
                      @keyup.enter.native="submitForm('loginForm')"></el-input>
          </el-col>
          <el-col :span="12">
            <div class="login-code" @click="refreshCode">
              <!--验证码组件-->
              <v-identify :identifyCode="identifyCode"></v-identify>
            </div>
          </el-col>
        </el-row>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
      </el-form-item>
    </el-form>
    <!--<el-dialog title="信息注册" :visible.sync="logonVisible" width="40%" center>
      <el-form :model="loginForm"
               ref="form"
               label-width="100px"
               size="mini"
               :rules="rules">
        <el-form-item label="宿舍号" prop='username'>
          <el-input v-model="loginForm.username"/>
        </el-form-item>

      </el-form>
    </el-dialog>-->
  </div>
</template>


<script>
import SIdentify from '../components/common/Sidentfy';
import Cookies from 'js-cookie';

export default {
  name: 'index',
  components: {
    'v-identify': SIdentify
  },
  data() {
    const validUser = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入您的用户名'));
      } else {
        callback();
      }
    };
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入您的密码'));
      } else {
        callback();
      }
    };
    const validateCode = (rule, value, callback) => {
      if (this.identifyCode !== value) {
        this.loginForm.code = '';
        this.refreshCode();
        callback(new Error('请输入正确的验证码'));
      } else {
        callback();
      }
    };
    return {
      title: '学生宿舍管理系统',
      loginForm: {
        username: '',
        password: '',
        code: '',
        rememberMe: ''
      },
      /*logonVisible: false,*/
      identifyCodes: '1234567890',
      identifyCode: '',
      rules: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { validator: validUser, trigger: 'blur' }
        ],
        password: [
          { validator: validatePass, trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { validator: validateCode, trigger: 'blur' }
        ]
      },
      verifyCode: null
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          Cookies.set('username', this.loginForm.username);
          //真验证
          this.$axios.post('/api/manager/login', {
            username: this.loginForm.username,
            password: this.loginForm.password
          }).then(response => {
            //判断后台返回的数据是否成功，如果代表成功，弹出登录成功的消息，跳转到首页
            if (response.data == 'success') {
              this.$message({
                message: '登录成功！',
                type: 'success'
              });
              localStorage.username = this.username;
              this.$router.push({ path: '/home' });
            }

          }).catch(error => {
            this.$message({
              message: '用户名或密码输入错误',
              type: 'error'
            });
          });
        }
      });
    },
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[
            this.randomNum(0, this.identifyCodes.length)
            ];
      }
    },
    refreshCode() {
      this.identifyCode = '';
      this.makeCode(this.identifyCodes, 4);
    },
   /* /!*注册*!/
    logon() {
      this.logonVisible = true;
    }*/
  },
  created() {
    this.refreshCode();
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background: url("../assets/image/login-background.jpg") no-repeat;
  background-size: cover;
}

h3 {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  width: 370px;
  padding: 25px 25px 5px 25px;
  position: relative;
  background: rgba(255,255,255,.2);
  border: 1px solid rgba(255,255,255,.3);
  box-shadow: 0 3px 0 rgba(12,12,12,.03);
 /* height: 320px;
  overflow: hidden;*/
}

.el-form-item {
  margin: 20px auto;
}

.el-input {
  height: 38px;
  background-color: #1c84c6;
  border-color: #1c84c6;
}

input {
  height: 38px;
  padding-left: 30px;
}

.el-input--small .el-input__inner {
  height: 40px;
  line-height: 32px;
}

.el-button {
  width: 100%;
}


.el-input__icon {
  font-size: 16px;
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  cursor: pointer;
  padding-left: 13px;
}

.login-code-img {
  width: 100px;
  height: 38px;
  background-color: #eee;
  border: 1px solid #f0f0f0;
  color: #333;
  font-size: 18px;
  font-weight: bold;
  letter-spacing: 2px;
  text-indent: 2px;
  text-align: center;
}


.el-icon-user-solid {
  font-size: 16px;
}

.el-input--small .el-input__icon {
  line-height: 41px;
}


.box-con {
  width: 100px;
  height: 100px;
  position: absolute;
  background: #3a8ee6;
  top: -50px;
  right: -50px;
  transform: rotate(45deg);
}

/*.box-con span {
  position: absolute;
  bottom: 5px;
  display: block;
  width: 100px;
  text-align: center;
}

.box-con span .el-button {
  font-size: 18px;
  color: #FFF;
}*/
</style>
