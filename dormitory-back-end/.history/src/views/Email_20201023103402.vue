<!--
 * @Author: your name
 * @Date: 2020-10-22 11:45:29
 * @LastEditTime: 2020-10-23 10:34:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \airplane\src\views\Email.vue
-->
<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item> <i class="el-icon-reading"></i> 联系我们 </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-form :model="Mail" ref="SendEmail" :rules="rules" style="width: 500px; height: 300px; margin: 12px auto auto">
                <el-form-item prop="subject">
                    <h5 style="text-align: left">我们的工作人员将在3个工作日内将处理结果发送至您的电子邮箱中。</h5>
                    <el-input placeholder="您的姓名" v-model="Mail.subject" clearable></el-input>
                </el-form-item>
                <el-form-item prop="receiver">
                    <el-input placeholder="您的E-mail" v-model="Mail.receiver" clearable></el-input>
                </el-form-item>

                <el-form-item prop="text">
                    <el-input type="textarea" placeholder="请输入内容" v-model="Mail.text" maxlength="100" show-word-limit />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submit">发送</el-button>

                    <el-button type="info" @click="resetForm('SendEmail')">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    name: 'HelloWorld',
    data() {
        const validUser = (rule, value, callback) => {
          let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
              if (!reg.test(value)) {
                callback(new Error('邮箱，请确认您的用户名是否输入错误！'));
            } else {
                callback();
            }
            if (value === '') {
                callback(new Error('请输入您的用户名'));
            } else {
                callback();
            }
        };
        return {
            Mail: {
                subject: '',
                receiver: '',
                text: ''
            },
            rules: {
                subject: [{ required: true, message: '邮箱不能为空', trigger: 'blur' }],
                receiver: [
                    { required: true, message: '邮箱不能为空', trigger: 'blur' },
                    { validator: validReceiver, trigger: 'blur' }
                ],
                text: [{ required: true, message: '邮箱不能为空', trigger: 'blur' }]
            }
        };
    },
    methods: {
        submit() {
            this.$axios.post('/api/mail/sendMail', this.Mail).then((response) => {
                if (response.data === 'success') {
                    this.$message({
                        showClose: false,
                        message: '发送成功',
                        type: 'success'
                    });
                }
                this.resetForm('SendEmail');
            });
        },
        resetForm(fromName) {
            this.$refs[fromName].resetFields();
        }
    }
};
</script>
<style lang="scss" scoped>
.el-input {
    margin-bottom: 10px;
}
.el-button {
    margin-left: 100px;
    width: 80px;
}
</style>