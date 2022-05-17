<script setup>
import { ref, onBeforeMount, reactive } from "vue";
import EventList from "../components/EventList.vue";
import {events} from "../stores/eventData.js"

const myEvents = events()

// onBeforeMount(async ()=>{
//   console.log(myEvents.pageSize)
//   await getEvents();
// }
// )






// const page = ref(0); //page start 0
// const pageSize = ref(9); //default 4

window.onscroll = () => {
  //const bottomOfWindow
  const scrollTop = document.documentElement.scrollTop;
  const innerHeight = window.innerHeight;
  const offsetHeight = document.documentElement.offsetHeight;
  if (scrollTop + innerHeight - offsetHeight >= 0) {
    //bottomOfWindow
    console.log("bottomOfWindow");
    //do tood
    myEvents.pageIncrement();
    myEvents.getEvents();
  }
  console.log("scroll");
  console.log("scroll: " + document.documentElement.scrollTop + window.innerHeight);
  console.log("scroll(Height): " + document.documentElement.offsetHeight);
};
</script>

<template>
  <div>
    <div>
      <EventList @deleteEvent="myEvents.removeEvent" @updateEvent="myEvents.updateEvent"></EventList>
    </div>
  </div>
</template>

<style scoped>
</style>
