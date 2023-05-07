<template>
  <v-snackbar
      v-model="showSnackbar"
      :timeout="timeout"
      color="success"
      top
      right
      shaped
  >
    {{ currentMessage }}
    <v-btn color="white" text @click="showSnackbar = false">
      Close
    </v-btn>
  </v-snackbar>
</template>

<script>
export default {
  name: "ErrorSnackbar",
  props: {
    message: {
      type: String,
      default: "Something went wrong. Please contact through the channel talk",
    },
    timeout: {
      type: Number,
      default: 3000,
    },
  },
  data() {
    return {
      showSnackbar: false,
      currentMessage: "",
    };
  },
  methods: {
    open(customMessage) {
      this.currentMessage = customMessage || this.message;
      this.showSnackbar = true;
    },
  },
};
</script>
