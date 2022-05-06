import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
// https://vitejs.dev/config/


export default defineConfig({
    base: `/kp1`,
    plugins: [vue()],
    server: {
        proxy: {
            "^/api": {
                target: 'http://intproj21.sit.kmutt.ac.th/kp1/api',
                changeOrigin: true,
                secure: false,
                rewrite: (path) => path.replace(/^\/api/, ''),
                ws: true,
            },
            "^/": {
                target: '/kp1',
                changeOrigin: true,
                secure: false,
                rewrite: (path) => path.replace(/^\//, ''),
                ws: true,
            }
        }
    }
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
