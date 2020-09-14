<template>
  <div class="login">
    <div class="login-form">
      <h3>登录</h3>
      <span @click="getHome()">
        <i class="icon iconfont icon-fanhui"></i>
        <h5>返回主页</h5>
      </span>
      <el-form :model="loginForm" status-icon :rules="rules" ref="ruleForm">
        <el-form-item prop="studentNo">
          <el-input prefix-icon="el-icon-user-solid" v-model="loginForm.studentNo" placeholder="请输入学号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" autocomplete="off"
                    v-model="loginForm.password" show-password
                    prefix-icon="el-icon-lock" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-row :span="24">
            <el-col :span="12" class="codeInput">
              <el-input v-model="loginForm.code" prefix-icon="iconfont icon-yanzhengma" style="width: 130%;"
                        auto-complete="off" placeholder="请输入验证码"
                        @keyup.enter.native="submitForm('loginForm')"
              ></el-input>
            </el-col>
            <el-col :span="12">
              <div class="login-code" @click="refreshCode">
                <!--验证码组件-->
                <v-identify :identifyCode="identifyCode"></v-identify>
              </div>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
        </el-form-item>
      </el-form>
      <div class="forget" @click="forgetRoom">忘记密码</div>
      <div class="register" @click="registerRoom">我要注册</div>
    </div>
  </div>
</template>

<script>
import SIdentify from './Sidentfy';


export default {
  name: "Login",
  components: {
    'v-identify': SIdentify
  },
  data() {
    const validStudentNo = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入您的学号'));
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
      loginForm: {
        studentNo: '',
        password: '',
        code: ''
      },
      identifyCodes: '1234567890',
      identifyCode: '',
      rules: {
        studentNo: [
          {required: true, message: '用户名不能为空', trigger: 'blur'},
          {validator: validStudentNo, trigger: 'blur'}
        ],
        password: [
          {validator: validatePass, trigger: 'blur'}
        ],
        code: [
          {required: true, message: '请输入验证码', trigger: 'blur'},
          {validator: validateCode, trigger: 'blur'}
        ]
      },
      verifyCode: null
    }
  },
  methods: {
    getHome() {
      this.$router.push('/Home')
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
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          //真验证
          this.$axios.post('/api/student/login', {
            studentNo: this.loginForm.studentNo,
            password: this.loginForm.password
          }).then(response => {
            //判断后台返回的数据是否成功，如果代表成功，弹出登录成功的消息，跳转到首页
            if (response.data == 'success') {
              this.$message({
                message: '登录成功！',
                type: 'success',
              });
              //存储cookies
              this.$cookies.set('studentNo', this.loginForm.studentNo);
              //跳转首页
              this.$router.push({path: "/Home"});
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
    registerRoom() {
      this.$router.push('/Register')
    },
    forgetRoom() {
      this.$router.push('/Forget')
    }
  },
  created() {
    this.refreshCode();
  }
}
</script>

<style lang="scss" scoped>
h3 {
  display: inline-block;
}

.login {
  background: url("../../assets/img/background.jpg") no-repeat;
  width: 100%;
  height: 100%;
  background-size: cover;

}

span {
  float: right;
  color: #2f82ff;
  cursor: pointer;
}

span h5 {
  display: inline-block;
  height: 24px !important;
  font-weight: 500;
}

.login-form {
  width: 400px;
  height: 350px;
  border-radius: 5px;
  margin: auto auto;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: #ffffff;
  padding: 25px 25px 5px 25px;
}

.el-form {
  margin: 20px auto;
}

.login-code {
  margin-left: 6px;
}

.el-form .el-form-item:last-child .el-button {
  width: 100%;
}

.forget {
  display: inline-block;
  color: red;
  cursor: pointer;
}

.register {
  float: right;
  display: inline-block;
  color: #2f82ff;
  cursor: pointer;
}

/deep/ .el-input__inner {
  background-color: transparent !important;
}
</style>

