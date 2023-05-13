import { createRouter, createWebHistory } from 'vue-router';
import NovelDetails from '../views/NovelDetails.vue';
import Login from "@/views/Login.vue";
import Signup from "@/views/Signup.vue";
import OAuth2Redirect from "@/views/OAuth2Redirect.vue";

const routes = [
    {
        path: '/novels/:id',
        name: 'NovelDetails',
        component: NovelDetails,
    },
    {
        path: '/signup',
        name: 'SignupView',
        component: Signup,
    },
    {
        path: '/login',
        name: 'LoginView',
        component: Login,
    },
    {
        path: "/oauth2/redirect",
        name: "OAuth2Redirect",
        component: OAuth2Redirect,
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;
