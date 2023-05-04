import { createRouter, createWebHistory } from 'vue-router';
import NovelDetails from '../views/NovelDetails.vue';

const routes = [
    {
        path: '/novels/:id',
        name: 'NovelDetails',
        component: NovelDetails,
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;
