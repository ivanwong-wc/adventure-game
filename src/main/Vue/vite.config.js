import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  base: '/static/',           // ← 這一行決定生死！加上這行就永不404
  build: {
    outDir: '../resources/static'  // 直接吐到 Spring Boot 靜態目錄
  }
})