import { createApp } from 'vue';
import App from './App.vue';
import router from './router/index';

const app = createApp(App);
app.use(router);

// Use provide and inject to set up a global property
app.config.globalProperties.$apiBaseUrl = 'http://localhost:8080/api/v1';

app.mount('#app');
