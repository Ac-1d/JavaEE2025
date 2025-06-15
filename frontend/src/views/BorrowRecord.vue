<template>
  <div class="p-6">
    <el-page-header @back="$router.back()">
      <template #title>返回</template>
      <template #content>借阅记录</template>
    </el-page-header>
    <el-table :data="records" class="mt-4" stripe border v-if="records.length">
      <el-table-column prop="title" label="书名" />
      <el-table-column prop="borrowTime" label="借出日期" />
      <el-table-column prop="returnTime" label="归还日期" />
      <el-table-column prop="status" label="状态" />
    </el-table>

    <el-empty description="暂无借阅记录" v-else />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '@/utils/request' // 确保你是从封装的axios实例导入
import { userStore } from '@/store/user' // 如果你用的是 pinia

const records = ref([])

const fetchRecords = async () => {
  try {
    const res = await axios.get(`/borrow/records/${userStore.userId}`)
    const newList = await Promise.all(
      res.data.map(async (item) => {
        const bookRes = await axios.get(`http://localhost:9090/api/books/${item.bookId}`)
        return {
          ...item,
          title: bookRes.data.title,
          status: item.returnTime ? '已归还' : '未归还',
        }
      })
    )
    console.log('newList: ', newList)
    records.value = newList
  } catch (error) {
    console.error('获取借阅记录失败', error)
  }
}

onMounted(fetchRecords)
</script>
