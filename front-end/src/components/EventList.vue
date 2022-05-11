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

//PUT
const editEvent = async (startTime, notes, id) => {
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/scheduled/${id}`, {
    method: 'PUT',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify({
      eventStartTime: new Date(startTime).toISOString().replace(".000Z", "Z"),
      eventNotes: notes,
    })
  })
  if (res.status === 201) {
    const modEvent = await res.json();
    props.events = props.events.map((event) =>
      event.id === modEvent.id
        ? { ...event, eventStartTime: modEvent.eventStartTime, eventNotes: modEvent.eventNotes }
        : event
    )

    console.log('edited successfully')
  } else {
    console.log('error, cannot edit')
  }
}


console.log(props.events);


</script>

<template>
  <div class="flex justify-center">
    <div class="m-10">
      <div id="HaveEvent">
        <div v-if="events.length != 0">


          <div class="card bg-white p-2 m-5">

            <div class="form-control ">
              <div class="input-group flex justify-center">
                <input type="text" placeholder="Search…" class="input input-bordered" />
                <button class="btn btn-square">
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24"
                    stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                  </svg>
                </button>

                <div class="px-5">
                  <select class="select select-bordered">
                    <option disabled selected>Pick category</option>
                    <option>T-shirts</option>
                    <option>Mugs</option>
                  </select>
                  <button class="btn">Go</button>
                </div>
              </div>
            </div>
          </div>

          <div id="ListEvent">
            <div>
              <ol class="">
                <div class="grid grid-cols-3 gap-3 ">
                  <li v-for="(event, index) in events" :key="index" class="card w-96 bg-base-100 shadow-xl space-x-5">
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
                  <div class="card w-96 bg-white shadow-xl space-x-5">

                  </div>
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
                  <p class="py-2">Event Start Time: <input type="datetime-local" v-model="editStartTime"></p>
                  <p class="py-2">Event Duration: {{ selectedEvent.eventDuration }} Minutes</p>
                  <p class="py-2">Event Notes: <textarea type="number" v-model="editNotes"
                      placeholder="Note ..."></textarea></p>
                  <div class="modal-action">

                    <label
                      class="duration-150 transform hover:scale-125 transition ease-linear btn btn-primary px-6 py-3.5 m-4 inline"
                      for="my-modal-6" @click="editEvent(editStartTime, editNotes, selectedEvent.id)">Update</label>
                    <label for="my-modal-6"
                      class="duration-150 transform hover:scale-125 transition ease-linear btn px-6 py-3.5  m-4 inline">Close</label>
                  </div>
                </div>
              </div>
              <!--  -->
            </div>
          </div>
        </div>
        <div v-else>
          <h2>No Scheduled Events</h2>
        </div>
      </div>
    </div>
  </div>


</template>

<style>
</style>
