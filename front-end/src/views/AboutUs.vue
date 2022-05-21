<script setup>
import p1 from "../assets/AdminPNG/1.png"



const myTeam = [
  {
    'photo': p1,
    'name': "",
    'role': "",
    'phone': "",
    'facebook': "",
    'URLfacebook': "",
    'instagram': "",
    'URLInstagram': "",
    'github': "",
    'URLGithub': "",
  }, {
    'photo': p1,
    'name': "",
    'role': "",
    'phone': "",
    'facebook': "",
    'URLfacebook': "",
    'instagram': "",
    'URLInstagram': "",
    'github': "",
    'URLGithub': "",
  },
  {
    'photo': p1,
    'name': "Aritach Krudminburee",
    'role': "นอนดูเพื่อนทำงาน",
    'phone': "096-701-XXXX",
    'facebook': "Mark Aritach",
    'URLfacebook': "https://www.facebook.com/profile.php?id=100004555050144",
    'instagram': "mark_aritach",
    'URLInstagram': "https://www.instagram.com/mark_aritach/?hl=en",
    'github': "markid1ggwp",
    'URLGithub': "https://github.com/markid1ggwp",
    'ID': 63130500133
  }

]
const photo = '../assets/1.jpg'
</script>
 
<template>
  <div class="grid grid-cols-3 gap-2 justify-items-center p-5 ">
    <div v-for="(team, index) in myTeam" :key="index" class=" 
    max-w-sm content-center justify-center mx-auto bg-white mt-[10vh] rounded-lg border border-gray-200 shadow-md dark:bg-gray-800 dark:border-gray-700
   transition ease-in-out delay-150  hover:-translate-y-1 hover:scale-110 hover:bg-indigo-500 duration-300">
      <img class="w-30 h-30 object-cover  mb-2 z-10 " :src="team.photo" alt="logo">

      <div class="flex flex-col   pl-10  pb-10">
        <h1 class="text-3xl text-gray-900 font-semibold">{{ team.name }}</h1>
        <div class="mb-2 mt-2">
          <h1 class="text-base text-gray-300 font-semibold">Student ID {{ team.ID }}</h1>
        </div>
        <div class="mb-2 mt-2">
          <h1 class="text-base text-gray-300 font-semibold">Bio</h1>
          <p class="text-base text-gray-800 font-semibold">{{ team.role }}</p>
        </div>
        <div class="mb-2 mt-2">

          <h1 class="text-base text-gray-300 font-semibold">Contact Details</h1>
          <div class="mt-2 flex items-center">
            <img class="w-10" src="../assets/Logo/Phone.png" />
            <h1 class="text-base text-gray-900 ml-5 font-semibold">{{ team.phone }}</h1>
          </div>
          <div class="mt-2 flex items-center">
            <img class="w-10" src="../assets/Logo/Facebook.png" />
            <a :href="team.URLfacebook" class="text-base text-gray-900 ml-5 font-semibold">{{ team.facebook }}</a>
          </div>
          <div class="mt-2 flex items-center">
            <img class="w-10" src="../assets/Logo/IG.png" />
            <a :href="team.URLInstagram" class="text-base text-gray-900 ml-5 font-semibold">{{ team.instagram }}</a>
          </div>
          <div class="mt-2 flex items-center">
            <img class="w-10" src="../assets/Logo/GitHub.png" />
            <a :href="team.URLGithub" class="text-base text-gray-900 ml-5 font-semibold">{{ team.github }}</a>
          </div>
        </div>
      </div>
    </div>


  </div>

</template>
 
<style>
</style>