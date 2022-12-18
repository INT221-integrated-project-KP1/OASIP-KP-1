<script setup>
import { ref, computed, onBeforeMount } from "vue";
import { cookieData } from "../stores/cookieData.js";
import { userData } from "../stores/userData.js";
import { fileData } from "../stores/fileData.js";
import { useRouter } from "vue-router";
import { events } from "../stores/eventData.js"
import { categorys } from "../stores/categoryData.js"

// //get จาก /api/blinded
// return id;
//     Instant eventStartTime;

//     Integer eventCategoryId;
//     eventCategoryEventCategoryName;

//     Integer eventDuration;
// no authorized
// ได้ id 
// eventstarttime
// eventdurationเอามา + กับ eventstarttime เป็นเวลาเริ่ม - จบ เช่น 13.30 + 30 นาที จะได้เป็น 13.30 - 14.00 โชว์ให้ user
//โชว์เป็นตารางแบ่งตาม eventcategory
const myCookie = cookieData();
const myFileData = fileData();
const { params } = useRouter();
const myRouter = useRouter();
const myUserData = userData();
const blinded = ref([])
const bookingTime = ref([])

const myCategorys = categorys()

const getEventBlinded = async () => {
    try {
        blinded.value = [];
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/event/blinded`, {
            method: "GET",
            headers: {
                "content-type": "application/json",
                Authorization: "Bearer " + myCookie.getCookie("token"),
            },
        });
        console.log(res.status);
        if (res.status === 200) {
            blinded.value = await res.json();
            dateTime();
        } else if (res.status === 401) {
            let resText = await res.text();
            // if (resText.toUpperCase().match("TOKENEXPIRED")) {
            //ได้ละ
            console.log("real");
            myUserData.refreshToken();
            // }
        } else {
            console.log("error, cannot get data");
        }
    } catch (err) {
        console.log("ERROR: " + err);

    }
};


const filterList = ref({
    eventCategoryId: -1,
    date: "",
});

const reset = () => {
    filterList.value.eventCategoryId = -1;
    filterList.value.date = "";
    getEventBlinded()
}

const dateTime = () => {
    bookingTime.value = []
    blinded.value.forEach((blinded) => {
        let data = { Category: "", Start_Time: "", End_Time: "" }
        data.Category = blinded.eventCategoryEventCategoryName
        data.Start_Time = new Date(blinded.eventStartTime);
        data.End_Time = addMinutes(new Date(blinded.eventStartTime), blinded.eventDuration)
        bookingTime.value.push(data)
    })
}
function addMinutes(date, minutes) {
    date.setMinutes(date.getMinutes() + minutes);
    return date;
}

function miMinutes(date, minutes) {
    date.setMinutes(date.getMinutes() - minutes);
    return date;
}
const filtering = () => {
    console.log(filterList.value.date == '')
    let filter = []
    let hasDate = filterList.value.date != ''
    let hasCategory = filterList.value.eventCategoryId > 0
    blinded.value.forEach((data) => {
        let check = [
            new Date(filterList.value.date).getDay() == new Date(data.eventStartTime).getDay(),
            new Date(filterList.value.date).getMonth() == new Date(data.eventStartTime).getMonth(),
            new Date(filterList.value.date).getFullYear() == new Date(data.eventStartTime).getFullYear()
        ]

        if (hasCategory && hasDate) {
            if (check[0] && check[1] && check[2]) {
                if (filterList.value.eventCategoryId == data.eventCategoryId) {
                    filter.push(data)
                }
            }
        }
        else if (hasCategory) {
            if (filterList.value.eventCategoryId == data.eventCategoryId) {
                filter.push(data)
            }
        }
        else if (hasDate) {
            if (check[0] && check[1] && check[2]) {
                filter.push(data)
            }
        }
        else {
            filter.push(data)
        }
    })
    bookingTime.value = []
    filter.forEach((blinded) => {
        let data = { Category: "", Start_Time: "", End_Time: "" }
        data.Category = blinded.eventCategoryEventCategoryName
        data.Start_Time = new Date(blinded.eventStartTime);
        data.End_Time = addMinutes(new Date(blinded.eventStartTime), blinded.eventDuration)
        bookingTime.value.push(data)
    })

}


getEventBlinded()
</script>

<template>
    <!-- FORM INPUT -->
    <div class="card bg-white p-2 m-5">

        <div class="form-control ">
            <div class="lg:flex lg:justify-center hidden">
                <div class="m-3"> Select your date to see Blinded</div>
                <div class="px-5">
                    <select class="select select-bordered " v-model="filterList.eventCategoryId">
                        <option disabled value=-1>Pick category</option>
                        <option value=0>none</option>
                        <option v-for="(eventCategory, index) in myCategorys.categoryList" :key="index"
                            :value="eventCategory.id">{{ eventCategory.eventCategoryName }}</option>
                    </select>
                </div>

                <input type="date" class="input input-bordered" v-model="filterList.date" />


                <button class="btn btn-square bg-primary" @click="filtering">
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
    <div class="card bg-white p-2 m-5">
        <table>
            <tr>
                <th>Category</th>
                <th>Start_Time</th>
                <th>End_Time</th>
            </tr>
            <tr v-for="bookingTime in bookingTime">
                <td>{{ bookingTime.Category }}</td>
                <td>{{ bookingTime.Start_Time }}</td>
                <td>{{ bookingTime.End_Time }}</td>

            </tr>
        </table>
    </div>

</template>

<style scoped>

</style>