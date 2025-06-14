<template>
  <div class="login-page">
    <h2>用户登录</h2>
    <form @submit.prevent="handleLogin">
      <div>
        <label for="username">用户名：</label>
        <input id="username" v-model="username" required />
      </div>
      <div>
        <label for="password">密码：</label>
        <input id="password" type="password" v-model="password" required />
      </div>
      <button type="submit">登录</button>
    </form>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script>
import axios from '@/utils/request'
import {userStore} from '@/store/user'

export default {
  name: 'LoginPage',
  data() {
    return {
      username: '',
      password: '',
      error: ''
    }
  },
  methods: {
    async handleLogin() {
      try {
        const res = await axios.post('/users/login', {
          username: this.username,
          password: this.password
        })

        if (res.status === 200) {
          userStore.setUser(res.data.username)
          this.$router.push('/')
        } else {
          this.error = res.data.message || '登录失败'
        }
      } catch (err) {
        this.error = '账号或密码错误，请重试'
        console.error(err)
      }
    }
  }
}
</script>

<style scoped>
.login-page {
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
</style>
