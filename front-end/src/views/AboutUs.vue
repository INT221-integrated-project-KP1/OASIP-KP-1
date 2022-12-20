<script setup>
import p1 from "../assets/Team/tus.jpg"
import p2 from "../assets/Team/amorn.jpg"
import p3 from "../assets/Team/mark.jpg"



const myTeam = [
  {
    'photo': p1,
    'name': "Rapeepat Klamjeen",
    'role': "ยาจก",
    'phone': "085-132-XXXX",
    'facebook': "Rapeepat Klamjeen",
    'URLfacebook': "https://www.facebook.com/rapeepat.klamjeen/",
    'instagram': "tuskungg",
    'URLInstagram': "https://www.instagram.com/tuskungg/",
    'github': "tuskingcup",
    'URLGithub': "https://github.com/tuskingcup",
    'ID': 63130500099
  }, {
    'photo':  p2,
    'name': "Amornpong Duangchawee",
    'role': "Valorant $$",
    'phone': "098-XXX-4953",
    'facebook': "Amornpong Duangchawee",
    'URLfacebook': "https://www.facebook.com/Amornnnnn/",
    'instagram': "a.mornp",
    'URLInstagram': "https://www.instagram.com/a.mornp/",
    'github': "huato112",
    'URLGithub': "https://github.com/huato112",
    'ID': 63130500131

  },
  {
    'photo': p3,
    'name': "Aritach Krudminburee",
    'role': "นอนดูเพื่อนทำงาน",
    'phone': "096-701-XXXX",
    'facebook': "Mark Aritach",
    'URLfacebook': "https://www.facebook.com/markkArit/",
    'instagram': "mark_aritach",
    'URLInstagram': "https://www.instagram.com/mark_aritach",
    'github': "markid1ggwp",
    'URLGithub': "https://github.com/markid1ggwp",
    'ID': 63130500133
  }

]
</script>
 
<template>
  <div class="grid lg:grid-cols-3 md:grid-cols-2 grid-cols-1 gap-2 justify-items-center p-5 ">
    <div v-for="(team, index) in myTeam" :key="index" class=" 
    max-w-sm content-center justify-center mx-auto bg-white mt-[10vh] rounded-lg border border-gray-200 shadow-md dark:bg-gray-800 dark:border-gray-700
   transition ease-in-out delay-150  hover:-translate-y-1 hover:scale-110 hover:bg-indigo-500 duration-300">
      <img class="w-30 h-30 object-cover  mb-2 z-10 " :src= "team.photo" alt="logo">

      <div class=" flex flex-col pl-10 pb-10 pr-10">
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
            <img class="w-10" src="../assets/Logo/Phone.png" alt="phone" />
            <h1 class="text-base text-gray-900 ml-5 font-semibold">{{ team.phone }}</h1>
          </div>
          <div class="tuskungtech mt-2 flex items-center">
            <img class="w-10" src="../assets/Logo/Facebook.png" alt="Facebook" />
            <a :href="team.URLfacebook" target="_blank" class="text-base text-gray-900 ml-5 font-semibold point ">{{
                team.facebook
            }}</a>
          </div>
          <div class="tuskungtech mt-2 flex items-center ">
            <img class="w-10" src="../assets/Logo/IG.png" alt="IG" />
            <a :href="team.URLInstagram" target="_blank" class="text-base text-gray-900 ml-5 font-semibold point">{{
                team.instagram
            }}</a>
          </div>
          <div class="tuskungtech mt-2 flex items-center ">
            <img class="w-10" src="../assets/Logo/GitHub.png" alt="GitHub" />
            <a :href="team.URLGithub" target="_blank" class="text-base text-gray-900 ml-5 font-semibold point">{{
                team.github
            }}</a>
          </div>
        </div>
      </div>
    </div>


  </div>

</template>
 
<style scoped>
.tuskungtech {
  padding: 2%;
  border: none;
  color: black;
  transition: 0.3s;
  height: auto
}

.tuskungtech:hover {
  background-color: #1F1D1E;
  color: white;
  
}
</style>
