<script setup>
import { ref, onBeforeMount, reactive } from "vue";
import EventList from "../components/EventList.vue";

const events = ref([]);
// GET
const getEvents = async () => {
  try {
    console.log(import.meta.env.URL);
    const res = await fetch(
      `${import.meta.env.VITE_BASE_URL}/scheduled?page=${page.value}&pageSize=${
        pageSize.value
      }`
    );
    console.log(res.status);
    if (res.status === 200) {
      const eventsToAdd = await res.json();
      console.log(eventsToAdd);
      // events << eventToAdd
      // eventToAdd อันที่โหลดเพิ่ม
      // events ของที่แสดงอยู่
      // เอาอันที่โหลดเพิ่มมาใส่
      eventsToAdd.content.forEach((e) => {
        events.value.push(e);
      });
    } else {
      console.log("error, cannot get data");
    }
  } catch (err) {
    console.log(err);
  }
};

// const event = ref();
// const getEventById = async (id) => {
//   try {
//     const res = await fetch(`${import.meta.env.VITE_BASE_URL}/scheduled/${id}`);
//     console.log(res.status);
//     if (res.status === 200) {
//       event.value = await res.json();
//       console.log(event.value);
//     } else {
//       console.log("error, cannot get data");
//     }
//   } catch (err) {
//     console.log("Error: ", err.message);
//   }
// };

const removeEvent = async (deleteId) => {
  console.log(deleteId);
  const res = await fetch(
    `${import.meta.env.VITE_BASE_URL}/scheduled/${deleteId}`,
    {
      method: "DELETE",
    }
  );
  if (res.status === 200) {
    events.value = events.value.filter((event) => event.id !== deleteId);
    console.log("deleted successfully");
  } else console.log("error, cannot delete data");
};

onBeforeMount(async () => {
  await getEvents();
});

const a = ref("display:none");

const page = ref(0); //page start 0
const pageSize = ref(9); //default 4

window.onscroll = () => {
  //const bottomOfWindow
  const scrollTop = document.documentElement.scrollTop;
  const innerHeight = window.innerHeight;
  const offsetHeight = document.documentElement.offsetHeight;
  if (scrollTop + innerHeight - offsetHeight >= 0) {
    //bottomOfWindow
    console.log("bottomOfWindow");
    //do tood
    page.value++;
    getEvents();
  }
  console.log("scroll");
  console.log(document.documentElement.scrollTop + window.innerHeight);
  console.log(document.documentElement.offsetHeight);
};
</script>

<template>

  <div >
    <div>
      <EventList 
        :events="events" @deleteEvent="removeEvent"
      ></EventList>
    </div>

    
  </div>
</template>

<style scoped></style>
