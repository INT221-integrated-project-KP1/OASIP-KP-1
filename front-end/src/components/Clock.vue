<script setup>
import {ref} from "vue";
const time = ref()
const timeStr = ref()
const setTime = () => {
  time.value = new Date().toString().split(" ", 5)
  timeStr.value = ''
  for(let i=0; i<time.value.length; i++){
    timeStr.value += ' ' + time.value[i];
  }
}

setInterval(() => setTime(), 1000);

</script>
 
<template>
<div>{{ timeStr }}</div>
</template>
 
<style>

</style>