import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './index.css'
import { createPinia } from 'pinia'
// createApp(App).use(createPinia()).mount('#app')
// createApp(App).use(router).mount('#app')

const app = createApp(App)
app.use(router)
app.use(createPinia())
app.mount('#app')

