<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import {useRoute, useRouter} from 'vue-router'
    const { params } = useRoute()
    const myRouter = useRouter()
    // const goHome = () => myRouter.push({name:'Home'})
    const goWelcome = () => myRouter.push({name:'Welcome'})

let index = ref(localStorage.getItem('active'));
let active = ref([])
if(index.value !== null && index.value != undefined && index.value != ''){
    color(index.value)
  }else {
    color(0)
  }





// const changeTab = (currentTab) => {
//   console.log(currentTab)
//   for (let i = 0; i < active.value.length; i++) {
//     if (currentTab === i) { active.value[i] = 'tab tab-active' ;}
//     else active.value[i] = 'tab' 
//   }
// }

function color (indexx){
  active.value[indexx] = "tab tab-active"
  if(indexx == 1){
    active.value[0] = "tab"
  }else{
    active.value[1] = "tab"
  }
}

const setlocal = (indexx) => {
  index = indexx;
  localStorage.setItem('active', indexx);
  color(indexx)
}




</script>

<template>
  <div>
  <!-- <div class="navbar bg-base-100" v-show="$route.name !=='Welcome'">
    <div class="flex-1">
      <a class="btn btn-ghost normal-case text-xl">Daimodd</a>
    </div>
    <div class="tabs tabs-boxed">
      <router-link :to="{ name: 'Home' }" class="tab">Home </router-link>
      <router-link :to="{ name: 'Booking' }" class="tab tab-active"> Add event </router-link>
    </div>
  </div> -->

  <!-- ddddd -->

  <div class="navbar bg-base-100" v-show="$route.name !=='Welcome'">
  <div class="navbar-start">
    <div class="dropdown">
      <label tabindex="0" class="btn btn-ghost lg:hidden">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h8m-8 6h16" /></svg>
      </label>
      <ul tabindex="0" class="menu menu-compact dropdown-content mt-3 p-2 shadow bg-base-100 rounded-box w-52">
        <li><a>Item 1</a></li>
        <li tabindex="0">
          <a class="justify-between">
            Parent
            <svg class="fill-current" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path d="M8.59,16.58L13.17,12L8.59,7.41L10,6L16,12L10,18L8.59,16.58Z"/></svg>
          </a>
          <ul class="p-2">
            <li><a>Submenu 1</a></li>
            <li><a>Submenu 2</a></li>
          </ul>
        </li>
        <li><a>Item 3</a></li>
      </ul>
    </div>
    <a class="btn btn-ghost normal-case text-xl" @click="goWelcome">Daiimod</a>
  </div>
  <div class="navbar-center hidden lg:flex">
    <ul class="menu menu-horizontal p-0">
      <div class="tabs tabs-boxed">
      <router-link :to="{ name: 'Home' }" @click="setlocal(0)" :class='active[0]'>List All </router-link>
      <router-link :to="{ name: 'Booking' }" @click="setlocal(1)" :class='active[1]'> Add event </router-link>
    </div>
    </ul>
  </div>
  <div class="navbar-end">
  <router-link :to="{ name: 'Booking' }"><a class="btn">Booking</a> </router-link>
    
  </div>
</div>


  <div>
    <router-view></router-view>
  </div>
  </div>
</template>

<style scoped>
</style>