<script setup>
import { ref } from "vue";
import ShadowEventVue from "./ShadowEvent.vue";
import Fillter from "./Fillter.vue";
import { useRouter } from 'vue-router'
import { events } from "../stores/eventData.js"
import { cookieData } from "../stores/cookieData.js"
import { fileData } from "../stores/fileData.js"

const myEvents = events()
const myCookie = cookieData()
const myRouter = useRouter()
const myFile = fileData()

const disNewFile = ref(true)
const newAttachment = ref("")
const goBooking = () => {
  myRouter.push({ name: 'Booking' })
}



//GET BY ID
const getEventById = async (id) => {
  try {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/event/${id}`, {
      method: "GET",
      headers: {
        'content-type': 'application/json',
        "Authorization": "Bearer " + myCookie.getCookie("token")
      }
    }
    );

    if (res.status === 200) {
      selectedEvent.value = await res.json();
      editNotes.value = selectedEvent.value.eventNotes
      if (editNotes.value == null) {
        editNotes.value = "";
      }
      let edit = new Date(selectedEvent.value.eventStartTime);
      editStartTime.value = `${edit.getFullYear()}-${numberFormat(edit.getMonth() + 1, 2)}-${numberFormat(edit.getDate(), 2)}T${edit.toLocaleTimeString('it-IT')}`
      console.log(new Date().getDate());
      console.log(edit.getDate());
      console.log(editStartTime.value);
    }
    else if (res.status === 401) {
      let resText = await res.text();
      if (resText.toUpperCase().match("TOKENEXPIRED")) {
        //ได้ละ
        console.log("real");
        refreshToken()
      }
      if (resText.toUpperCase().match("cannot refresh token. need to login again".toUpperCase())) {
        myCookie.setCookie("token", "", -1)
        myCookie.setCookie("name", "", -1)
      }
    } else { console.log("error, cannot delete data"); };
    console.log(res.status);
  } catch (err) {
    console.log("Error: ", err.message);
  }
};





defineEmits(["deleteEvent", "updateEvent"]);

const selectedEvent = ref({ id: '', bookingName: '', bookingEmail: '', eventCategory: { eventCategoryName: '', eventCategoryDescription: '' }, eventStartTime: '', eventDuration: '', eventNotes: '', attachment: '' });

let editStartTime = ref('')
let editNotes = ref('')
//2022-02-20T02:02

function topFunction() {
  console.log("TestTop")
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
}


const numberFormat = function (number, width) {
  return new Array(+width + 1 - (number + '').length).join('0') + number;
}

// const updateEvent = async (startTime, notes, id, duration) => {
const statusError = ref(0)
const statusErrorText = ref("")
const EditEvent = async (notes, startTime, id, duration, file) => {

  const status = await myEvents.updateEvent(startTime, notes, id, duration, file);
  statusErrorText.value = status.error
  statusError.value = status.status
  if (statusError == -1) {
    errorInsert()
  }
  topFunction()
  setTimeout(() => (statusError.value = 0), 2000);
  myEvents.getFilteredEvents();
}

const errorInsert = () => {
  topFunction()
  setTimeout(() => (statusError.value = 0), 2000);
};

const deleteFun = (id, attachment) => {
  let com = confirm("You want to delete a event");
  if (com) {
    myEvents.removeEvent(id, attachment)
  }
}
myEvents.getFilteredEvents();

const downloadFile = (name) => {
  myFile.getFile(name);
}

const checkFile = () => {
  let time = new Date(new Date().toISOString()).getTime();
  newAttachment.value = time + "_" + document.getElementById("fileupload").files[0].name
  alert("newfile is " + newAttachment.value)
  document.getElementById("fileupload").disabled = true;
}

const closeUpnewFile = () => {
  disNewFile.value = !disNewFile.value;
  newAttachment.value = '';
  document.getElementById("fileupload").disabled = false;
  document.getElementById("fileupload").value = null;
}

const updateMyEvent = (selectedEvent) => {
  let file1 = selectedEvent.attachment
  let file2 = newAttachment.value
  let file = ""
  alert(file1)
  alert(file2)
  if (file2 !== null) {
    if (!(document.getElementById("fileupload").files[0].size / 1024 / 1024 > 10) //admin edit event บอก err
    ) {
      if (file1 == "") {
        if (file1 != file2) {
          selectedEvent.attachment = newAttachment.value
          myFile.uploadFile(selectedEvent)
        }
      } else if (file1 == null) {
        if (file1 != file2) {
          selectedEvent.attachment = newAttachment.value
          myFile.uploadFile(selectedEvent)
        }
      }
      else if (file1 != file2 && file2 != "") {
        myFile.deleteFile(selectedEvent.attachment)
        selectedEvent.attachment = newAttachment.value
        myFile.uploadFile(selectedEvent)
      }
    }
  } 

  alert("file" + file)
  EditEvent(editNotes.value, editStartTime.value, selectedEvent.id, selectedEvent.eventDuration, selectedEvent.attachment)
}

const deleteFile = (selectedEvent) => {
  if (confirm("You confirm that you want to delete this file") == true) {
    myFile.deleteFile(selectedEvent.attachment)
    selectedEvent.attachment = ""
    EditEvent(editNotes.value, editStartTime.value, selectedEvent.id, selectedEvent.eventDuration, file)
    myEvents.getFilteredEvents();
  } else {
    closeUpnewFile();
  }
}
</script>

<template>
  <div class="flex justify-center">
    <div class="m-10">

      <div id="HaveEvent">
        <div class="p-5">
          <div class="alert alert-success shadow-lg" v-if="statusError === 1">
            <div>
              <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
                viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              <span>Your Edit has been confirmed!</span>
            </div>
          </div>

          <div class="alert alert-error shadow-lg" v-else-if="statusError === -1">
            <div>
              <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
                viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              <span>Error! Input Value Uncomplete {{ statusErrorText }}</span>
            </div>
          </div>
        </div>




        <Fillter />
        <div v-if="myEvents.eventList.length != 0">

          <div id="ListEvent">
            <div>
              <ol class="">
                <div class="grid xl:grid-cols-3 lg:grid-cols-2 grid-cols-1 gap-10 justify-items-center">
                  <li v-for="(event, index) in myEvents.eventList" :key="index"
                    class="card w-96 bg-base-100 shadow-xl space-x-5">
                    <div class="card-body bg-white">
                      <p class="card-title"> Booking Name: {{ event.bookingName }} </p>
                      <p v-if="event.bookingEmail !== undefined"> Booking Email: {{ event.bookingEmail }}</p>
                      <p :class="myEvents.color[event.eventCategory.id - 1]" class="rounded-md p-3">Event Category Name:
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

                      <p>Attachment: {{ event.attachment }} </p>
                      <button v-show="event.attachment !== null" @click="downloadFile(event.attachment)" class="btn"
                        style="width:100%"><i class="fa fa-download"></i> Download </button>


                      <div v-if="myCookie.getCookie('token') !== ''">
                        <div class="card-actions justify-end">
                          <label @click="getEventById(event.id); myEvents.boolOverlap = true;" for="my-modal-6" :class="
                            ['modal-button', 'duration-150', 'transform', 'hover:scale-125', 'transition', 'ease-linear', 'btn', 'btn-primary', 'px-6', 'py-3.5', 'm-4', 'inline']
                          ">Show
                            more... </label>
                          <label for="my-modal"
                            class="btn modal-button duration-150 transform hover:scale-125 transition ease-linear px-6 py-3.5 m-4 inline"
                            @click="deleteFun(event.id, event.attachment)">Delete</label>
                        </div>
                      </div>
                    </div>
                  </li>
                  <!-- //ทำเงา ๆๆๆ -->

                  <ShadowEventVue v-show="myEvents.checkLoaded" />

                </div>
              </ol>


              <!-- Modal -->
              <input type="checkbox" id="my-modal-6" class="modal-toggle" />
              <div class="modal modal-bottom sm:modal-middle ">
                <div class="modal-box bg-white">
                  <h3 class="font-bold text-lg">Booking Name: {{ selectedEvent.bookingName }}</h3>
                  <p class="py-2">Booking Email: {{ selectedEvent.bookingEmail }}</p>
                  <p class="py-2">Event Category Name: {{ selectedEvent.eventCategory.eventCategoryName }}</p>
                  <p class="py-2">Event Category Description: {{ selectedEvent.eventCategory.eventCategoryDescription }}
                  </p>
                  <div v-if="myEvents.validateFutureDate(selectedEvent.eventStartTime)">
                    <span v-show="!myEvents.validateFutureDate(editStartTime)" style="color: red;">*Future Time
                      Only</span>
                    <span v-show="!myEvents.boolOverlap" style="color: red;">*OverLap Time</span>
                    <p class="py-2">Event Start Time:

                      <input class="border-4 border-primary" type="datetime-local" v-model="editStartTime"
                        @change="myEvents.validateOverlab(selectedEvent.id, 0, editStartTime, selectedEvent.duration)" />
                    </p>
                    <p class="py-2">Event Notes: <span v-show="editNotes.length > 500" style="color: red;">*Invalid
                        Notes</span>

                    </p><textarea maxlength="500" class="border-4 border-primary" rows="4" cols="50" type="number"
                      v-model="editNotes" placeholder="Note ..."></textarea><br>
                    <span>{{ 500 - editNotes.length }}/500</span>
                  </div>

                  <div v-else>
                    <p class="py-2">Event Start Time:
                      <input disabled type="datetime-local" v-model="editStartTime">
                    </p>
                    <p class="py-2">Event Notes : {{ selectedEvent.eventNotes }}</p>
                  </div>
                  <p class="py-2">Event Duration: {{ selectedEvent.eventDuration }} Minutes</p>
                  <p class="py-2" v-show="selectedEvent.attachment==''||selectedEvent.attachment==undefined"> Attachment : {{ selectedEvent.attachment }}</p>
                  <button class="btn btn-primary" v-show="disNewFile" @click="disNewFile = !disNewFile"> Open Upload New File </button>
                  <button class="btn btn-secondary" v-show="!disNewFile" @click="closeUpnewFile"> Close Upload New File </button>

                  <input v-show="!disNewFile" type="file"
                    :class="['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']"
                    id="fileupload" @change="checkFile" />
                  <div>
                    <button v-show="selectedEvent.attachment != null" @click="deleteFile(selectedEvent)"> Delete File
                    </button>
                  </div>

                  <!-- <button v-show="!disNewFile" @click="deleteFile"> Delete File </button> -->

                  <div class="modal-action">
                    <label
                      :class="myEvents.validateFutureDate(selectedEvent.eventStartTime) ? ['duration-150', 'transform', 'hover:scale-125', 'transition', 'ease-linear', 'btn', 'btn-primary', 'px-6', 'py-3.5', 'm-4', 'inline'] : 'hidden'"
                      for="my-modal-6" @click="updateMyEvent(selectedEvent)">Update</label>
                    <!-- @click="$emit('updateEvent', editStartTime, editNotes, selectedEvent.id, selectedEvent.eventDuration)">Update</label> -->
                    <label for="my-modal-6"
                      class="duration-150 transform hover:scale-125 transition ease-linear btn px-6 py-3.5  m-4 inline">Close</label>
                  </div>
                </div>
              </div>



            </div>
          </div>
        </div>
        <div v-else class="grid justify-items-center">
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
