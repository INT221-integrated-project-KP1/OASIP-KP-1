import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './index.css'
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

