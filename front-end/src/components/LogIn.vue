<script setup>
import { ref, computed } from "vue";
import { useRoute, useRouter } from 'vue-router'
const { params } = useRoute()
const myRouter = useRouter()

const loginuser = ref({ email: '', password: '' });

const MatchingCheck = async (loginuser) => {
    try {
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/matching/matching`, {
            method: "POST",
            headers: {
                "content-type": "application/json",
            },
            body: JSON.stringify({
                email: loginuser.email,
                password: loginuser.password
            }),
        });

       let matchStatus = await res.text()

        if (res.status === 200) {
            console.log("Check com")
            alert(matchStatus);
        } else {
            alert(matchStatus);
            console.log("error, cannot matching");
        }
    } catch (err) {
        console.log(err);
        alert(err);
    }
}

const noIsFun = () => {
    alert("This version doesn't have this function.")
}

const goSignUp = () => {
  myRouter.push({ name: 'SignUp' });
}

const validateEmail = computed(() => {
    loginuser.value.email = loginuser.value.email.trimStart().trimEnd();
  console.log(loginuser.value.email)
  return loginuser.value.email.match(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)
})

</script>

<template class="antialiased bg-gradient-to-br from-green-100 to-white">

<div class="container px-6 mx-auto">
        <div class="flex flex-col text-center md:text-left md:flex-row h-screen justify-evenly md:items-center">

            <div class="flex flex-col w-full">
                <div>
                    <svg class="w-20 h-20 mx-auto md:float-left fill-stroke text-gray-800" fill="none"
                        stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4">
                        </path>
                    </svg>
                </div>
                <h1 class="text-5xl text-gray-800 font-bold">Client Area</h1>
                <p class="w-5/12 mx-auto md:mx-0 text-gray-500">
                    Control and monitorize your website data from dashboard.
                </p>
            </div>


            <div class="w-full md:w-full lg:w-9/12 mx-auto md:mx-0">
                <div class="bg-white p-10 flex flex-col w-full shadow-xl rounded-xl">
                    <h2 class="text-2xl font-bold text-gray-800 text-left mb-5">
                        Sigin
                    </h2>
                    <form action="" class="w-full">
                        <div id="input" class="flex flex-col w-full my-5">
                            <label for="email" class="text-gray-500 mb-2">Email :<span
                    v-show="!validateEmail && loginuser.email.length > 0"
                    style="color: red;">*Invalid Email</span>
                            </label>
                            <input type="text" id="username" placeholder="Please insert your email"
                                class="appearance-none border-2 border-gray-100 rounded-lg px-4 py-3 placeholder-gray-300 focus:outline-none focus:ring-2 focus:ring-green-600 focus:shadow-lg"
                                v-model="loginuser.email" />
                        </div>
                        <div id="input" class="flex flex-col w-full my-5">
                            <label for="password" class="text-gray-500 mb-2">Password
                                <span
                    v-show="loginuser.password.length < 8 || loginuser.password.length > 18 "
                    style="color: red;">*Invalid Password</span>
                            </label>
                            <input type="password" id="password" placeholder="Please insert your password"
                                class="appearance-none border-2 border-gray-100 rounded-lg px-4 py-3 placeholder-gray-300 focus:outline-none focus:ring-2 focus:ring-green-600 focus:shadow-lg"
                                v-model="loginuser.password" />
                        </div>
                        <div id="button" class="flex flex-col w-full my-5">
                            <button type="button" class="w-full py-4 bg-green-600 rounded-lg text-green-100">
                                <div class="flex flex-row items-center justify-center">
                                    <div class="mr-2">
                                        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                                            xmlns="http://www.w3.org/2000/svg">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1">
                                            </path>
                                        </svg>
                                    </div>
                                    <div class="font-bold" @click="MatchingCheck(loginuser)">Signin</div>
                                </div>
                            </button>
                            <div class="flex justify-evenly mt-5">
                                <a href="#" class="w-full text-center font-medium text-gray-500" @click="noIsFun">Recover password!</a>
                                <a href="#" class="w-full text-center font-medium text-gray-500" @click="goSignUp">Signup!</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>
 
<style>
</style>