<script setup>
import { ref, onBeforeMount, onBeforeUnmount } from 'vue'
let active = ref(['tab tab-active','tab'])
const changeTab = (currentTab) => {
  console.log(currentTab)
  for (let i = 0; i < active.value.length; i++) {
    if (currentTab === i) { active.value[i] = 'tab tab-active' ;}
    else active.value[i] = 'tab' 
  }
}

const setlocal = (current) => {
changeTab(current);
localStorage.setItem('active', active.value[current]);
}

active.value = localStorage.getItem('active')



</script>

<template>
  <div class="navbar bg-base-100">
    <div class="flex-1">
      <a class="btn btn-ghost normal-case text-xl">Daimodd</a>
    </div>
    <div class="tabs tabs-boxed">
      <router-link :to="{ name: 'Home' }" :class=active[0] @click="setlocal(0)">Home </router-link>
      <router-link :to="{ name: 'Booking' }" :class=active[1] @click="setlocal(1)"> Add event </router-link>
    </div>
  </div>

  <div>
    <router-view></router-view>
  </div>
</template>

<style scoped>
</style>