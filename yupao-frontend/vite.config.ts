import { defineConfig } from 'vite'
import dns from "dns"
dns.setDefaultResultOrder("verbatim")
import vue from '@vitejs/plugin-vue'
import styleImport, {VantResolve} from 'vite-plugin-style-import';
// https://vitejs.dev/config/

export default defineConfig({
  server:{
    host: "localhost",
    port: 3000,
  },
  plugins: [vue(), // 配置后，Vant各组件才生效
    styleImport({
      resolves: [VantResolve()],
      libs: [
        {
          libraryName: 'vant',
          esModule: true,
          resolveStyle: (name) => `../es/${name}/style`
        }
      ]
    }),
  ]
})
