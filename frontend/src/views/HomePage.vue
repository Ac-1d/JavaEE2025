<template>
  <div>
    <nav class="navbar">
      <div class="nav-left">
        <h1>图书管理系统</h1>
      </div>
      <div class="nav-right">
        <template v-if="!userStore.isLoggedIn">
          <router-link to="/login" class="nav-button">登录</router-link>
          <router-link to="/register" class="nav-button">注册</router-link>
        </template>
        <template v-else>
          <span class="welcome-text">欢迎，{{ userStore.username }}</span>
          <button @click="logout" class="nav-button logout-button">退出</button>
        </template>
      </div>
    </nav>

    <main class="main-content">
      <h2>欢迎来到图书管理系统</h2>
      
      <BookList/>
    </main>
  </div>
</template>

<script>
import { userStore } from '@/store/user'
import BookList from '@/views/BookList.vue'

export default {
  name: 'HomePage',
  setup() {
    const logout = () => {
      userStore.clearUser()
    }
    return { userStore, logout }
  },
  components: {
    BookList
  }
}
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background-color: #42b983;
  color: white;
}

.nav-button {
  margin-left: 1rem;
  padding: 0.5rem 1rem;
  background-color: white;
  color: #42b983;
  border: none;
  border-radius: 4px;
  font-weight: bold;
  cursor: pointer;
  text-decoration: none;
  transition: background-color 0.3s ease;
}

.nav-button:hover {
  background-color: #3a9d75;
  color: white;
}

.logout-button {
  background-color: #e74c3c;
}

.logout-button:hover {
  background-color: #c0392b;
}

.welcome-text {
  font-weight: bold;
}
.main-content {
  padding: 2rem;
  text-align: center;
}
</style>
