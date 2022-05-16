<script setup>
import { ref, computed } from "vue";
import DeleteButton from "../components/deleteButton.vue";
import ShadowEventVue from "./ShadowEvent.vue";
import Fillter from "./Fillter.vue";
import { useRoute, useRouter } from 'vue-router'
import { events } from "../stores/eventData.js"

const myEventss = events()

const myRouter = useRouter()
const goBooking = () => {
  myRouter.push({ name: 'Booking' })
}

const props = defineProps({
  events: {
    default: [],
    type: Array,
  },
});

const myEvents = computed(() => {
  let eventsToAdd = []
  props.events.forEach((ele) => {
    eventsToAdd.push({
      "id": ele.id,
      "bookingName": ele.bookingName,
      "bookingEmail": ele.bookingEmail,
      "eventCategory": {
        "eventCategoryName": ele.eventCategory.eventCategoryName,
        "eventCategoryDescription": ele.eventCategory.eventCategoryDescription,
      },
      "eventStartTime": ele.eventStartTime,
      "eventDuration": ele.eventDuration,
      "eventNotes": ele.eventNotes
    })
  })
  return eventsToAdd;
})




defineEmits(["selectedEventId", "deleteEvent", "updateEvent"]);

//let selectedEventId = ref('');
const selectedEvent = ref({ bookingName: '', bookingEmail: '', eventCategoryName: '', eventCategoryDescription: '', eventStartTime: '', eventDuration: '', eventNotes: '' });

let editStartTime = ref('')
let editNotes = ref('')
//`${edit.getFullYear}-${edit.getMonth+1}-${edit.getDate}T${edit.getHours}:${edit.getUTCMinutes}`
// let test = ref('2022-02-20T02:02');
//2022-02-20T02:02



const numberFormat = function (number, width) {
  return new Array(+width + 1 - (number + '').length).join('0') + number;
}

const getEventById = async (id) => {
  try {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/scheduled/${id}`);
    console.log(res.status);
    if (res.status === 200) {
      selectedEvent.value = await res.json();
      editNotes.value = selectedEvent.value.eventNotes

      let edit = new Date(selectedEvent.value.eventStartTime);
      editStartTime.value = `${edit.getFullYear()}-${numberFormat(edit.getMonth() + 1, 2)}-${numberFormat(edit.getDate(), 2)}T${edit.toLocaleTimeString('it-IT')}`
    } else {
      console.log("error, cannot get data");
    }
  } catch (err) {
    console.log("Error: ", err.message);
  }
};

</script>

<template>
  <div class="flex justify-center">
    <div class="m-10">
      <div id="HaveEvent">
        <div v-if="myEvents.length != 0">
          <Fillter />
          <div id="ListEvent">
            <div>
              <ol class="">
                <div class="grid grid-cols-3 gap-2 ">
                  <li v-for="(event, index) in myEvents" :key="index" class="card w-96 bg-base-100 shadow-xl space-x-5">
                    <div class="card-body bg-white">
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
                        <label @click="getEventById(event.id)" for="my-modal-6"
                          class="modal-button duration-150 transform hover:scale-125 transition ease-linear btn btn-primary px-6 py-3.5 m-4 inline">Show
                          more...</label>
                        <DeleteButton class="btn btn-primary" @confirmDelete="$emit('deleteEvent', event.id)" />
                      </div>
                    </div>
                  </li>
                  <!-- //ทำเงา ๆๆๆ -->
                  <ShadowEventVue />
                </div>
              </ol>


              <!-- Modal -->
              <input type="checkbox" id="my-modal-6" class="modal-toggle " />
              <div class="modal modal-bottom sm:modal-middle ">
                <div class="modal-box bg-white">
                  <h3 class="font-bold text-lg">Booking Name: {{ selectedEvent.bookingName }}</h3>
                  <p class="py-2">Booking Email: {{ selectedEvent.bookingEmail }}</p>
                  <p class="py-2">Event Category Name: {{ selectedEvent.eventCategoryName }}</p>
                  <p class="py-2">Event Category Description: {{ selectedEvent.eventCategoryDescription }}</p>
                  <p class="py-2">Event Start Time: <input class="border-4 border-primary" type="datetime-local"
                      v-model="editStartTime"></p>
                  <p class="py-2">Event Duration: {{ selectedEvent.eventDuration }} Minutes</p>
                  <p class="py-2">Event Notes: </p><textarea maxlength="500" class="border-4 border-primary" rows="4"
                    cols="50" type="number" v-model="editNotes" placeholder="Note ..."></textarea><br><span>{{editNotes.length}}/500</span>
                  <div class="modal-action">

                    <label
                      class="duration-150 transform hover:scale-125 transition ease-linear btn btn-primary px-6 py-3.5 m-4 inline"
                      for="my-modal-6"
                      @click="$emit('updateEvent', editStartTime, editNotes, selectedEvent.id)">Update</label>
                    <label for="my-modal-6"
                      class="duration-150 transform hover:scale-125 transition ease-linear btn px-6 py-3.5  m-4 inline">Close</label>
                  </div>
                </div>
              </div>



            </div>
          </div>
        </div>
        <div v-else>
          <div class="card w-96 glass">
            <figure><img src="../assets/gif2.gif" alt="gif2"></figure>
            <div class="card-body">
              <h2 class="card-title">No Scheduled Events </h2>
              <p>Do you want to booking a new event?</p>
              <div class="card-actions justify-end">
                <button class="btn" @click="goBooking">Booking now!</button>
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
