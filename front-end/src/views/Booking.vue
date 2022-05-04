<script setup>
import { ref, onBeforeMount, computed, reactive } from 'vue'

let categorys = ref([]);

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


let newEvent = reactive({
    name: '', email: '', detail: '', startTime: '', eventCategory: { id: '', duration: '' }
})

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
                'eventNotes': event.detail,
                'eventStartTime': new Date(event.startTime).toISOString().replace(".000Z", "Z"),
                'eventCategory': { id: event.eventCategory.id },
                'eventDuration': event.eventCategory.duration
            })
        })
        if (res.status === 201) {
            console.log('added sucessfully')
        } else console.log('error, cannot be added')
    }
    catch (err) { console.log(err) }
}

</script>
 
<template>
    <div id="BookingForm">
        <div>
            <p><span>Name: <input type="text" v-model="newEvent.name"></span></p>
            <p><span>Email: <input type="text" v-model="newEvent.email"></span></p>
            <p><span>Details: <input type="text" v-model="newEvent.detail"></span></p>
            <p><span>Start Time: <input type="datetime-local" v-model="newEvent.startTime"></span></p>
            <p><span>Event Category:
                    <select v-model="newEvent.eventCategory">
                        <option v-for="(category, index) in categorys" :key="index"
                            :value="{ 'id': category.id, 'duration': category.eventDuration }">
                            {{ category.eventCategoryName }}</option>
                    </select>
                </span>
            </p>
            <p><span>Event durations: <input type="text" disabled v-model="newEvent.eventCategory.duration"></span></p>
            <button @click="
                checkProperties(newEvent) ? createNewEvent(newEvent) : ''
                ">Book</button>
        </div>
    </div>
</template>
 
<style>
</style>