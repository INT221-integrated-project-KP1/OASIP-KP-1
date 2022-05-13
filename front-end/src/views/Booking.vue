<script setup>
import { ref, onBeforeMount, computed, reactive } from "vue";

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

const newEvent = ref({ eventCategory: { id: "", duration: "" } });

function checkProperties(obj) {
  for (let key in obj) {
    if (obj[key] !== null && obj[key] !== "" && obj[key] !== undefined) {
      if (typeof obj[key] == "object") {
        console.log("object");
        if (checkProperties(obj[key]) == false) {
          return false;
        }
      }
    } else {
      return false;
    }
  }
  return true;
}

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
        eventNotes: event.note,
        eventStartTime: new Date(event.startTime)
          .toISOString()
          .replace(".000Z", "Z"),
        eventCategory: { id: event.eventCategory.id },
      }),
    });

    if (res.status === 201) {
      console.log("added sucessfully");
      newEvent.value = { eventCategory: { id: "", duration: "" } };
      statusError.value = 1;
    } else {
      error.value = await res.text()
      console.log(await res.text())
      console.log("error, cannot be added");
      statusError.value = 2;
    }
  } catch (err) {
    error.value = await res.text()
    console.log(await res.text())
    console.log(err);
    statusError.value = 2;
  }
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
                <input maxlength="100"
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-400"
                  placeholder="Enter your name" v-model="newEvent.name" />
              </div>

              <div class="space-y-2">
                <label class="text-sm font-medium text-gray-700 tracking-wide">Email :
                </label>
                <input
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-400"
                  placeholder="mail@gmail.com" v-model="newEvent.email" />
              </div>
              <div class="space-y-2">
                <label class="mb-5 text-sm font-medium text-gray-700 tracking-wide">
                  Notes :
                </label>
                <textarea maxlength="500"
                  class="w-full content-center text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-400"
                  placeholder="Enter your note" v-model="newEvent.note"></textarea>
              </div>
              <div class="space-y-2">
                <label class="mb-5 text-sm font-medium text-gray-700 tracking-wide">
                  Start Time:
                </label>
                <input input type="datetime-local"
                  class="w-full content-center text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-400"
                  v-model="newEvent.startTime" />
              </div>

              <div class="space-y-2">
                <label class="mb-5 text-sm font-medium text-gray-700 tracking-wide">
                  Event Category:
                </label>
                <select v-model="newEvent.eventCategory"
                  class="w-full content-center text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-400">
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
                  class="w-full content-center text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-400"
                  disabled v-model="newEvent.eventCategory.duration" />
              </div>

              <div>
                <button type="submit"
                  class="w-full flex justify-center btn-success hover:btn-accent text-gray-100 p-3 hover:text-gray-100 rounded-full tracking-wide font-semibold shadow-lg cursor-pointer transition ease-in duration-500"
                  @click="
                    checkProperties(newEvent)
                      ? createNewEvent(newEvent)
                      : errorInsert()
                  ">
                  Add New Event
                </button>
              </div>
            </div>
            <div class="pt-5 text-center text-gray-400 text-xs">
              <span>
                Copyright © 2021-2022
                <a href="https://codepen.io/uidesignhub" rel="" target="_blank" title="Ajimon"
                  class="text-green hover:text-green-500">AJI</a></span>
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
