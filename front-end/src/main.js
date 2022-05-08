import { createApp } from 'vue'
import './index.css'
import App from './App.vue'
import router from './router'

// createApp(App).use(router).mount('#app')

const app = createApp(App)
app.use(router)
// app.use(
//     proxy("/api", {
//         target: "http://10.4.56.84:3000",
//         secure: false,
//         changeOrigin: true
//     })
// )
app.mount('#app')

