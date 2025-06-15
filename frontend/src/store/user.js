import { reactive } from 'vue'

export const userStore = reactive({
  username: null,
  isLoggedIn: false,
  userId: null,
  role: null,
  setUser(name, userId, role) {
    this.username = name
    this.isLoggedIn = true
    this.userId = userId
    this.role = role
  },
  clearUser() {
    this.username = null
    this.isLoggedIn = false
    this.userId = null
    this.role = null
  }
})
