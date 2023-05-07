import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router.js'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'

import '@mdi/font/css/materialdesignicons.css'
import '@/assets/css/style.css'
import 'vuetify/styles'

loadFonts()


const app = createApp(App);
const baseUrl = process.env.VUE_APP_BASE_URL;

app.use(router);
app.use(vuetify);

// Use provide and inject to set up a global property
app.config.globalProperties.$apiBaseUrl = 'http://localhost:8080/api/v1';
app.config.globalProperties.$oauth2Redirect = baseUrl + '/oauth2/redirect';
app.config.globalProperties.$jwtToken = null;

app.mount('#app')
