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

let editStartTime = ref('')
let editNotes = ref('')
//`${edit.getFullYear}-${edit.getMonth+1}-${edit.getDate}T${edit.getHours}:${edit.getUTCMinutes}`
// let test = ref('2022-02-20T02:02');
//2022-02-20T02:02


const getEventById = async (id) => {
  try {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/scheduled/${id}`);
    console.log(res.status);
    if (res.status === 200) {
      selectedEvent.value = await res.json();
      console.log(selectedEvent.value);
      editNotes.value = selectedEvent.value.eventNotes

      let edit = new Date(selectedEvent.value.eventStartTime);
      console.log('edit')
      console.log(edit)
      
      if(edit.getMonth() < 9){
        editStartTime.value = `${edit.getFullYear()}-0${edit.getMonth()+1}`;
      } else {
        editStartTime.value = `${edit.getFullYear()}-${edit.getMonth()+1}`;
      }
      if(edit.getDate() < 10){
        editStartTime.value += `-0${edit.getDate()}T${edit.toLocaleTimeString('it-IT')}`;
      } else{
        editStartTime.value += `-${edit.getDate()}T${edit.toLocaleTimeString('it-IT')}`
      }
      
      //editStartTime.value = edit.toLocaleString("en-US", {year: "numeric", month: "2-digit", day: "numeric"})
      //05/23/2022
      //5/23/2022, 16:30:00
      console.log('editstatitmmee')
      console.log(editStartTime)


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
          eventNotes: notes
        })
    })
    if (res.status === 201) {
        const modEvent = await res.json()
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
  <div class="p-10">
    <div id="HaveEvent">
      <div v-if="events.length != 0">
        <div id="ListEvent">
          <div>
            <ol class="">
                <div class="grid grid-cols-3 gap-3 ">
              <li v-for="(event, index) in events" :key="index" class="card w-96 bg-base-100 shadow-xl space-x-5">
                <div class="card-body bg-white" >
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
                      @confirmDelete ="$emit('deleteEvent', event.id)"
                      class="btn btn-primary"
                    />
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
    <p class="py-2">Event Duration:  {{selectedEvent.eventDuration}} Minutes</p>
    <p class="py-2">Event Notes: <textarea type="number" v-model="editNotes" placeholder="Note ..."></textarea></p>
    <div class="modal-action">
      <button class="btn btn-primary" @click="editEvent(editStartTime, editNotes, selectedEvent.id)">Update</button>
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
