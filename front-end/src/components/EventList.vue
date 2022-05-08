<script setup>
import { ref } from 'vue'
import DeleteButton from '../components/deleteButton.vue';

let props = defineProps({
    events: {
        default: {},
        type: Object
    }
})

defineEmits(['selectedEventId', 'deleteEvent'])



//let selectedEventId = ref('');

console.log(props.events)

</script>
 
<template>
    <div>
        <div id="HaveEvent">
            <div v-if="events.length != 0">
                <div id="ListEvent">
                    <div>
                        <ol class="card w-96 bg-base-100 shadow-xl " >
                            <li v-for="(event, index) in events.content" :key="index" class="card-body">
                                <p class="card-title">Booking Name: {{ event.bookingName }}</p>
                                <p v-if="event.bookingEmail !== undefined">Booking Email: {{ event.bookingEmail }}</p>
                                <p>Event Category Name: {{ event.eventCategory.eventCategoryName }}</p>
                                <p>Event Start Time: {{ new Date(event.eventStartTime).toString() }}</p>
                                <p>Event Duration: {{ event.eventDuration }} Minutes</p>
                                <p v-if="event.eventDetails !== undefined">Event Details: {{ event.eventDetails }}</p>
                                <div class="card-actions justify-end">
                                    <br><button @click="$emit('selectedEventId', event.id)" class="btn btn-primary-focus">Select</button>
                                    <DeleteButton @click="$emit('deleteEvent', event.id)" class="btn btn-primary"/>
                                </div>
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