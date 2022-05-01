import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
// https://vitejs.dev/config/
<<<<<<< HEAD



  // import.meta.env.VITE_NAME available here with: process.env.VITE_NAME
  // import.meta.env.VITE_PORT available here with: process.env.VITE_PORT



  export default defineConfig({
    plugins: [vue()],
    Server: {
      proxy: {
        "/api": {
          target : "http://ip21kp1.sit.kmutt.ac.th:8080",
          changeOrigin: true,
          secure: false,
          rewrite: (path) => path.replace(/^\/api/, ''),
        },
      },
    },
  });
=======
export default defineConfig({
    plugins: [vue()],
    // server: {
    //     proxy: {
    //         '/foo': 'http://localhost:4567',
    //         "^/api": {
    //             target: "http://10.4.56.84:5000",
    //             changeOrigin: true,
    //             secure: false,
    //             rewrite: (path) => path.replace(/^\/api/, ''),
    //             ws: true,
    //         }
    //     }
    // }
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
>>>>>>> 9b5a4982adbb3d88e130663759ee53ed239ce22c
