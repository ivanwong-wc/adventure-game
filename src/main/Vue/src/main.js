import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'   // 這行一定要加！

createApp(App).use(router).mount('#app')  // 這裡也要 .use(router)