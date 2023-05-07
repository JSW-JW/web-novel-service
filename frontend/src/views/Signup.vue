<template>
  <v-container fluid class="main-container">
    <v-row align="center" justify="center">
      <v-col sm="6" md="4">
        <v-card class="box-container">
          <v-card-title class="heading">
            Create Your Account
          </v-card-title>
          <v-card-text>
            <v-form ref="form" @submit.prevent="submitForm">
              <v-text-field
                  id="user_name"
                  name="username"
                  label="Nickname"
                  outlined
                  v-model="name"
              ></v-text-field>
              <v-text-field
                  id="email"
                  name="email"
                  label="Email Address"
                  type="email"
                  outlined
                  v-model="email"
              ></v-text-field>
              <v-text-field
                  id="password"
                  name="password"
                  label="Password"
                  type="password"
                  outlined
                  v-model="password"
              ></v-text-field>
              <v-btn
                  class="createaccount"
                  name="commit"
                  type="submit"
                  block
                  color="primary"
              >
                Create account
              </v-btn>
            </v-form>
          </v-card-text>
          <v-divider class="login-choice"></v-divider>
          <v-card-text>
            <div class="login-choice"><span>or sign up with</span></div>
            <SocialSignUp />
          </v-card-text>
          <v-card-text class="center">
            By signing up you agree to the
            <a href="#">Terms of Service</a>.
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
    <SuccessSnackbar ref="successSnackbar" message="Registration successful!" />
    <ErrorSnackbar ref="errorSnackbar" message="Something went wrong. Please try again." />
    <v-footer fixed>
      <v-container>
        <p>Already have an account? <router-link to="/login"> Sign In</router-link></p>
      </v-container>
    </v-footer>
  </v-container>
</template>

<script>
import SocialSignUp from "@/components/SocialLogin";
import axios from "axios";
import SuccessSnackbar from "@/components/snackbar/SuccessSnackbar.vue";
import ErrorSnackbar from "@/components/snackbar/ErrorSnackbar.vue";
export default {
  name: "SignupView",
  components: {
    SuccessSnackbar,
    ErrorSnackbar,
    SocialSignUp,
  },
  data() {
    return {
      name: "",
      email: "",
      password: "",
    };
  },
  methods: {
    async submitForm() {
      if (await this.$refs.form.validate()) {
        try {
          const response = await axios.post(`${this.$apiBaseUrl}/auth/signup`, {
            name: this.name,
            email: this.email,
            password: this.password,
          });

          if (response.data.status === "OK") {
            const jwtToken = response.data.data.accessToken;
            if (jwtToken) {
              this.$jwtToken = jwtToken;
              this.$router.push("/");
              this.$refs.successSnackbar.open("Registration successful! Welcome!");
            } else {
              this.$router.push("/login");
            }
          } else {
            // The status code is not 200, handle the error case
            this.$refs.errorSnackbar.open("Something went wrong. Please try again.");
          }
        } catch (error) {
          // Handle the error (e.g., show an error message)
          console.error("Signup failed:", error);
        }
      }
    },
  }
};
</script>
