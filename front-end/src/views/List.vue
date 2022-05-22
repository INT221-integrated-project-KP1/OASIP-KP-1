<script setup>
import EventList from "../components/EventList.vue";
import { events } from "../stores/eventData.js";

const myEvents = events();

window.onscroll = () => {
  //const bottomOfWindow
  const scrollTop = document.documentElement.scrollTop;
  const innerHeight = window.innerHeight;
  const offsetHeight = document.documentElement.offsetHeight;
  if (myEvents.checkLoaded) {  
    if (scrollTop + innerHeight - offsetHeight >= 0) {
      //bottomOfWindow
      // console.log("bottomOfWindow");
      //do tood
      myEvents.pageIncrement();
      myEvents.getEventsFilteredMorePage();
    }
  }
  }
</script>

<template>
  <div>
    <div>
      <EventList
        @deleteEvent="myEvents.removeEvent"
        @updateEvent="myEvents.updateEvent"
      ></EventList>
    </div>
  </div>
</template>

<style scoped>
</style>
