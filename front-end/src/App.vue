<script setup>
import { useRouter } from 'vue-router'
import Clock from "./components/Clock.vue"


const myRouter = useRouter()

const goWelcome = () => {
  myRouter.push({ name: 'Welcome' })
}



</script>

<template>



  <div>
    <div class="navbar bg-white" v-show="$route.name !== 'Welcome'">
      <div class="navbar-start">
        <div class="dropdown">
          <label tabindex="0" class="btn btn-ghost lg:hidden">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24"
              stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h8m-8 6h16" />
            </svg>
          </label>
          <ul tabindex="0" class="menu menu-compact dropdown-content mt-3 p-2 shadow bg-base-100 rounded-box w-52">
            <li>
              <router-link :to="{ name: 'Home' }">Home </router-link>
            </li>
            <li>
              <router-link :to="{ name: 'List' }"> List Event All </router-link>
            </li>
            <li>
              <router-link :to="{ name: 'Booking' }"> Add event </router-link>
            </li>
            <li>
              <router-link :to="{ name: 'ListCategory' }"> List Category </router-link>
            </li>
            <li>
              <router-link :to="{ name: 'AboutUs' }"> About US </router-link>
            </li>
          </ul>
        </div>


        <a class="btn btn-ghost normal-case text-xl" @click="goWelcome">Daiimod</a>
      </div>

      <div class="navbar-center hidden lg:flex">
        <ul class="menu menu-horizontal p-0">

          <div class="tabs tabs-boxed">
            <router-link :to="{ name: 'Home' }" :class="[$route.name == 'Home' ? 'tab-active' : '', 'tab']">Home
            </router-link>
            <router-link :to="{ name: 'List' }" :class="[$route.name == 'List' ? 'tab-active' : '', 'tab']">List All
            </router-link>
            <router-link :to="{ name: 'Booking' }" :class="[$route.name == 'Booking' ? 'tab-active' : '', 'tab']">
              Add
              event </router-link>
               <router-link :to="{ name: 'ListCategory' }" :class="[$route.name == 'ListCategory' ? 'tab-active' : '', 'tab']">
              List Category </router-link>
            <router-link :to="{ name: 'AboutUs' }" :class="[$route.name == 'AboutUs' ? 'tab-active' : '', 'tab']">
              About US </router-link>
          </div>
        </ul>
      </div>
      <div class="navbar-end">
        <Clock />
      </div>

    </div>


    <div class="">
      <router-view></router-view>
    </div>
  </div>


</template>

<style scoped>
</style>