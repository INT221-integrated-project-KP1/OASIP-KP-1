<script setup>
import { ref, computed, onBeforeMount } from "vue";

import { userData } from "../stores/userData.js"
import { cookieData } from "../stores/cookieData"
import { fileData } from "../stores/fileData.js";
import { useRouter } from "vue-router";
import { events } from "../stores/eventData.js"
import { categorys } from "../stores/categoryData.js"
const cookie = cookieData();


const myCookie = cookieData();
const myFileData = fileData();
const { params } = useRouter();
const myRouter = useRouter();
const myUserData = userData();
const clickup = ref(false)
const myCategorys = categorys()
const arrayUser = ref([])
const beforeArrayUser = ref([])
const filtereventCategoryId= ref(0);
const userLec = ref([])
const lecOwer = ref([])

const reset = () => {
    lecOwer.value = []
    filtereventCategoryId.value = 0;
     clickup.value = false
}
const getEventCategoryOwner = async () => {
    try {
        lecOwer.value = [];
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/eventcategoryowner/${filtereventCategoryId.value}`, {
            method: "GET",
            headers: {
                "content-type": "application/json",
                Authorization: "Bearer " + myCookie.getCookie("token"),
            },
        });
        console.log(res.status);
        if (res.status === 200) {
            lecOwer.value = await res.json();
            clickup.value = true
        } else if (res.status === 401) {
            let resText = await res.text();

            console.log("real");
            myUserData.refreshToken();
        } else {
            console.log("error, cannot get data");
        }
    } catch (err) {
        console.log("ERROR: " + err);

    }

    addArrayUser()
};
const getUserLec = async () => {
    try {
        lecOwer.value = [];
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/user/lecturer`, {

            method: "GET",
            headers: {
                "content-type": "application/json",
                Authorization: "Bearer " + myCookie.getCookie("token"),
            },
        });
        console.log(res.status);
        if (res.status === 200) {
            userLec.value = await res.json();
        } else if (res.status === 401) {
            let resText = await res.text();

            console.log("real");
            myUserData.refreshToken();
        } else {
            console.log("error, cannot get data");
        }
    } catch (err) {
        console.log("ERROR: " + err);

    }
};

const addArrayUser = () => {
    arrayUser.value = []
    beforeArrayUser.value = []

    lecOwer.value.forEach((user) => {
        arrayUser.value.push(user.id)
        beforeArrayUser.value.push(user.id)
    })
}

const updataOwner = async() => {
    if (beforeArrayUser.value != arrayUser.value) {
            try {
                const res = await fetch(
                    `${import.meta.env.VITE_BASE_URL}/eventcategoryowner`,
                    {
                        method: "POST",
                        headers: {
                            "content-type": "application/json",
                            Authorization: "Bearer " + cookie.getCookie("token"),
                        },
                        body: JSON.stringify({
                            eventCategoryId: filtereventCategoryId.value,
                            userId: arrayUser.value,
                        }),
                    }
                );
                if (res.status === 201) {

                    getEventCategoryOwner()
                    console.log("edited successfully");
                } else if (res.status === 400) { alert('Category should be at least 1 lecture')}
                else if (res.status === 401) {
                    let resText = await res.text();
                    if (resText.toUpperCase().match("TOKENEXPIRED")) {
                        //ได้ละ
                        console.log("real");
                        refreshToken();
                    }
                    if (
                        resText
                            .toUpperCase()
                            .match("cannot refresh token. need to login again".toUpperCase())
                    ) {
                        cookie.setCookie("token", "", -1);
                        cookie.setCookie("name", "", -1);
                    }
                } else {
                    console.log("error, cannot edit");
                }
            } catch (err) {
                console.log("catchhhhh");
                console.log(err);
                myRouter.push({ name: "SignIn" });

            }
        };
    
}

myUserData.getUsers();
getUserLec()
</script>

<template>
    <div class="card bg-white p-2 m-5">
        <div class="form-control ">
            <div class="lg:flex lg:justify-center hidden">
                <div class="m-3"> Select your categorys to see lectures in categorys</div>
                <div class="px-5">
                    <select class="select select-bordered " v-model="filtereventCategoryId">
                        <option value=0>Pick category</option>
                        <option v-for="(eventCategory, index) in myCategorys.categoryList" :key="index"
                            :value="eventCategory.id">{{ eventCategory.eventCategoryName }}</option>
                    </select>
                </div>

                <button class="btn btn-square bg-primary" @click="getEventCategoryOwner">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24"
                        stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                    </svg>
                </button>

                <button class="btn btn-square mx-2 " @click="reset">
                    RESET
                </button>
            </div>

        </div>
    </div>
    <div v-show="clickup">
        <label @click="" for="modalUser" :class="
        ['modal-button', 'duration-150', 'transform', 'hover:scale-125', 'transition', 'ease-linear', 'btn', 'btn', 'px-6', 'py-3.5', 'm-4', 'inline']
    ">Edit Owenr</label>
    <div class="card bg-white p-2 m-5">
        <table class="text-center">
            <tr>
                <th>id</th>
                <th>name</th>
                <th>email</th>
            </tr>
            <tr v-for="lecOwer in lecOwer">
                <td>{{ lecOwer.id }}</td>
                <td>{{ lecOwer.name }}</td>
                <td>{{ lecOwer.email }}</td>

            </tr>
        </table>

    </div>

    <!-- Modal -->
    <input type="checkbox" id="modalUser" class="modal-toggle" />
    <div class="modal modal-bottom sm:modal-middle ">
        <div class="modal-box bg-white">
            checkbox to select lecture to Owner

            <div v-for="( userLec ) in userLec" :key="index">
                <input type="checkbox" v-model="arrayUser" :value="userLec.id">
                <label> id : {{ userLec.id }} </label>
                <label> name : {{ userLec.name }} </label>
                <div> email : {{ userLec.email }} </div>
            </div>
            <div class="modal-action">
                <label
                    class="duration-150 transform hover:scale-125 transition ease-linear btn btn-primary px-6 py-3.5 m-4 inline"
                    for="modalUser" @click="
                    updataOwner()">
                    Update
                </label>
                <label for="modalUser"
                    class="duration-150 transform hover:scale-125 transition ease-linear btn px-6 py-3.5  m-4 inline">Close</label>
            </div>
        </div>
    </div>
    </div>
    


</template>

<style scoped>

</style>
