<template>
  <div class="book-list">
    <h2>图书列表</h2>
    <div v-if="books.length === 0">暂无图书信息</div>
    <ul>
      <li v-for="book in books" :key="book.id" class="book-item">
        <div>
          <strong>{{ book.title }}</strong> - {{ book.author }}    剩余：{{ book.stock }}本
        </div>
        <button @click="borrowBook(book.id)">借阅</button>
        <button @click="returnBook(book.id)">归还</button>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from '@/utils/request'
import { userStore } from '@/store/user'

export default {
  name: 'BookList',
  data() {
    return {
      books: []
    }
  },
  created() {
    this.fetchBooks()
  },
  methods: {
    async fetchBooks() {
      const res = await axios.get('/books')
      console.log(res.data)
      this.books = res.data
    },
    async borrowBook(bookId) {
      if (!userStore.isLoggedIn) {
        alert('请先登录')
        this.$router.push('/login')
        return
      }
      try {
        const res = await axios.post(`/borrow/borrow`, null, {
          params: {
            userId: userStore.username, // 假设 username 是用户 ID
            bookId: bookId
          }
        })
        this.fetchBooks()
        alert(res.data)
      } catch (err) {
        alert('借阅失败：' + err.response?.data || err.message)
      }
    },
    async returnBook(bookId) {
      if (!userStore.isLoggedIn) {
        alert('请先登录')
        this.$router.push('/login')
        return
      }
      try {
        const res = await axios.post(`/borrow/return`, null, {
          params: {
            userId: userStore.username, // 假设 username 是用户 ID
            bookId: bookId
          }
        })
        this.fetchBooks()
        alert(res.data)
      } catch (err) {
        alert('归还失败：' + err.response?.data || err.message)
      }
    }
  }
}
</script>

<style scoped>
.book-list {
  padding: 2rem;
}
.book-item {
  margin: 1rem 0;
  display: flex;
  justify-content: space-between;
}
</style>
