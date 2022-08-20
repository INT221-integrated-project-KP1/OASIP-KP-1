<script setup>
import { ref,onBeforeMount } from "vue";
import ShadowEventVue from "./ShadowEvent.vue";
import Fillter from "./Fillter.vue";
import { useRouter } from 'vue-router'
import { userData } from "../stores/userData.js"

const myUserData = userData();

console.log(myUserData.userList);
const myRouter = useRouter()
const goSignup = () => {
    myRouter.push({ name: 'SignUp' })
}

//GET BY ID
// const getEventById = async (id) => {
//   try {
//     const res = await fetch(`${import.meta.env.VITE_BASE_URL}/event/${id}`);
//     console.log(res.status);
//     if (res.status === 200) {
//       selectedEvent.value = await res.json();
//       editNotes.value = selectedEvent.value.eventNotes
//       if (editNotes.value == null) {
//         editNotes.value = "";
//       }
//       let edit = new Date(selectedEvent.value.eventStartTime);
//       editStartTime.value = `${edit.getFullYear()}-${numberFormat(edit.getMonth() + 1, 2)}-${numberFormat(edit.getDate(), 2)}T${edit.toLocaleTimeString('it-IT')}`
//       console.log(new Date().getDate());
//       console.log(edit.getDate());
//       console.log(editStartTime.value);
//     } else {
//       console.log("error, cannot get data");
//     }
//   } catch (err) {
//     console.log("Error: ", err.message);
//   }
// };


// defineEmits(["deleteUser", "updateUser"]);

// const selectedEvent = ref({ id: '', bookingName: '', bookingEmail: '', eventCategoryName: '', eventCategoryDescription: '', eventStartTime: '', eventDuration: '', eventNotes: '' });

function topFunction() {
    console.log("TestTop")
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}


// const updateEvent = async (startTime, notes, id, duration) => {
const statusError = ref(0)
const statusErrorText = ref("")

// const EditEvent = async (notes, startTime, id, duration) => {

//   const status = await myEvents.updateEvent(startTime, notes, id, duration);
//   statusErrorText.value = status.error
//   statusError.value = status.status
//   if (statusError == -1) {
//     errorInsert()
//   }
//   topFunction()
//   setTimeout(() => (statusError.value = 0), 2000);
// }

const errorInsert = () => {
    topFunction()
    setTimeout(() => (statusError.value = 0), 2000);
};

const deleteFun = (id) => {
    let com = confirm("You want to delete a user");
    if (com) {
        myUserData.removeEvent(id)
    }
}
</script>

<template>
    <div class="flex justify-center">
        <div class="m-10">
            <div class="p-5">
                <div class="alert alert-success shadow-lg" v-if="statusError === 1">
                    <div>
                        <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
                            viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                        <span>Your Edit has been confirmed!</span>
                    </div>
                </div>
                <div class="alert alert-error shadow-lg" v-else-if="statusError === -1">
                    <div>
                        <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
                            viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                        <span>Error! Input Value Uncomplete {{ statusErrorText }}</span>
                    </div>
                </div>
            </div>
            <div id="isHaveUser">
                <div v-if="myUserData.userList.length != 0">
                    <div id="ListEvent">
                        <div>
                            <ol class="">
                                <div class="grid xl:grid-cols-3 lg:grid-cols-2 grid-cols-1 gap-10 justify-items-center">
                                    <li v-for="(user, index) in myUserData.userList" :key="index"
                                        class="card w-96 bg-base-100 shadow-xl space-x-5">
                                        <div class="card-body bg-white">
                                            <p class="card-title"> Name: {{ user.name }} </p>
                                            <p v-if="user.email !== undefined"> Email: {{
                                                    user.email
                                            }}</p>
                                            <p class="card-title"> Role: {{ user.role }} </p>

                                        </div>
                                    </li>
                                    <!-- //ทำเงา ๆๆๆ -->
                                    <ShadowEventVue />
                                </div>
                            </ol>
                        </div>
                    </div>
                </div>
                <div v-else class="grid justify-items-center">
                    <div class="card w-96 glass">
                        <figure><img src="../assets/gif2.gif" alt="gif2"></figure>
                        <div class="card-body">
                            <h2 class="card-title">No User </h2>
                            <p>Do you want to Register?</p>
                            <div class="card-actions justify-end">
                                <button class="btn" @click="goSignup">Sign Up!</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>




</template>

<style>
</style>
