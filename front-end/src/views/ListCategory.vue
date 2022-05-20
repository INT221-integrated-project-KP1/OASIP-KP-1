<script setup>
import { ref, onBeforeMount, reactive } from "vue";
import EventCatogory from "../components/EventCatogory.vue";
import { categorys } from "../stores/categoryData.js"

const myCategorys = categorys()


window.onscroll = () => {
  //const bottomOfWindow
  const scrollTop = document.documentElement.scrollTop;
  const innerHeight = window.innerHeight;
  const offsetHeight = document.documentElement.offsetHeight;
  if (scrollTop + innerHeight - offsetHeight >= 0) {
    //bottomOfWindow
    //do tood
    myCategorys.pageIncrement();
    myCategorys.getEvents();
  }
};
</script>

<template>
  <div>
    <div>
      <EventCatogory></EventCatogory>
    </div>
  </div>
</template>

<style scoped>
</style>
