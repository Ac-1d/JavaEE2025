<template>
  <div class="book-list p-6">
    <el-page-header content="图书管理" class="mb-4" />

    <!-- 新增图书按钮 -->
    <el-button type="success" @click="showAddDialog = true" class="mb-4">添加图书</el-button>

    <!-- 图书为空时的提示 -->
    <el-empty description="暂无图书信息" v-if="books.length === 0" />

    <!-- 图书列表 -->
    <el-row :gutter="20" v-else>
      <el-col :span="12" v-for="book in books" :key="book.id" class="mb-4">
        <el-card shadow="hover">
          <div class="flex justify-between items-center">
            <div>
              <h3 class="text-lg font-bold">{{ book.title }}</h3>
              <p>作者：{{ book.author }}</p>
              <p>剩余：{{ book.stock }} 本</p>
            </div>
            <div class="flex flex-col gap-2">
              <el-button type="primary" size="small" @click="borrowBook(book.id)">借阅</el-button>
              <el-button type="warning" size="small" @click="returnBook(book.id)">归还</el-button>
              <el-button type="danger" size="small" @click="deleteBook(book.id)">删除</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 添加图书 Dialog -->
    <el-dialog v-model="showAddDialog" title="添加图书" width="500px">
      <el-form :model="newBook" label-width="80px">
        <el-form-item label="书名">
          <el-input v-model="newBook.title" placeholder="请输入书名" />
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="newBook.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="数量">
          <el-input-number v-model="newBook.stock" :min="1" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="addBook">提交</el-button>
      </template>
    </el-dialog>
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
      showAddDialog: false,
      newBook: {
        title: '',
        author: '',
        stock: 1
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
        console.log(this.newBook)
        this.showAddForm = false
        this.newBook = { title: '', author: '', stock: 1 }
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
