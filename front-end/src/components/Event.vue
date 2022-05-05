<script setup>
import { ref } from 'vue'
defineEmits(['deleteNote'])

let props = defineProps({
    event: {
        default: [],
        type: Object
    }
})

console.log("TEST");
console.log(props.event)




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
                        <p>Event Notes: {{ event.eventNotes }}</p>
                        <!-- <p>Event Details: {{ event.eventDetails }}</p> -->

                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>
 
<style>
</style>