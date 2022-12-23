<script setup>
import { useRouter } from 'vue-router'
import Clock from "./components/Clock.vue"
import { cookieData } from "./stores/cookieData.js"
import { userData } from "./stores/userData.js"
import { msData } from "./stores/loginMS.js"
import { ref, computed } from "vue";

const cookie = cookieData()
const userStore = userData()
const ms = msData()
const myRouter = useRouter()

const goWelcome = () => {
  myRouter.push({ name: 'Welcome' })
}

const logoutFun = () => {
  if(ms.msLogin == true){
      ms.logoffMS()
  }
  cookie.setCookie("msal.b8588d84-fe40-487c-9948-7b70a676916c.client.info", "", -1)
  cookie.setCookie("msal.b8588d84-fe40-487c-9948-7b70a676916c.idtoken", "", -1)
  cookie.setCookie("token", "", -1)
  cookie.setCookie("name", "", -1)
  cookie.setCookie("role", "", -1)
  cookie.setCookie("email", "", -1)
  cookie.setCookie("refreshtoken", "", -1)
  myRouter.push({ name: 'Welcome' })
}
// const signIn = () => {
//   console.log(cookie.getCookie('token'))
//   if(cookie.getCookie('token') == ""  ) {myRouter.push({ name: 'SignIn' });}
//   else myRouter.push({ name: 'ListUser' })
// }
const checkToken = () => {
  console.log(cookie.getCookie('token') == "")
  if (cookie.getCookie('token') == "") { return true }
  else return false
}

</script>

<template>



  <div>
    <div class="navbar bg-white" v-show="!($route.name === 'Welcome' )">
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
              <router-link v-show="cookie.getCookie('token') !== ''" :to="{ name: 'List' }"> List Event All
              </router-link>
            </li>
            <li>
              <router-link v-show="cookie.getCookie('role') == 'ADMIN' || cookie.getCookie('role') == 'STUDENT'"
                :to="{ name: 'Booking' }"> Add event </router-link>
            </li>
            <li>
              <router-link :to="{ name: 'ListCategory' }"> List Category </router-link>
            </li>
            <!-- <li>
              <router-link :to="{ name: 'SignUp' }"> Sign Up </router-link>
            </li>
            <li>
              <router-link :to="{ name: 'SignIn' }"> Sign In </router-link>
            </li> -->
            <li v-show="cookie.getCookie('role') === 'ADMIN'">
              <router-link :to="{ name: 'ListUser' }"> List User </router-link>
              <!-- <p @click="signIn"> List User </p> -->
            </li>

            <li v-show="cookie.getCookie('role') === 'ADMIN'">
              <router-link :to="{ name: 'listEventCategoryOwner' }"> List Owner </router-link>
              <!-- <p @click="signIn"> List User </p> -->
            </li>

            <li>
              <router-link :to="{ name: 'AboutUs' }"> About US </router-link>
            </li>
          </ul>
        </div>


        <a class="btn btn-ghost normal-case text-xl" @click="goWelcome">Daiimod</a>
        <div class="px-10">
          <Clock />
        </div>

      </div>

      <div class="navbar-center hidden lg:flex">
        <ul class="menu menu-horizontal p-0">

          <div class="tabs tabs-boxed">
            <router-link :to="{ name: 'Home' }" :class="[$route.name == 'Home' ? 'tab-active' : '', 'tab']">Home
            </router-link>
            <router-link v-show="cookie.getCookie('token') !== ''" :to="{ name: 'List' }"
              :class="[$route.name == 'List' ? 'tab-active' : '', 'tab']">List All
            </router-link>


            <router-link v-show="cookie.getCookie('role') == 'ADMIN' || cookie.getCookie('role') == 'STUDENT'"
              :to="{ name: 'Booking' }" :class="[$route.name == 'Booking' ? 'tab-active' : '', 'tab']">
              Add event </router-link>

            <router-link :to="{ name: 'ListCategory' }"
              :class="[$route.name == 'ListCategory' ? 'tab-active' : '', 'tab']">
              List Category </router-link>

            <!-- <router-link :to="{ name: 'SignUp' }" :class="[$route.name == 'SignUp' ? 'tab-active' : '', 'tab']">
              Sign Up </router-link>
            <router-link :to="{ name: 'SignIn' }" :class="[$route.name == 'SignIn' ? 'tab-active' : '', 'tab']">
              Sign In </router-link> -->
            <!-- 
  <router-link :to="{ name: 'ListUser' }" :class="[$route.name == 'ListUser' ? 'tab-active' : '', 'tab']">
              List User </router-link> -->

            <div v-show="cookie.getCookie('role') === 'ADMIN' ">
              <router-link :to="{ name: 'SignUp' }" :class="[$route.name == 'SignUp' ? 'tab-active' : '', 'tab']">
                Sign Up </router-link>
            </div>

            <router-link v-show="cookie.getCookie('role') === 'ADMIN'" :to="{ name: 'ListUser' }"
              :class="[$route.name == 'ListUser' ? 'tab-active' : '', 'tab']"> List User
            </router-link>

            <router-link v-show="cookie.getCookie('role') === 'ADMIN'" :to="{ name: 'listEventCategoryOwner' }"
              :class="[$route.name == 'listEventCategoryOwner' ? 'tab-active' : '', 'tab']"> List Owner 
            </router-link>

            <router-link :to="{ name: 'BlindedEvent' }" :class="[$route.name == 'BlindedEvent' ? 'tab-active' : '', 'tab']">
              Blinded Event </router-link>

            <router-link :to="{ name: 'AboutUs' }" :class="[$route.name == 'AboutUs' ? 'tab-active' : '', 'tab']">
              About US </router-link>
          </div>

        </ul>
      </div>

      <div class="navbar-end">
        <div v-if="cookie.getCookie('token') == '' ? true : false">
          <label tabindex="0" class="btn btn-ghost btn-circle" @click="myRouter.push({ name: 'SignIn' });">
            <div class="w-50">
              <p>login</p>
            </div>
          </label>
        </div>

        <div v-else class="dropdown dropdown-end">
          <label tabindex="0" class="btn btn-ghost btn-circle avatar">
            <div class="w-50 rounded-full">
              <img src="./assets/AdminPNG/2.png" />
            </div>
          </label>
          <ul tabindex="0" class="mt-3 p-2 shadow menu menu-compact dropdown-content bg-base-100 rounded-box w-52">
            <li>
              <a class="justify-between">
                User : {{ cookie.getCookie('name') }}
              </a>
            </li>
            <li>
              <a class="justify-between">
                Role : {{ cookie.getCookie('role') }}
              </a>
            </li>
            <li><a @click="logoutFun">Logout</a></li>
          </ul>
        </div>


      </div>

    </div>


    <div class="">
      <router-view></router-view>
    </div>
  </div>


</template>

<style scoped>

</style>