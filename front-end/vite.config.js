import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
const url = import.meta.url 
// https://vitejs.dev/config/

export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      "/api": {
        // target: host+":"+port,
        target: "http://10.4.56.84:5000",
        changeOrigin: true,
        secure: false,
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
});
