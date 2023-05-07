<template>
  <v-container class="main-container">
    <v-form>
      <v-card class="box-container" elevation="2">
        <v-card-title class="heading">Sign In</v-card-title>
        <v-card-text>
          <v-text-field label="Email Address" id="email" name="email" type="text" placeholder="Email Address"></v-text-field>
          <v-text-field label="Password" id="password" name="password" type="password" placeholder="Password"></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-btn block class="signIn" name="commit" type="submit">Sign In</v-btn>
        </v-card-actions>
        <v-divider class="login-choice"></v-divider>
        <SocialLogin />
      </v-card>
    </v-form>
    <v-footer class="footer">
      <p>Don't have an account? <router-link to="/signup"> Create one now</router-link></p>
    </v-footer>
  </v-container>
</template>

<script>
import SocialLogin from '@/components/SocialLogin';

export default {
  name: 'LoginView',
  components: {
    SocialLogin,
  },
};
</script>
