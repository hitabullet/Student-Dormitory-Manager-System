<template>
  <div class="DormRe">
    <div class="breadcrumb">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>宿舍报修</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-form ref="form" label-width="95px" :rules="rules" :model="repairs">
      <el-form-item label="您的学号">
        {{ showStudentNo }}
      </el-form-item>
      <el-form-item label="您的宿舍" prop="chamber">
        <el-select v-model="repairs.chamber" placeholder="请选择">
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
      <el-form-item label="宿舍损坏处" prop="message">
        <el-input v-model="repairs.message" type="text" maxlength="6"/>
      </el-form-item>
      <el-form-item label="报修时间" prop="repairtime">
        <el-date-picker
            v-model="repairs.repairtime"
            type="date"
            value-format=" yyyy-MM-dd "
            format=" yyyy-MM-dd "
            placeholder="选择日期"
            style="width: 100%;">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit('form')" :disabled="!btn">提交</el-button>
      </el-form-item>
    </el-form>
    <div class="prompt">
      <div class="marquee-block">
        <div class="marquee">您可以向宿管进行宿舍报修</div>
        <div class="marqueeT">您可以向宿管进行宿舍报修</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "DormitoryRepair",
  inject: ['reload'],   //注入App里的reload方法
  data() {
    return {
      btn: true,
      repairs: {
        chamber: '',
        message: '',
        repairtime: ''
      },
      cities: [{
        label: 'C232'
      }, {
        label: 'C233'
      }, {
        label: 'B234'
      }],
      rules: {
        chamber: [
          {required: true, message: '报修宿舍不能为空', trigger: 'blur'}
        ],
        message: [
          {required: true, message: '损坏处不能为空', trigger: 'blur'}
        ],
        repairtime: [
          {required: true, message: '报修时间不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    submit(form) {
      this.$refs[form].validate(valid => {
        if (!valid) {
          return false;
        } else {
          this.$axios.post('/api/DormRepairs/add', {
            id: this.repairs.id,
            chamber: this.repairs.chamber,
            message: this.repairs.message,
            repairtime: this.repairs.repairtime,
            flag: this.repairs.flag
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
  },
  computed: {
    showStudentNo() {
      return this.$cookies.get('studentNo')
    }
  },
  mounted() {
    this.$axios.get("/api/DormRepairs/valid/" + this.showStudentNo).then(resp => {
      if (resp.data === false) {
        /*document.querySelector('.prompt').style.display = 'block';*/
        document.querySelector('.marquee').innerHTML = '您暂未入住宿舍...';
        document.querySelector('.marqueeT').innerHTML = '您暂未入住宿舍...';
        this.btn = !this.btn;
      }
    });
  }
}
</script>

<style lang="scss" scoped>
.DormRe {
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

.el-select {
  width: 100%;
}

.el-button {
  width: 100%;
}
</style>
