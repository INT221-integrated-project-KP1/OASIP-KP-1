import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
// https://vitejs.dev/config/


export default defineConfig({
    plugins: [vue()],
    base: `/kp1/`,
    server: {
        // proxy: {
        //     "^/api": {
        //         target: 'http://localhost/api',
        //         changeOrigin: true,
        //         secure: false,
        //         rewrite: (path) => path.replace(/^\/api/, ''),
        //         ws: true,
        //     }
        // }
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
