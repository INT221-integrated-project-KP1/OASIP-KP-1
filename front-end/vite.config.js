import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    server: {
        proxy: {
            "^/api": {
                target: "http://ip21pl1.sit.kmutt.ac.th:8080/api",
                changeOrigin: true,
                secure: false,
                rewrite: (path) => path.replace(/^\/api/, ''),
                ws: true,
            }
        },
        proxy: {
            "^/api": {
                target: "http://10.4.84.106:8080/api",
                changeOrigin: true,
                secure: false,
                rewrite: (path) => path.replace(/^\/api/, ''),
                ws: true,
            }
        }
    },

})

// module.exports={
//     devServer: {
//         proxy: {
//           "^/api/": {
//             target: "http://api:3000",
//             secure: false,
//             pathRewrite: {
//               "/api/*": "/"
//             }
//           }
//         }
//       }
//     };
