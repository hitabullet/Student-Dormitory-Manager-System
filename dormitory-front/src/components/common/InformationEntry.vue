<template>
  <div class="Inform">
    <div class="breadcrumb">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>信息录入</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-form ref="form" label-width="120px" :rules="rules" :model="student">
      <el-form-item label="您的学号">
        {{ showStudentNo }}
      </el-form-item>
      <el-form-item label="您的姓名" prop="name">
        <el-input v-model="student.name"/>
      </el-form-item>
      <el-form-item label="您的性别" prop="sex">
        <el-radio v-model="student.sex" label="1">男</el-radio>
        <el-radio v-model="student.sex" label="2">女</el-radio>
      </el-form-item>
      <el-form-item label="您居住的城市" prop="address">
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
      <el-form-item label="您的专业班级" prop="className" placeholder="例:软件技术">
        <el-input v-model="student.className"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('form')" :disabled="btnChangeEnable">确定录入</el-button>
        <el-button @click="resetForm('form')">清空内容</el-button>
      </el-form-item>
    </el-form>
    <div class="prompt">
      <div class="marquee-block">
        <div class="marquee">您已完成信息录入</div>
        <div class="marqueeT">您已完成信息录入</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "InformationEntry",
  inject:['reload'],   //注入App里的reload方法
  data() {
    let validName = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error('姓名不能为空'));
      }
      let pattern = /[^\u4e00-\u9fa5]/g;
      if (pattern.test(value)) {
        callback(new Error('姓名格式错误,请输入汉字,例如:周树人'))
      } else {
        callback();
      }
    };
    return {
      btnChangeEnable:true,
      student: {
        name: '',
        sex: '1',
        address: '',
        className: ''
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
        sex: [
          {required: true, trigger: 'blur'},
        ],
        name: [
          {required: true, validator: validName, trigger: 'blur'}
        ],
        address: [
          {required: true, message: '家庭住址不能为空', trigger: 'blur'},
        ],
        className: [
          {required: true, message: '专业不能为空', trigger: 'blur'},
        ]
      },
    }
  },
  methods: {
    submitForm(form) {
      this.$refs[form].validate(valid => {
        if (!valid) {
          return false;
        } else {
          this.$axios.post('/api/student/update',{
            studentNo :this.showStudentNo,
            name: this.student.name,
            sex: this.student.sex,
            address: this.student.address,
            className: this.student.className,
          }).then(resp => {
            if (resp.data) {
              this.$notify({
                type: 'success',
                message: '提交成功'
              });

              this.reload(); //页面刷新
            } else {
              this.$notify({
                type: 'error',
                message: '提交失败'
              });
              this.reload(); //页面刷新
            }
          });
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  computed: {
    showStudentNo() {
      return this.$cookies.get('studentNo')
    }
  },
  mounted() {
    this.$axios.get("/api/student/valid/" + this.showStudentNo).then(resp => {
      if (resp.data === false) {
        document.querySelector('.marquee').innerHTML = '您还未完成信息录入,请你尽快完成信息录入';
        document.querySelector('.marqueeT').innerHTML = '您还未完成信息录入,请你尽快完成信息录入';
        this.btnChangeEnable = !this.btnChangeEnable;
      }
    });
  }
}
</script>

<style lang="scss" scoped>
.Inform {
  display: flex;
  position: relative;
}

.breadcrumb {
  flex-grow: 1;
  position: absolute;
  top: 6vh;
  left: 8vw;
}

.el-form {
  flex-grow: 2;
  width: 540px;
  border-radius: 5px;
  position: absolute;
  top: 320px;
  left: 50vw;
  transform: translate(-50%, -50%);
  padding: 25px 25px 5px 25px;
  border: 1px solid #2f82ff;
  background-color: white;
}

.prompt {
  flex-grow: 1;
  /*  display: none;*/
  max-width: 100%;
  white-space: nowrap;
  overflow: hidden;
  position: relative;
  height: 20px;
  line-height: 20px;
}

.prompt:hover {
  animation-play-state: paused;
}

.prompt span {
  margin: 0 10px;
  position: absolute;
  left: 0;
  animation: marquee1 15s linear 8;
}

@keyframes marquee {
  0% {
    left: 0;
  }
  100% {
    left: -100%;
  }
}

.prompt {
  height: 30px;
  line-height: 30px;
  background-color: rgb(140, 197, 255);;
  color: #ffffff;
  position: relative;
  overflow: hidden;
  text-align: center;
  width: 100%;
  padding-right: 20px;
  z-index: 100;
}

.marquee-block {
  display: inline-block;
  width: 100%;
  height: 100%;
  vertical-align: middle;
  overflow: hidden;
  box-sizing: border-box;
  padding-left: 15px;
  position: relative;
}

.marquee {
  animation: marquee 10s linear infinite;
  white-space: nowrap;
  position: absolute;
}

.marqueeT {
  animation: marqueeT 10s linear infinite;
  white-space: nowrap;
  position: absolute;
}

@keyframes marquee {

  0% {
    left: 0;
  }

  100% {
    left: -105%;
  }

}

@keyframes marqueeT {

  0% {
    left: 105%;
  }

  100% {
    left: 0;
  }

}


</style>
