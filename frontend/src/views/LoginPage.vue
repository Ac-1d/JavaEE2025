<template>
  <div class="login-page">
    <el-card class="login-card" shadow="hover">
      <h2 class="title">用户登录</h2>
      <el-form>
        <el-form-item label="用户名">
          <el-input v-model="username" placeholder="请输入用户名" clearable />
        </el-form-item>

        <el-form-item label="密码">
          <el-input v-model="password" placeholder="请输入密码" show-password />
        </el-form-item>

        <el-form>
          <!-- 表单项 -->
          <el-form-item>
            <el-button type="primary" @click="handleLogin" class="login-button" block>
              登录
            </el-button>
          </el-form-item>
        </el-form>
      </el-form>

      <el-alert v-if="error" :title="error" type="error" show-icon class="error-alert" center closable
        @close="error = ''" />
    </el-card>
  </div>
</template>

<script>
import axios from '@/utils/request'
import { userStore } from '@/store/user'

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
          userStore.setUser(res.data.username, res.data.id, res.data.role)
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
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f7fa;
}

.login-card {
  width: 400px;
  padding: 2rem;
}

.title {
  text-align: center;
  margin-bottom: 1rem;
}

.login-button {
  width: 100%;
}

.error-alert {
  margin-top: 1rem;
}
</style>