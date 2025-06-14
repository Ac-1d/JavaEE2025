import axios from 'axios'

const instance = axios.create({
  baseURL: 'http://localhost:9090/api', // 后端服务地址
  timeout: 5000
})

export default instance
