<script setup>
import { ref, onMounted, onBeforeMount, onUnmounted } from 'vue'
import {useRoute, useRouter} from 'vue-router'
    const { params } = useRoute()
    const myRouter = useRouter()
    // const goHome = () => myRouter.push({name:'Home'})
    const goWelcome = () => {
      myRouter.push({name:'Welcome'})
    }

</script>

<template>
  <div>
  <div class="navbar bg-base-100" v-show="$route.name !=='Welcome'">
  <div class="navbar-start">
    <div class="dropdown">
      <label tabindex="0" class="btn btn-ghost lg:hidden">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h8m-8 6h16" /></svg>
      </label>
      <ul tabindex="0" class="menu menu-compact dropdown-content mt-3 p-2 shadow bg-base-100 rounded-box w-52">
        <li><router-link :to="{ name: 'Home' }">List All </router-link></li>
        <li><router-link :to="{ name: 'Booking' }"> Add event </router-link></li>
        
      </ul>
    </div>
    <a class="btn btn-ghost normal-case text-xl" @click="goWelcome">Daiimod</a>
  </div>
  <div class="navbar-center hidden lg:flex">
    <ul class="menu menu-horizontal p-0">
      
      <div class="tabs tabs-boxed">
      <router-link :to="{ name: 'Home' }" :class="[$route.name=='Home' ? 'tab-active' : '', 'tab']">List All </router-link>
      <router-link :to="{ name: 'Booking' }" :class="[$route.name=='Booking' ? 'tab-active' : '', 'tab']"> Add event </router-link>
    </div>
    </ul>
  </div>
  <div class="navbar-end"></div>

</div>


  <div class="bg-primary">
    <router-view></router-view>
  </div>
  </div>
</template>

<style scoped>
</style>