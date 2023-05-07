<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>
        Web Novel Service
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text @click="$router.push('/')">Home</v-btn>
      <v-btn text @click="$router.push('/signup')">Sign up</v-btn>
      <v-btn text @click="$router.push('/login')">Login</v-btn>
    </v-app-bar>
    <v-main>
      <div id="app">
        <router-view></router-view>
        <section class="home-best-section" v-if="$route.path === '/'">
          <h2>New Release Best</h2>
          <div class="thumbnail-container">
            <div class="thumbnail" v-if="newReleaseTop.length">
              <router-link :to="`/novels/${newReleaseTop[0].id}`">
                <img :src="newReleaseTop[0].thumbnailUrl || 'https://via.placeholder.com/150'" alt="New Release Best Novel Thumbnail" />
                <p class="title">{{ newReleaseTop[0].title }}</p>
              </router-link>
            </div>
          </div>
        </section>
        <section class="home-best-section" v-if="$route.path === '/'">
          <h2>Best Sellers</h2>
          <div class="thumbnail-container">
            <div class="thumbnail" v-for="novel in bestSellers" :key="novel.id">
              <router-link :to="`/novels/${novel.id}`">
                <img :src="novel.thumbnailUrl || 'https://via.placeholder.com/150'" alt="Best Seller Novel Thumbnail" />
                <p class="title">{{ novel.title }}</p>
              </router-link>
            </div>
          </div>
        </section>
      </div>
    </v-main>
  </v-app>
</template>

<script>
import axios from "axios";

export default {
  name: "App",
  data() {
    return {
      bestSellers: [],
      newReleaseTop: [],
    };
  },
  methods: {
    async fetchData() {
      try {
        const response = await axios.get("http://localhost:8080/api/v1/novels/home-best");
        this.newReleaseTop = response.data.data.new_release_top;
        this.bestSellers = response.data.data.best_seller;
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },
  },
  created() {
    this.fetchData();
  },
};
</script>
