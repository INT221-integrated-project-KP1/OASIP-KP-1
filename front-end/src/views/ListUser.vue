<script setup>

import ListUser from "../components/ListUser.vue";
import { userData } from "../stores/userData.js"
import { cookieData } from "../stores/cookieData"

const cookie = cookieData();
const myUserData = userData();


</script>

<template>
  <div>
    <div v-if="cookie.getCookie('role') !== 'ADMIN'" class="grid justify-items-center m-10 p-5">
      <div class="card w-96 glass">
        <figure><img src="../assets/AdminPNG/nope.jpg" alt="gif2"></figure>
        <div class="card-body">
          <h2 class="card-title">You have no permission to use it.</h2>
          <p>Please Log in to your account have role in the admin</p>
        </div>
      </div>
    </div>
    <div v-else>
      <ListUser></ListUser>
    </div>
  </div>
</template>

<style scoped>

</style>
