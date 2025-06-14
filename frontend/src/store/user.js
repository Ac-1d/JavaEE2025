import { reactive } from 'vue'

export const userStore = reactive({
  username: null,
  isLoggedIn: false,
  setUser(name) {
    this.username = name
    this.isLoggedIn = true
  },
  clearUser() {
    this.username = null
    this.isLoggedIn = false
  }
})
