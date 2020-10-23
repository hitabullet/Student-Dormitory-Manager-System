<!--
 * @Author: your name
 * @Date: 2020-10-22 11:45:29
 * @LastEditTime: 2020-10-23 10:07:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \airplane\src\views\Email.vue
-->

<template>
  <div class="hello">
     <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-reading"></i> 学生管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
       <div class="container">
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
      this.$axios.post("/mail/sendMail", this.Mail).then((response) => {
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