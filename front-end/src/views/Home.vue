<script setup>
import { ref } from "vue"
import { useRoute, useRouter } from 'vue-router'
const myRouter = useRouter()
const goBooking = () => {
  myRouter.push({ name: 'Booking' })
}

</script>
 
<template>


    <div class="hero min-h-screen bg-primary ">

        <div class="hero-content flex-col lg:flex-row-reverse">
            <img src="../assets/gif1.gif" alt="picture1" class="max-w-sm rounded-lg shadow-2xl hover:animate-bounce" />
            <div>
                <h1 class="text-5xl font-bold"> Booking Web Application</h1>
                <p class="text-2xl font-bold p-9">web นี้เป็นส่วนหนึ่งของวิชา 221 ของคณะเทคโนโลยีสารสนเทศ ทำเอาเพื่อเอาคะเเนนอย่างเดียวครับ  </p>
                <button class="btn" @click="goBooking"> Booking </button>
            </div>
        </div>
    </div>
</template>
 
<style>
</style>