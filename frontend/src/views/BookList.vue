<template>
  <div class="book-list">
    <h2>图书列表</h2>
    <!-- 新增图书按钮 -->
    <button
      @click="showAddForm = true"
      class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 mb-4"
    >
      新增图书
    </button>

    <!-- 新增图书表单 -->
    <div v-if="showAddForm" class="border p-4 mb-4 rounded bg-gray-100">
      <h3 class="text-lg font-semibold mb-2">添加新图书</h3>
      <input v-model="newBook.title" placeholder="书名" class="border px-2 py-1 mb-2 w-full" />
      <input v-model="newBook.author" placeholder="作者" class="border px-2 py-1 mb-2 w-full" />
      <input
        v-model.number="newBook.availableCount"
        type="number"
        placeholder="数量"
        class="border px-2 py-1 mb-2 w-full"
      />
      <div>
        <button
          @click="addBook"
          class="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700 mr-2"
        >
          提交
        </button>
        <button
          @click="showAddForm = false"
          class="bg-gray-400 text-white px-3 py-1 rounded hover:bg-gray-500"
        >
          取消
        </button>
      </div>
    </div>
    <div v-if="books.length === 0">暂无图书信息</div>
    <ul>
      <li v-for="book in books" :key="book.id" class="book-item">
        <div>
          <strong>{{ book.title }}</strong> - {{ book.author }}    剩余：{{ book.stock }}本
        </div>
        <button @click="borrowBook(book.id)">借阅</button>
        <button @click="returnBook(book.id)">归还</button>
        <button @click="deleteBook(book.id)">删除</button>
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
      books: [],
      showAddForm: false,
      newBook: {
        title: '',
        author: '',
        availableCount: 1
      }
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
    },
    async deleteBook(id) {
      if (!confirm('确认删除这本图书吗？')) return

      try {
        await axios.delete(`/books/${id}`)
        this.books = this.books.filter(book => book.id !== id)
      } catch (err) {
        console.error('删除图书失败', err)
        alert('删除失败')
      }
    },
    async addBook() {
      if (!this.newBook.title || !this.newBook.author) {
        alert('请填写完整信息')
        return
      }
      try {
        await axios.post('/books', this.newBook)
        this.showAddForm = false
        this.newBook = { title: '', author: '', availableCount: 1 }
        await this.fetchBooks()
      } catch (err) {
        alert('添加失败')
        console.error(err)
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
