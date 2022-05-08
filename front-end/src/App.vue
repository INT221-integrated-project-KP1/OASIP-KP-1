<script setup>
import { ref } from 'vue'
const active = ref(['tab tab-active', 'tab'])
const changeTab = (currentTab) => {
  console.log(currentTab)
  for (let i = 0; i < active.value.length; i++) {
    if (currentTab === i)
      {active.value[i] = 'tab tab-active'}
    else active.value[i] = 'tab'
  }
}

</script>

<template>
  <div class="navbar bg-base-100">
    <div class="flex-1">
      <a class="btn btn-ghost normal-case text-xl">Gay</a>
    </div>
    <div class="tabs tabs-boxed">
      <router-link :to="{ name: 'Home' }" :class=active[0] @click="changeTab(0)">Home </router-link>
      <router-link :to="{ name: 'Booking' }" :class=active[1] @click="changeTab(1)">Add event</router-link>
    </div>
  </div>

  <div>
    <router-view></router-view>
  </div>
</template>

<style scoped>


</style>