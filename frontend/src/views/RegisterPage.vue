<template>
  <div class="register-page">
    <h2>用户注册</h2>
    <form @submit.prevent="handleRegister">
      <div>
        <label for="username">用户名：</label>
        <input id="username" v-model="username" required />
      </div>
      <div>
        <label for="password">密码：</label>
        <input id="password" type="password" v-model="password" required />
      </div>
      <div>
        <label for="confirmPassword">确认密码：</label>
        <input id="confirmPassword" type="password" v-model="confirmPassword" required />
      </div>
      <button type="submit">注册</button>
    </form>
    <p v-if="error" class="error">{{ error }}</p>
    <p v-if="success" class="success">{{ success }}</p>
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
  max-width: 400px;
  margin: 3rem auto;
  padding: 2rem;
  border: 1px solid #ddd;
  border-radius: 8px;
}
input {
  display: block;
  width: 100%;
  padding: 0.5rem;
  margin-top: 0.2rem;
  margin-bottom: 1rem;
}
button {
  width: 100%;
  padding: 0.5rem;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.error {
  color: red;
  margin-top: 1rem;
  text-align: center;
}
.success {
  color: green;
  margin-top: 1rem;
  text-align: center;
}
</style>
