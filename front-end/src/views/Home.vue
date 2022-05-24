<script setup>

import {  useRouter } from 'vue-router'
const myRouter = useRouter()
const goBooking = () => {
  myRouter.push({ name: 'Booking' })
}
const golistAll = () => {
  myRouter.push({ name: 'List' })
}
</script>
 
<template>


    <div class="hero min-h-screen bg-primary ">

        <div class="hero-content flex-col lg:flex-row-reverse">
            <img src="../assets/gif1.gif" alt="picture1" class="max-w-sm rounded-lg shadow-2xl " />
            <div>
                <h1 class="text-5xl font-bold"> Booking Web Application</h1>
                <h3 class="text-3xl font-bold m-5"> This Website make by group KP1</h3> 
                <br>
                <button class="btn btn-secondary m-2" @click="goBooking"> Booking </button>
                <button class="btn m-2" @click="golistAll"> List Event All </button>

            </div>
        </div>
    </div>
</template>
 
<style>

</style>
