<script setup>
import { ref } from "vue";
const time = ref()
const timeStr = ref()


const setTime = () => {
  const date = new Date()
  time.value = date.toDateString()
  timeStr.value = date.toTimeString().slice(0, 8)
}

setInterval(() => setTime(), 1000);

</script>
 
<template>
  <div class="stats shadow">
    <div class="stat">
      <div class="stat-title">{{ time }}</div>
      <div class="stat-value">{{ timeStr }}</div>
    </div>
  </div>
</template>
 
<style>
</style>