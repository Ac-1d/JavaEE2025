<template>
  <div class="register-page">
    <el-card class="register-card">
      <h2 style="text-align:center;">用户注册</h2>

      <el-form @submit.prevent="handleRegister" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="username" placeholder="请输入用户名" />
        </el-form-item>

        <el-form-item label="密码">
          <el-input v-model="password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>

        <el-form-item label="确认密码">
          <el-input v-model="confirmPassword" type="password" placeholder="请再次输入密码" show-password />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleRegister" style="width: 100%;">注册</el-button>
        </el-form-item>
      </el-form>

      <el-alert v-if="error" :title="error" type="error" show-icon class="alert" />
      <el-alert v-if="success" :title="success" type="success" show-icon class="alert" />
    </el-card>
  </div>
</template>

<script>
import axios from '@/utils/request'

export default {
  name: 'RegisterPage',
  data() {
    return {
      username: '',
      password: '',
      confirmPassword: '',
      error: '',
      success: ''
    }
  },
  methods: {
    async handleRegister() {
      this.error = ''
      this.success = ''

      if (!this.username || !this.password || !this.confirmPassword) {
        this.error = '请完整填写所有字段'
        return
      }
      if (this.password !== this.confirmPassword) {
        this.error = '两次密码输入不一致'
        return
      }

      try {
        const res = await axios.post('/users/register', {
          username: this.username,
          password: this.password,
          role: "USER"
        })
        console.log(res);
        if (res.status === 200) {
          this.success = '注册成功，请登录'
          // 清空输入
          this.username = ''
          this.password = ''
          this.confirmPassword = ''
          // 也可以跳转登录页
          this.$router.push('/login')
        } else {
          this.error = res.data.message || '注册失败'
        }
      } catch (err) {
        this.error = '请求失败，请检查网络或服务器状态'
        console.error(err)
      }
    }
  }
}
</script>

<style scoped>
.register-page {
  display: flex;
  justify-content: center;
  margin-top: 4rem;
}

.register-card {
  width: 400px;
  padding: 2rem;
  border-radius: 12px;
}

.alert {
  margin-top: 1rem;
}
</style>
