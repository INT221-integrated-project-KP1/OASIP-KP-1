<script setup>
import { ref, onBeforeMount, computed, reactive } from "vue";
import { events } from "../stores/eventData.js"

const myEvents = events()

const getEvents = async () => {
  try {
    const res = await fetch(
      `${import.meta.env.VITE_BASE_URL}/scheduled/all`
    );
    if (res.status === 200) {
      const eventsToAdd = await res.json();
      // events << eventToAdd
      // eventToAdd อันที่โหลดเพิ่ม
      // events ของที่แสดงอยู่
      // เอาอันที่โหลดเพิ่มมาใส่
      eventsToAdd.forEach((e) => {
        if (e.id != myEvents.eventList.id) {
          myEvents.eventList.push(e);
        }
      });
    } else {
      console.log("error, cannot get data");
    }
  } catch (err) {
    console.log("ERROR: " + err);
  }
};

// GET
const getEventsAllPageThatLoaded = async () => {
  try {
    const res = await fetch(
      `${import.meta.env.VITE_BASE_URL}/scheduled?page=0&pageSize=${myEvents.pageSize*myEvents.page}`
    );
    if (res.status === 200) {
      const eventsToAdd = await res.json();
      // events << eventToAdd
      // eventToAdd อันที่โหลดเพิ่ม
      // events ของที่แสดงอยู่
      // เอาอันที่โหลดเพิ่มมาใส่
      //ตัสแก้
      myEvents.update(eventsToAdd.content);
    } else {
      console.log("error, cannot get data");
    }
  } catch (err) {
    console.log("ERROR: " + err);
  }
};

function validateOverlab(categoryId, startTime, duration) {
  getEvents();
  let newMilli = new Date(startTime).getTime(); //new EventStartTime in milli
  let newDurationMilli = duration * 60 * 1000;
  let bool = ref(true);
  myEvents.eventList.forEach((value) => {
    if (categoryId == value.eventCategory.id) {
      let milli = new Date(value.eventStartTime).getTime(); // get eventStartTime in milli
      let durationMilli = value.eventDuration * 60 * 1000;

      if (newMilli + newDurationMilli + 60000 > milli && newMilli + newDurationMilli - 60000 < milli + durationMilli) {
        //overlab 1+4
        console.log('Overlab 1+4');
        bool.value = false;
        return false; //overlab
      }
      else if (newMilli + 60000 > milli && newMilli - 60000 < milli + durationMilli) {
        //System.out.println("Overlab2+4");
        console.log('Overlab2+4');
        bool.value = false;
        return false;
        //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
      }
      else if (newMilli - 60000 < milli && newMilli + newDurationMilli + 60000 > milli + durationMilli) {
        //System.out.println("Overlab3");
        console.log('Overlab3');
        bool.value = false;
        return false;
        //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
      }
    }
  })
  console.log('return:' + bool.value);
  return bool.value;
}

const categorys = ref([]);
const error = ref();
const getEventCategory = async () => {
  try {
    console.log(import.meta.env.URL);
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/EventCategory`);
    console.log(res.status);
    if (res.status === 200) {
      categorys.value = await res.json();
      console.log(categorys.value);
    } else {
      console.log("error, cannot get data");
    }
  } catch (err) {
    console.log(err);
  }
};

onBeforeMount(async () => {
  await getEventCategory();
});

const newEvent = ref({ name: '', notes: '', email: '', eventCategory: { id: "", duration: "" } });


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
    if ((newEvent.value.name.length > 100)) {
      console.log('name false');
      return false;
    }
  }
  return true;
})

const validateEventEmail = computed(() => {
  console.log(newEvent.value.email)
return newEvent.value.email.match(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)


  // if (newEvent.value.email.match(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)) {
  //   //valid email
  //   return true;
  // }
  // //invalid email
  // return false;
})

const validateEventNotes = () => {
  // let note
  // console.log(notes)
  // if (notes == undefined) {
  //   note = ""
  // } else note = notes
  // console.log("dd"+note)

  //undefine ไม่มี length
  console.log("check notes")
  if (newEvent.value.notes.length > 500) {
    console.log('notes false');

    return false;
  }
  return true;
}

const validateFutureDate = computed(() => {
  let nowDate = new Date().getTime(); //time in millisecond
  let eventDate = new Date(newEvent.value.startTime).getTime();
  console.log('in Date')
  if (eventDate < nowDate) {
    console.log('future false');
    return false;
  }
  return true;
});


// POST
const createNewEvent = async (event) => {
  try {
    console.log(event);
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/scheduled/`, {
      method: "POST",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        bookingName: event.name,
        bookingEmail: event.email,
        eventNotes: event.notes,
        eventStartTime: new Date(event.startTime)
          .toISOString()
          .replace(".000Z", "Z"),
        eventCategory: { id: event.eventCategory.id },
      }),
    });

    if (res.status === 201) {
      console.log("added sucessfully");
      newEvent.value = { name: '', notes: '', email: '', eventCategory: { id: "", duration: "" } };
      statusError.value = 1;
    } else {
      error.value = await res.text()
      console.log(await res.text())
      console.log("error, cannot be added");
      statusError.value = 2;
    }
  } catch (err) {
    // error.value = await res.text()
    // console.log(await res.text())
    console.log(err);
    statusError.value = 2;
  }
  getEventsAllPageThatLoaded();
  topFunction();
  setTimeout(() => (statusError.value = 0), 2000);
};

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

const check = () => {
  console.log(checkProperties(newEvent.value) + "1")
  console.log(validateEventEmail.value + "2")
  console.log(validateEventName.value + "3")
  console.log(validateEventNotes() + "4")
  console.log(validateFutureDate.value + "5")
  console.log(validateOverlab(newEvent.value.eventCategory.id, newEvent.value.startTime, newEvent.value.eventCategory.duration) + "6")
  return checkProperties(newEvent.value) && validateEventEmail.value && validateEventName.value && validateEventNotes() && validateFutureDate.value && validateOverlab(newEvent.value.eventCategory.id, newEvent.value.startTime, newEvent.value.eventCategory.duration)

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
          <span>Warning: {{ error }}</span>
        </div>
      </div>

      <div class="alert alert-error shadow-lg" v-else-if="statusError === -1">
        <div>
          <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
            viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <span>Error! Input Value Uncomplete</span>
        </div>
      </div>
    </div>

    <DIV>
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
                " placeholder="Enter your name" v-model="newEvent.name" /><br><span>{{ newEvent.name.length
}}/100</span>
              </div>

              <div class="space-y-2">
                <label class="text-sm font-medium text-gray-700 tracking-wide">Email :<span v-show="!validateEventEmail"
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
                <textarea maxlength="500" :class="validateEventNotes() ?
                  ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']
                  : ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'border-red-400']
                " placeholder="Enter your note" v-model="newEvent.notes"></textarea>
                <!-- มาร์คเเก้ -->
                <br><span>{{ newEvent.notes.length
                  }}/500</span>
              </div>
              <div class="space-y-2">
                <label class="mb-5 text-sm font-medium text-gray-700 tracking-wide">
                  Start Time:<span v-show="!validateFutureDate" style="color: red;">*Future Time Only</span>
                </label>
                <input input type="datetime-local" :class="validateFutureDate ?
                  ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'focus:border-green-400']
                  : ['w-full', 'text-base', 'px-4', 'py-2', 'border', 'border-gray-300', 'rounded-lg', 'focus:outline-none', 'border-red-400']
                " v-model="newEvent.startTime" />
              </div>

              <div class="space-y-2">
                <label class="mb-5 text-sm font-medium text-gray-700 tracking-wide">
                  Event Category:
                </label>
                <select v-model="newEvent.eventCategory"
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-400">
                  <option v-for="(category, index) in categorys" :key="index"
                    :value="{ id: category.id, duration: category.eventDuration }">
                    {{ category.eventCategoryName }}
                  </option>
                </select>
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
                  @click="check()
                  ? createNewEvent(newEvent)
                  : errorInsert()">
                  Add New Event
                </button>
              </div>
            </div>
            <div class="pt-5 text-center text-gray-400 text-xs">
              <span>
                Donate By 037-7-384-30-0 Bangkok Bank</span>
            </div>
          </div>
        </div>
        <!-- <div class="carousel w-80 h-80 ">
            <div id="item1" class="carousel-item w-80 h-80">
              <img src="../assets/93018428.jpg" class="w-80 h-80" />
            </div> -->
        <!-- <div id="item2" class="carousel-item w-80 h-80">
              <img src="../assets/93018428.jpg" class="w-80 h-80" />
            </div>
          </div> -->
      </div>
    </div>
  </DIV>
</template>

<style>
</style>
