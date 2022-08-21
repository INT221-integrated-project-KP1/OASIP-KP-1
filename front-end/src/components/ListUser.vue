<script setup>
import { ref, onBeforeMount } from "vue";
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

// GET BY ID
const getUserById = async (id) => {
    try {
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/user/${id}`);
        console.log(res.status);
        if (res.status === 200) {
            selectedUser.value = await res.json();

            selectedUser.value.createdOn = new Date(selectedUser.value.createdOn);
            selectedUser.value.updatedOn = new Date(selectedUser.value.updatedOn);



        } else {
            console.log("error, cannot get data");
        }
    } catch (err) {
        console.log("Error: ", err.message);
    }
};


// defineEmits(["deleteUser", "updateUser"]);

const selectedUser = ref({ name: '', email: '', role: '', createdOn: '', updatedOn: '' });

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

const deleteUser = (id) => {
    if (confirm("You want to delete a user")) {
        myUserData.removeUser(id)
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
                                            <p class="card-title" v-if="user.email !== undefined"> Email: {{
                                                    user.email
                                            }}</p>
                                            <p class="card-title"> Role: {{ user.role }} </p>

                                        </div>
                                        <div class="card-actions justify-end">
                                            <label @click="getUserById(user.id);" for="modalUser" :class="
                                                ['modal-button', 'duration-150', 'transform', 'hover:scale-125', 'transition', 'ease-linear', 'btn', 'btn-primary', 'px-6', 'py-3.5', 'm-4', 'inline']
                                            ">Show
                                                more...</label>
                                            <label for="my-modal"
                                                class="btn modal-button duration-150 transform hover:scale-125 transition ease-linear px-6 py-3.5 m-4 inline"
                                                @click="deleteUser(user.id)">Delete</label>

                                        </div>
                                    </li>
                                    <!-- //ทำเงา ๆๆๆ -->
                                    <ShadowEventVue />
                                </div>
                            </ol>

                            <!-- Modal -->
                            <input type="checkbox" id="modalUser" class="modal-toggle" />
                            <div class="modal modal-bottom sm:modal-middle ">
                                <div class="modal-box bg-white">
                                    <h3 class="font-bold text-lg">Name: {{ selectedUser.name }}</h3>
                                    <p class="py-2">Email: {{ selectedUser.email }}</p>
                                    <p class="py-2">Created On: {{ selectedUser.createdOn }}</p>
                                    <p class="py-2">updatedOn: {{ selectedUser.updatedOn }} </p>

                                    <div class="modal-action">
                                        <!-- <label
                                            :class="myEvents.validateFutureDate(selectedEvent.eventStartTime) ? ['duration-150', 'transform', 'hover:scale-125', 'transition', 'ease-linear', 'btn', 'btn-primary', 'px-6', 'py-3.5', 'm-4', 'inline'] : 'hidden'"
                                            for="my-modal-6"
                                            @click="EditEvent(editNotes, editStartTime, selectedEvent.id, selectedEvent.eventDuration)">Update</label> -->
                                        <!-- @click="$emit('updateEvent', editStartTime, editNotes, selectedEvent.id, selectedEvent.eventDuration)">Update</label> -->
                                        <label for="modalUser"
                                            class="duration-150 transform hover:scale-125 transition ease-linear btn px-6 py-3.5  m-4 inline">Close</label>
                                    </div>
                                </div>
                            </div>

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
