// src/router/index.js   ← 直接把這整段取代掉你原本的
import { createRouter, createWebHistory } from 'vue-router'

import StartPage from '../components/StartPage.vue'
import ChoosePage from '../components/ChoosePage.vue'          // 正確名稱
import BattlePage from '../components/BattlePage.vue'
import EventPage from '../components/EventPage.vue'
import EndPage from '../components/EndPage.vue'
import ShopPage from '../components/ShopPage.vue'
import WinPage from '../components/WinPage.vue'

const routes = [
  { path: '/',                  component: StartPage },
  { path: '/ChoosePage',            component: ChoosePage },     // 改成 /choose 比較短又好記
  { path: '/battlepage',            component: BattlePage },
  { path: '/EventPage',             component: EventPage },
  { path: '/EndPage',               component: EndPage },
  { path: '/ShopPage',              component: ShopPage },
  { path: '/WinPage',               component: WinPage },
  { path: '/:pathMatch(.*)*',   redirect: '/' }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router