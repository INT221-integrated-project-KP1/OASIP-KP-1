<script setup>
import { ref } from "vue";
import DeleteButton from "../components/deleteButton.vue";

let props = defineProps({
  events: {
    default: [],
    type: Array,
  },
});




defineEmits(["selectedEventId", "deleteEvent"]);

//let selectedEventId = ref('');
const selectedEvent = ref({bookingName: '', bookingEmail : '', eventCategoryName : '', eventCategoryDescription: '', eventStartTime: '',eventDuration:'',eventNotes:''});


const getEventById = async (id) => {
  try {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/scheduled/${id}`);
    console.log(res.status);
    if (res.status === 200) {
      selectedEvent.value = await res.json();
      console.log(selectedEvent.value);
    } else {
      console.log("error, cannot get data");
    }
  } catch (err) {
    console.log("Error: ", err.message);
  }
};


console.log(props.events);
</script>

<template>
  <div class="p-10">
    <div id="HaveEvent">
      <div v-if="events.length != 0">
        <div id="ListEvent">
          <div>
            <ol class="">
                <div class="grid grid-cols-3 gap-3 ">
              <li v-for="(event, index) in events" :key="index" class="card w-96 bg-base-100 shadow-xl space-x-5">
                <div class="card-body">
                  <p class="card-title"> Booking Name: {{ event.bookingName }} </p>
                  <p v-if="event.bookingEmail !== undefined"> Booking Email: {{ event.bookingEmail }}</p>
                  <p>Event Category Name:
                    {{ event.eventCategory.eventCategoryName }}
                  </p>
                  <p>
                    Event Start Time:
                    {{ new Date(event.eventStartTime).toString() }}
                  </p>
                  <p>Event Duration: {{ event.eventDuration }} Minutes</p>
                  <p v-if="event.eventDetails !== undefined">
                    Event Details: {{ event.eventDetails }}
                  </p>
                  <div class="card-actions justify-end">
                    <!-- <br /><button
                      @click="$emit('selectedEventId', event.id)"
                      for="my-modal-6" class="btn modal-button"
                    >
                      Show more...
                    </button> -->
                    <!-- The button to open modal -->
                        <!-- The button to open modal -->
                        
                    <label  @click="getEventById(event.id)"
                    for="my-modal-6" class="btn modal-button" >Show more...</label>


                    <DeleteButton
                      @click="$emit('deleteEvent', event.id)"
                      class="btn btn-primary"
                    />
                  </div>
                </div>
              </li>
 <!-- //ทำเงา ๆๆๆ -->
              <div class="card w-96 bg-base-100 shadow-xl space-x-5"></div>
              </div>
            </ol>
<!-- Modal -->
    <input type="checkbox" id="my-modal-6" class="modal-toggle" />
    <div class="modal modal-bottom sm:modal-middle">
    <div class="modal-box">
    <h3 class="font-bold text-lg">Booking Name: {{ selectedEvent.bookingName }}</h3>
    <p class="py-2">Booking Email: {{ selectedEvent.bookingEmail }}</p>
    <p class="py-2">Event Category Name: {{ selectedEvent.eventCategoryName }}</p>
    <p class="py-2">Event Category Description: {{ selectedEvent.eventCategoryDescription }}</p>
    <p class="py-2">Event Start Time: {{ new Date(selectedEvent.eventStartTime).toString() }}</p>
    <p class="py-2">Event Duration: {{ selectedEvent.eventDuration }} Minutes</p>
    <p class="py-2">Event Notes: {{ selectedEvent.eventNotes }}</p>
    <div class="modal-action">
      <label for="my-modal-6" class="btn">Close</label>
    </div>
  </div>
</div>
 <!--  -->
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

<style></style>
