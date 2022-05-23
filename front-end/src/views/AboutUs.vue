<script setup>
import p1 from "../assets/AdminPNG/1.png"



const myTeam = [
  {
    'photo': 'https://scontent-kut2-2.xx.fbcdn.net/v/t39.30808-6/271129077_4438876166238753_8476312548203047623_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=T1mEev51WzYAX_wsfMl&_nc_ht=scontent-kut2-2.xx&oh=00_AT_Cms-KZhe8UwUNifEqJcEhz_Y83XSdi9RPi_QBzzaeSg&oe=62907E4A',
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
    'photo': 'https://scontent-kut2-1.xx.fbcdn.net/v/t1.18169-9/10492251_463085203858201_6361870020470654759_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=yqKEK4zni20AX9-Zbq1&_nc_ht=scontent-kut2-1.xx&oh=00_AT_Hiso1w1OLR4uDHMkUJ1AQwFpRDaQTqHBe8DLGFJlaMA&oe=62B21C6E',
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
    'photo': 'https://scontent-kut2-2.xx.fbcdn.net/v/t39.30808-6/272998035_1930265093801974_3627705199021434752_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=wvQFFgpbOP0AX8prhF4&_nc_ht=scontent-kut2-2.xx&oh=00_AT8_GnV7n7NOKBMoDes3jcGdHsYDkEZvcKedVGTTxsK7tQ&oe=6290EF55',
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
      <img class="w-30 h-30 object-cover  mb-2 z-10 " :src="team.photo" alt="logo">

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
