<!--
 * @Author: your name
 * @Date: 2020-10-22 11:45:29
 * @LastEditTime: 2020-10-22 14:35:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \airplane\src\views\Email.vue
-->

<template>
  <div class="hello">
    <el-row>
      <el-col :span="8">
        <div style="width: 450px; height: 500px"></div>
      </el-col>
      <el-col :span="8">
        <div style="width: auto; height: auto">
          <el-form :model="Mail" ref="SendEmail" style="width: 500px">
            <el-form-item prop="subject">
              <h1>联系我们</h1>
              <h5 style="text-align: left">
                我们的工作人员将在3个工作日内将处理结果发送至您的电子邮箱中。
              </h5>
              <el-input
                placeholder="您的姓名"
                v-model="Mail.subject"
                clearable
              ></el-input>
            </el-form-item>
            <el-form-item prop="receiver">
              <el-input
                placeholder="您的E-mail"
                v-model="Mail.receiver"
                clearable
              ></el-input>
            </el-form-item>

            <el-form-item prop="text">
              <el-input
                type="textarea"
                placeholder="请输入内容"
                v-model="Mail.text"
                maxlength="100"
                show-word-limit
              />
            </el-form-item>
            <el-form-item>
              <el-button
                type="success"
                @click="submit"
                round
                >发送</el-button
              >

              <el-button type="info"  @click="resetForm('SendEmail')" round>重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
      <el-col :span="8">
        <div style="width: 450px; height: 500px"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "HelloWorld",
  data() {
    return {
      Mail: {
        subject: "",
        receiver: "",
        text: "",
      },
    };
  },
  methods: {
    submit() {
      axios.post("/mail/sendMail", this.Mail).then((response) => {
        if (response.data === "success") {
          this.$message({
            showClose:false,
            message:"发送成功",
            type:"success"
          })
        }
        this.resetForm('SendEmail');
      });
    },
    resetForm(fromName) {
      this.$refs[fromName].resetFields();
    }
  }
}
</script>
<style>
</style>