<script setup>
import { ref, computed } from "vue";
import { events } from "../stores/eventData.js"
import { categorys } from "../stores/categoryData.js"

const myEvents = events()
const myCategorys = categorys()

const error = ref();
const errorWarning = ref();
const newEvent = ref({ name: '', notes: '', email: '', eventCategory: { id: "", duration: "" } });

const boolOverlap = ref(true);

//ระเบิด 01
function checkProperties(obj) {
  //check value of object is not null
  for (let key in obj) {
    if (key !== "notes") {
      if (obj[key] !== null && obj[key] !== "" && obj[key] !== undefined) {
        if (typeof obj[key] == "object") {
          console.log("object1");
          if (!(checkProperties(obj[key]))) {
            console.log("object2");
            return false;
          }
        }
      } else {
        return false;
      }
    }
    return true;
  }
  return true;
}


const validateEventName = computed(() => {
  //check length type bra bra brah...
  if (newEvent.value.name != undefined) {
    newEvent.value.name = newEvent.value.name.replace("  ", " ").trimStart();
    if ((newEvent.value.name.length > 100)) {
      console.log('name false');
      return false;
    }
  }
  return true;
})

const validateEventEmail = computed(() => {
  newEvent.value.email = newEvent.value.email.trimStart().trimEnd();
  console.log(newEvent.value.email)
  return newEvent.value.email.match(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)

})

const createNewEvent = async () => {
  newEvent.value.notes = newEvent.value.notes.trimStart().trimEnd();
  newEvent.value.name = newEvent.value.name.trimEnd();
  const status = await myEvents.createNewEvent(newEvent.value);
  console.log(status, 'tusCheckStauts');
    errorWarning.value = status.error
  if (status.status == 1) {
    newEvent.value = { name: '', notes: '', email: '', eventCategory: { id: "", duration: "" } };
  }
  statusError.value = status.status
  error.value = status.error
  myEvents.getEventsAllPageThatLoaded();
  topFunction();
  setTimeout(() => (statusError.value = 0), 2000);
  setTimeout(() => (error.value = ""), 2000);
}

const statusError = ref(0);
function topFunction() {
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
}

const errorInsert = () => {
  statusError.value = -1;
  topFunction();
  setTimeout(() => (statusError.value = 0), 2000);
};

const checkValidateOverlab = async () => {
  boolOverlap.value = await myEvents.validateOverlab(0, newEvent.value.eventCategory.id, newEvent.value.startTime, newEvent.value.eventCategory.duration)
}

const check = async () => {
  const bool1 = checkProperties(newEvent.value);
  const bool2 = validateEventEmail.value
  const bool3 = validateEventName.value
  const bool4 = myEvents.validateEventNotes(newEvent.value)
  const bool5 = myEvents.validateFutureDate(newEvent.value.startTime)
  const bool6 = boolOverlap.value
let er=""
if(!bool1){
er += "Value has null\n"
  }
if(!bool2){
      er += "Email invaild\n"
    }
    if(!bool3){
      er += "Name > 100\n"
    }
    if(!bool4){
      er += "Notes > 500\n"
    }
    if(!bool5){
      er += "Time is not Future\n"
    }
    if(!bool6){
      er += "Time is OverLap\n"
    }

  //0 คือ eventId เราไม่เช็ค เพราะเรา create ไม่มี eventId
  if(bool1 && bool2 && bool3 && bool4 && bool5 && bool6){
    createNewEvent()
  }else{
error.value = er    
    errorInsert();
  }

  return bool1 && bool2 && bool3 && bool4 && bool5 && bool6

}
</script>

<template>

  <div class="">
    <div class="p-5">
      <div class="alert alert-success shadow-lg" v-if="statusError === 1">
        <div>
          <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
            viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <span>Your booking has been confirmed!</span>
        </div>
      </div>

      <div class="alert alert-warning shadow-lg" v-else-if="statusError === 2">
        <div>
          <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
            viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
          </svg>
          <span>Warning: {{ errorWarning }}</span>
        </div>
      </div>

      <div class="alert alert-error shadow-lg" v-else-if="statusError === -1">
        <div>
          <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
            viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <span>Error! Input Value Uncomplete {{ error }}</span>
        </div>
      </div>
    </div>

    <DIV class ="grid lg:grid-cols-2 gap-2">
      <div class ="hidden lg:flex">
          <ul class="grid grid-rows-3 gap-2 p-4">
            <li v-for="(eventCategory, index) in myCategorys.categoryList" :key="index"
              class="card w-50 bg-base-100 shadow-xl space-x-5 transition ease-in-out delay-150  hover:-translate-y-1 hover:scale-110 hover:bg-indigo-500 duration-300">
              <div class="card-body bg-white">
                <p class="card-title rounded-md p-3" :class="myEvents.color[eventCategory.id-1]" > Event Category Name : {{
                    eventCategory.eventCategoryName
                }} </p>
                <p>Event Category Description:{{ eventCategory.eventCategoryDescription }}
                </p>
                <p class="bg-base-300"> Event Duration: {{ eventCategory.eventDuration }} Minutes</p>
                </div>
            </li>
          </ul>
      </div>
      <div class="grid gap-5 p-5 ">
        <!-- FORM INPUT -->
        <div class="flex justify-center self-center z-10 ">
          <div class="p-12 bg-white mx-auto rounded-2xl w-100">
            <div class="mb-4">
              <h3 class="font-semibold text-2xl text-gray-800">Insert Event</h3>
              <p class="text-gray-500">Please insert event to booking.</p>
            </div>
            <div class="space-y-5">
              <div class="space-y-2">
                <label class="text-sm font-medium text-gray-700 tracking-wide">Name :
                </label>
                <input maxlength="100" :class="validateEventName ?
                  ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']
                  : ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'border-red-400']
                " placeholder="Enter your name" v-model="newEvent.name" /><br>
                <span>{{ 100-newEvent.name.length}}/100</span>
              </div>

              <div class="space-y-2">
                <label class="text-sm font-medium text-gray-700 tracking-wide">Email :<span v-show="!validateEventEmail && newEvent.email.length > 0"
                    style="color: red;">*Invalid Email</span>
                </label>
                <input :class="validateEventEmail ?
                  ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']
                  : ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'border-red-400']
                " placeholder="mail@gmail.com" v-model="newEvent.email" />
              </div>
              <div class="space-y-2">
                <label class="mb-5 text-sm font-medium text-gray-700 tracking-wide">
                  Notes :
                </label>
                <textarea maxlength="500" :class="myEvents.validateEventNotes(newEvent) ?
                  ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']
                  : ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'border-red-400']
                " placeholder="Enter your note" v-model="newEvent.notes"></textarea>
                <!-- มาร์คเเก้ -->
                <br><span>{{ 500-newEvent.notes.length
                }}/500</span>
              </div>

               <div class="space-y-2">
                <label class="mb-5 text-sm font-medium text-gray-700 tracking-wide">
                  Event Category:
                </label>
                <select v-model="newEvent.eventCategory"
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-400">

                  <option v-for="(category, index) in myCategorys.categoryList" :key="index"
                    :value="{ id: category.id, duration: category.eventDuration }">
                    {{ category.eventCategoryName }}
                  </option>
                </select>
              </div>

              <div class="space-y-2">
                <label class="mb-5 text-sm font-medium text-gray-700 tracking-wide">
                  Start Time:<span v-show="!myEvents.validateFutureDate(newEvent.startTime)" style="color: red;">*Future Time
                    Only</span><span v-show="!newEvent.eventCategory.id > 0" style="color: red;">*Select Category First
                    </span><span v-show="!boolOverlap" style="color: red;">*OverLap Time
                    </span>
                </label>
                
                <input input type="datetime-local" :disabled="!newEvent.eventCategory.id > 0" :class="myEvents.validateFutureDate(newEvent.startTime) ?
                  ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']
                  : ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'border-red-400']
                " v-model="newEvent.startTime" @change="checkValidateOverlab()"/>
              </div>
             

              <div class="space-y-2">
                <label class="mb-5 text-sm font-medium text-gray-700 tracking-wide">
                  Event durations:
                </label>
                <input type="text"
                  :class="['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']"
                  disabled v-model="newEvent.eventCategory.duration" />
              </div>

              <div>
                <button type="submit"
                  class="w-full flex justify-center btn-success hover:btn-accent text-gray-100 p-3 hover:text-gray-100 rounded-full tracking-wide font-semibold shadow-lg cursor-pointer transition ease-in duration-500"
                  @click="check()">Add New Event
                </button>
              </div>
            </div>
            <div class="pt-5 text-center text-gray-400 text-xs">
              <span>
                Donate By 037-7-384-30-0 Bangkok Bank</span>
            </div>
          </div>
        </div>
    
      </div>
    </div>
  </DIV>
</template>

<style>

</style>
