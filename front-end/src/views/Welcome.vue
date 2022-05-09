<script setup>
    import {useRoute, useRouter} from 'vue-router'
    const { params } = useRoute()
    const myRouter = useRouter()
    const goHome = () => myRouter.push({name:'Home'})
</script>
 
<template>
<div class="absolute">
  <span class="animate-ping-fast relative inline-flex rounded-lg h-12 w-12 inset-y-[23rem] left-[12rem] bg-primary"></span>
  <span class="animate-ping-fast relative inline-flex rounded-lg h-5 w-5 inset-y-[17rem] left-[49rem] bg-primary"></span>
  <span class="animate-ping-fast relative inline-flex rounded-lg h-3 w-3  inset-y-[10rem] left-[6rem] bg-primary"></span>
  <span class="animate-ping-slow relative inline-flex rounded-lg h-10 w-10 inset-y-[36rem] left-[64rem] bg-primary"></span>
  <span class="animate-ping-slow relative inline-flex rounded-lg h-14 w-14 inset-y-[2rem] left-[24rem] bg-primary"></span>
  <span class="animate-ping-slow relative inline-flex rounded-lg h-6 w-6  inset-y-[38rem] left-[6rem] bg-primary"></span>
  <span class="animate-ping-slow relative inline-flex rounded-lg h-6 w-6  inset-y-[20rem] left-[15rem] bg-primary"></span>
  <span class="animate-ping-moderato relative inline-flex rounded-lg h-3 w-3 inset-y-[8rem] left-[64rem] bg-primary"></span>
  <span class="animate-ping-moderato relative inline-flex rounded-lg h-3 w-3 inset-y-[14rem] left-[35rem] bg-primary"></span>
  <span class="animate-ping-moderato relative inline-flex rounded-lg h-10 w-10 inset-y-[19rem] left-[24rem] bg-primary"></span>
  <span class="animate-ping-moderato relative inline-flex rounded-lg h-10 w-10 inset-y-[35rem] left-[5rem] bg-primary"></span>
  <span class="animate-ping-moderato relative inline-flex rounded-lg h-3 w-3 inset-y-[1rem] left-[8rem] bg-primary"></span>
</div>

    <div class="hero min-h-screen bg-base-200">
  <div class="hero-content text-center ">
    <div class="max-w-md ">
      <h1 class="text-5xl font-bold">Welcome to Group</h1>
      <h1 class="text-4xl font-bold p-9 ">KP-1</h1>


<p class="py-6">

      </p>
      <button class="btn btn-primary animate-bounce " @click="goHome">Get Started</button>
    </div>
  </div>
</div>
</template>
 
<style scoped>

</style>