<script setup>
import { ref, onBeforeMount, computed, reactive } from 'vue'

const categorys = ref([]);

const getEventCategory = async () => {
    try {
        console.log(import.meta.env.URL);
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/EventCategory`)
        console.log(res.status)
        if (res.status === 200) {
            categorys.value = await res.json()
            console.log(categorys.value)
        } else {
            console.log('error, cannot get data')
        }
    }
    catch (err) {
        console.log(err)
    }
}

onBeforeMount(async () => {
    await getEventCategory()
})


const newEvent = ref({ eventCategory: { id: '', duration: '' }})

function checkProperties(obj) {
    for (let key in obj) {
        if (obj[key] !== null && obj[key] !== "" && obj[key] !== undefined) {
            if(typeof(obj[key]) == 'object'){
                console.log('object')
                if(checkProperties(obj[key]) == false){
                    return false;
                }
            }
        }
        else {
            return false
        }
    }
    return true;
}


// POST

// {
//     "eventStartTime": "2022-05-23T16:30:00Z",
//     "eventDuration": 152,
//     "eventCategory": {
//         "id": 1
//     },
//     "eventNotes": "Gayasdas",
//     "bookingEmail": "Gay@gmail.com",
//     "bookingName": "Gayสมเกียรติ ขยันเรียน กลุ่ม TT-4"
// }
const createNewEvent = async (event) => {
    try {
        console.log(event)
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/scheduled/`, {
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify({
                'bookingName': event.name,
                'bookingEmail': event.email,
                'eventNotes': event.note,
                'eventStartTime': new Date(event.startTime).toISOString().replace(".000Z", "Z"),
                'eventCategory': { id: event.eventCategory.id },
                // 'eventDuration': event.eventCategory.duration
            })
        })
        if (res.status === 200) {
            console.log('added sucessfully')
            newEvent.value = { eventCategory: { id: '', duration: '' }}
            alert("added successfully")
        } else console.log('error, cannot be added')
    }
    catch (err) { console.log(err) }
}

</script>
 
<template>
    <div id="BookingForm">
        <div>
            
            <p><span>Name: <input type="text" placeholder="Type here" class="input input-bordered w-full max-w-xs" v-model="newEvent.name"/></span></p>
            <p><span>Email: <input type="email" placeholder="Type here" class="input input-bordered w-full max-w-xs" v-model="newEvent.email"/></span></p>
            <p><span>Notes: <input type="text" placeholder="Type here" class="input input-bordered w-full max-w-xs" v-model="newEvent.note"/></span></p>
            <p><span>Start Time: <input type="datetime-local" placeholder="Type here" class="input input-bordered w-full max-w-xs" v-model="newEvent.startTime"/></span></p>
            <p><span>Event Category:
                    <select v-model="newEvent.eventCategory" class="select select-bordered w-full max-w-xs">
                        <option v-for="(category, index) in categorys" :key="index"
                            :value="{ 'id': category.id, 'duration': category.eventDuration }">
                            {{ category.eventCategoryName }}</option>
                    </select>
                </span>
            </p>
            <p><span>Event durations: <input type="text" class="input input-bordered w-full max-w-xs" disabled v-model="newEvent.eventCategory.duration"></span></p>
            <button class="input input-bordered w-full max-w-xs" @click="
                checkProperties(newEvent) ? createNewEvent(newEvent) : ''
                ">Book</button>
        </div>
    </div>
</template>
 
<style>
</style>
