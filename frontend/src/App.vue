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
        <template v-for="sectionType in bestSectionTypes">
          <section
              class="home-best-section"
              v-if="$route.path === '/'"
              :key="sectionType.title"
          >
            <h2>{{ sectionType.title }}</h2>
            <div class="thumbnail-container">
              <div
                  class="thumbnail"
                  v-for="novel in sectionType.novels"
                  :key="novel.id"
              >
                <router-link :to="`/novels/${novel.id}`">
                  <img
                      :src="novel.thumbnailUrl || 'https://via.placeholder.com/150'"
                      :alt="`${sectionType.title} Novel Thumbnail`"
                  />
                  <p class="title">{{ novel.title }}</p>
                </router-link>
              </div>
            </div>
          </section>
        </template>
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
      bestSectionTypes: [],
    };
  },
  methods: {
    mapSectionType(title, novels) {
      return { title, novels };
    },

    getTitle(fieldName, data) {
      return data[fieldName].title;
    },

    async fetchData() {
      try {
        const response = await axios.get(
            "http://localhost:8080/api/v1/novels/home-best"
        );
        const data = response.data.data;

        for (const key in data) {
          const title = this.getTitle(key, data);
          this.bestSectionTypes.push(this.mapSectionType(title, data[key].novels));
        }
        console.log(this.bestSectionTypes);
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
