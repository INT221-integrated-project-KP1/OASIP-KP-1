<script setup>
import { ref } from 'vue'

let props = defineProps({
    event: {
        default: [],
        type: Object
    }
})

console.log("TEST");
console.log(props.event)

// DELETE
const removeNote = async (deleteId) => {
    console.log(deleteId)
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/scheduled/${deleteId}`, {
        method: 'DELETE'
    })
    if (res.status === 200) {
        console.log('deleted successfully')
    } else console.log('error, cannot delete data')
}


</script>
 
<template>
    <div>
        <div id="ListEvent">
            <div>
                <ul>
                    <li>
                        <p>Booking Name: {{ event.bookingName }}</p>
                        <p>Booking Email: {{ event.bookingEmail }}</p>
                        <p>Event Category Name: {{ event.eventCategoryName }}</p>
                        <p>Event Category Description: {{ event.eventCategoryDescription }}</p>
                        <p>Event Start Time: {{ new Date(event.eventStartTime).toString() }}</p>
                        <p>Event Duration: {{ event.eventDuration }} Minutes</p>
                        <button @click="removeNote(event.id)"></button>
                        <!-- <p>Event Details: {{ event.eventDetails }}</p> -->
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>
 
<style>
</style>