<script setup>
import { ref } from 'vue'

let props = defineProps({
    events: {
        default: [],
        type: Array
    }
})

defineEmits(['selectedEventId'])



//let selectedEventId = ref('');

console.log(props.events)

</script>
 
<template>
    <div>
        <div id="HaveEvent">
            <div v-if="events.length != 0">
                <h2>Event Scheduled</h2>
                <!-- <div id="SelectionEvent">
                    <label>Choose a Event:</label>
                    <select v-model="selectedEventId">
                        <option selected disabled>Select Event !</option>
                        <option v-for="(event, index) in events" :value="event.id">{{ event.bookingName }}</option>
                    </select>
                    <button @click="$emit('selectedEventId', selectedEventId)">Select</button>
                </div> -->
                <div id="ListEvent">
                    <div>
                        <ol>
                            <li v-for="(event, index) in events" :key="index">
                                <p>Booking Name: {{ event.bookingName }}</p>
                                <p v-if="event.bookingEmail !== undefined">Booking Email: {{ event.bookingEmail }}</p>
                                <p>Event Category Name: {{ event.eventCategoryName }}</p>
                                <p>Event Start Time: {{ event.eventStartTime }}</p>
                                <p>Event Duration: {{ event.eventDuration }}</p>
                                <p v-if="event.eventDetails !== undefined">Event Details: {{ event.eventDetails }}</p>
                                <br><button @click="$emit('selectedEventId', event.id)">Select</button>
                            </li>
                        </ol>
                        <p></p>
                    </div>
                </div>
            </div>
            <div v-else>
                <h2>No Scheduled Events</h2>
            </div>
        </div>

    </div>
</template>
 
<style>
</style>