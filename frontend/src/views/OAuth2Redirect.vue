<template>
  <div>
    Processing authentication...
  </div>
</template>

<script>
export default {
  name: "OAuth2Redirect",
  mounted() {
    const jwtToken = this.$route.query.token;

    if (jwtToken) {
      this.$jwtToken = jwtToken;
      this.$router.push("/");

      console.log(this.$jwtToken);
    } else {
      this.$router.push("/login");
    }
  },
};
</script>
