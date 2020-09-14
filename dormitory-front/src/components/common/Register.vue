<template>
  <div class="register">
    <div class="register-form">
      <h3>注册</h3>
      <span @click="getHome()">
        <i class="icon iconfont icon-fanhui"></i>
        <h5>返回主页</h5>
      </span>
      <el-form :model="registerForm" status-icon :rules="rules" ref="ruleForm">
        <el-form-item prop="studentNo">
          <el-input prefix-icon="el-icon-user-solid" v-model="registerForm.studentNo" placeholder="请输入学号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" autocomplete="off"
                    v-model="registerForm.password" show-password
                    prefix-icon="el-icon-lock" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="repeatPassword">
          <el-input type="password" autocomplete="off"
                    v-model="registerForm.repeatPassword" show-password
                    prefix-icon="el-icon-lock" placeholder="请再次输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="number">
          <el-input prefix-icon="iconfont icon-dianhuahaoma" v-model="registerForm.number" placeholder="请输入电话号码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
        </el-form-item>
      </el-form>
      <div class="forget" @click="forgetRoom">忘记密码</div>
      <div class="login" @click="loginRoom">我已注册</div>
    </div>
  </div>
</template>

<script>

export default {
  name: "Register",
  data() {
    const validStudentNo = (rule, value, callback) => {
      this.$axios.get("/api/student/validStudentNo/"+value).then(resp=>{
        if (resp.data) {
          callback(new Error("学号重复"));
        } else {
          callback();
        }
      });
    };
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入您的密码'));
      } else {
        callback();
      }
    };
    const validateRepeatPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    const validNumber = (rule,value,callback)=> {
      if (value === '') {
        return callback(new Error('手机号码不能为空'));
      }
      let pattern = /^1[3|4|5|8|7|9][0-9]\d{8}$/;
      if (!pattern.test(value)) {
        callback(new Error('手机号格式错误'))
      } else {
        callback();
      }
    };
    return {
      registerForm: {
        studentNo: '',
        password: '',
        repeatPassword: '',
        number: ''
      },
      rules: {
        studentNo: [
          {required: true, message: '用户名不能为空', trigger: 'blur'},
          {validator: validStudentNo, trigger: 'blur'}
        ],
        password: [
          {validator: validatePass, trigger: 'blur'}
        ],
        repeatPassword: [
          {validator: validateRepeatPassword, trigger: 'blur'}
        ],
        number: [
          {validator: validNumber, trigger: 'blur'}
        ],
      },
    }
  },
  methods: {
    getHome() {
      this.$router.push('/Home')
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          //真验证
          this.$axios.post('/api/student/register', {
            studentNo: this.registerForm.studentNo,
            password: this.registerForm.password,
            number: this.registerForm.number
          }).then(response => {
            //判断后台返回的数据是否成功，如果代表成功，弹出登录成功的消息，跳转到首页
            if (response.data == 'success') {
              this.$message({
                message: '注册成功,已为你跳转至登录页面！',
                type: 'success'
              });
              this.$router.push('/Login');
            }
          }).catch(error => {
            this.$message({
              message: '出现意料之外的错误',
              type: 'error'
            });
          });
        }
      });
    },
    loginRoom() {
      this.$router.push('/Login')
    },
    forgetRoom() {
      this.$router.push('/Forget')
    }
  },


}
</script>

<style lang="scss" scoped>
h3 {
  display: inline-block;
}

.register {
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

.register-form {
  width: 400px;
  height: 420px;
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

.register-code {
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

.login {
  float: right;
  display: inline-block;
  color: #2f82ff;
  cursor: pointer;
}
</style>
