<template>
  <div id="app">
    <header>
      <h1>Web Novel Service</h1>
    </header>
    <section>
      <h2>New Release Best</h2>
      <div class="thumbnail-container">
        <div class="thumbnail" v-if="newReleaseTop.length">
          <img :src="newReleaseTop[0].thumbnailUrl || 'https://via.placeholder.com/150'" alt="New Release Best Novel Thumbnail" />
          <p class="title">{{ newReleaseTop[0].title }}</p>
        </div>
      </div>
    </section>
    <section>
      <h2>Best Sellers</h2>
      <div class="thumbnail-container">
        <div class="thumbnail" v-for="novel in bestSellers" :key="novel.id">
          <img :src="novel.thumbnailUrl || 'https://via.placeholder.com/150'" alt="Best Seller Novel Thumbnail" />
          <p class="title">{{ novel.title }}</p>
        </div>
      </div>
    </section>
  </div>
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
        const response = await axios.post("http://localhost:8080/api/v1/novels/home-best", {
          showcaseTypeIds: [1, 2]
        });
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



<style>
body {
  margin: 0;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
}

header {
  background-color: #2c3e50;
  color: #fff;
  padding: 20px;
  text-align: center;
}

h1 {
  margin: 0;
}

h2 {
  margin-bottom: 20px;
}

.thumbnail-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.thumbnail {
  width: 150px;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.thumbnail img {
  width: 100%;
  height: auto;
}

.title {
  margin-top: 10px;
}
</style>
