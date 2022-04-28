<script setup>
</script>

<template>
<div class="navbar bg-base-100">
  <div class="flex-1">
    <a class="btn btn-ghost normal-case text-xl">Gay</a>
  </div>
  <div class="flex-none">
    <ul class="menu menu-horizontal p-0">
      <li><router-link :to="{ name: 'Home' }">Home </router-link></li>
    </ul>
  </div>
</div>

    <div>
      <router-view></router-view>
    </div>
</template>

<style scoped>
a{
  color: rgb(19, 19, 19)
}
.router-link-active {
  color:blue
}
body{
  color: white
}
</style>