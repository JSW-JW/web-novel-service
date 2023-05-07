<template>
  <div class="novel-details">
    <section class="novel-info">
      <img :src="novel.thumbnailUrl || 'https://via.placeholder.com/150'" alt="Novel thumbnail" />
      <h1>{{ novel.title }}</h1>
      <p>{{ novel.contents }}</p>
    </section>
    <section class="chapters-list">
      <h2>Chapters</h2>
      <ul>
        <li v-for="(chapter, index) in chapters" :key="index" class="chapter-item">
          <img :src="'https://via.placeholder.com/150'" alt="Chapter thumbnail" class="chapter-thumbnail" />
          <span class="chapter-index">{{ index + 1 }}화</span>
          <span class="chapter-title">{{ chapter.title }}</span>
        </li>
      </ul>
    </section>
  </div>
</template>

<script>
export default {
  data() {
    return {
      novel: {
        id: null,
        title: '',
        thumbnailUrl: '',
        genre: '',
        description: '',
        showcaseType: null,
      },
      chapters: [],
    };
  },
  async created() {
    const novelId = this.$route.params.id;
    await this.fetchNovelDetailsAndChapters(novelId);
  },
  methods: {
    async fetchNovelDetailsAndChapters(novelId) {
      const response = await fetch(`${this.$apiBaseUrl}/chapters/list/${novelId}`);
      const jsonResponse = await response.json();

      // Update the novel and chapters data properties with the fetched data
      this.novel = jsonResponse.data.novel;
      this.chapters = jsonResponse.data.chapters;
    },
  },
};
</script>

<style scoped>
.novel-details {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 70%;
  margin: 0 auto;
  padding-top: 30px;
}

.novel-info {
  display: flex;
  width: 70%;
  margin: 0 auto;
}

.novel-info img {
  width: 200px;
  height: auto;
}

.novel-info h1 {
  margin-left: 45px;
}

.chapters-list {
  width: 70%;
  margin: 0 auto;
}

.chapters-list h2 {
  padding: 20px 0;
}

.chapters-list ul {
  list-style-type: none;
  padding: 0;
}

.chapter-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 0;
}

.chapter-thumbnail {
  width: 100px;
  height: auto;
}

.chapter-index,
.chapter-title {
  margin: 0;
}
</style>
