<template>
  <div class="forget">
    <div class="forget-form">
      <h3>忘记密码</h3>
      <span @click="getHome()">
        <i class="icon iconfont icon-fanhui"></i>
        <h5>返回主页</h5>
      </span>
      <el-form :model="forgetForm" status-icon :rules="rules" ref="ruleForm">
        <el-form-item prop="studentNo">
          <el-input prefix-icon="el-icon-user-solid" v-model="forgetForm.studentNo" placeholder="请输入学号"></el-input>
        </el-form-item>
        <el-form-item prop="number">
          <el-input prefix-icon="el-icon-user-solid" v-model="forgetForm.number" placeholder="请输入电话号码"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" autocomplete="off"
                    v-model="forgetForm.password" show-password
                    prefix-icon="el-icon-lock" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="repeatPassword">
          <el-input type="password" autocomplete="off"
                    v-model="forgetForm.repeatPassword" show-password
                    prefix-icon="el-icon-lock" placeholder="请再次输入密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit('ruleForm')">忘记密码</el-button>
        </el-form-item>
      </el-form>
      <div class="login" @click="loginRoom">我已注册</div>
      <div class="register" @click="registerRoom">我未注册</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Forget",
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
    const validateRepeatPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.forgetForm.password) {
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
      forgetForm: {
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
  methods:{
    getHome() {
      this.$router.push('/Home')
    },
    loginRoom() {
      this.$router.push('/Login')
    },
    registerRoom() {
      this.$router.push('/Register')
    },
    submit(formName) {
      this.$refs[formName].validate(valid => {
        if (!valid) {
          return false;
        } else {
          this.$axios.post('/api/student/forget',{
            studentNo: this.forgetForm.studentNo,
            number: this.forgetForm.number,
            password: this.forgetForm.password,
          }).then(resp => {
            if (resp.data) {
              this.$notify({
                type: 'success',
                message: '密码修改成功,为您前往登录页面'
              });
              this.$router.push("/Login")
            } else {
              this.$notify({
                type: 'error',
                message: '密码修改失败,请重新输入！'
              });
              this.$router.push('/Forget')
            }
          });
        }
      })
    },

  }
}
</script>

<style lang="scss" scoped>
h3 {
  display: inline-block;
}
.forget {
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

.forget-form {
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

.el-form .el-form-item:last-child .el-button {
  width: 100%;
}

.login {
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
</style>
